package com.example.commonsmodule.security;

import com.example.commonsmodule.security.enums.UserRole;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.Set;

@Slf4j
public class JwtTokenProvider {
    private static final String HEADER_AUTHORIZATION = HttpHeaders.AUTHORIZATION;
    private static final String BEARER_TOKEN_START = "Bearer ";

    private String secret;
    private Long jwtExpiry;

    public JwtTokenProvider(String secretKey, long validityInSeconds) {
        this.secret = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.jwtExpiry = validityInSeconds;
    }

    public String createToken(Authentication authentication) throws JsonProcessingException {
        OurUserDetails ourUserDetails = (OurUserDetails) authentication.getPrincipal();
        Claims claims = Jwts.claims().setSubject(ourUserDetails.getUsername());

        claims.put("userId", String.valueOf(ourUserDetails.getId()));
        claims.put("username", ourUserDetails.getUsername());
        ObjectMapper objectMapper = new ObjectMapper();
        claims.put("roles", objectMapper.writeValueAsString(ourUserDetails.getUserRoles()));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (jwtExpiry * 1000)))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String getTokenFromRequestHeader(HttpServletRequest req) {
        String bearerToken = req.getHeader(HEADER_AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith(BEARER_TOKEN_START)) {
            return bearerToken.split(" ")[1];
        }
        return null;
    }

    public boolean validateToken(String token, HttpServletRequest httpServletRequest) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (ExpiredJwtException ex) {
            log.error("Expired: JWT token");
            httpServletRequest.setAttribute("expired", "Expired: JWT token");
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            log.error("Invalid: JWT token");
            ex.printStackTrace();
            httpServletRequest.setAttribute("invalid", "Invalid: JWT token");
        }
        return false;
    }

    public Authentication getAuthenticationFromTokenString(String token, HttpServletRequest request) throws JsonProcessingException {
        Claims body = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        Long userId = Long.valueOf(String.valueOf(body.get("userId")));
        String username = ((String) body.get("username"));
        ObjectMapper objectMapper = new ObjectMapper();
        Set<String> stringRoles = objectMapper.readValue(body.get("roles").toString(), Set.class);
        Set<UserRole> userRolesSet = CommonSecurityUtils.stringSetToUserRoleSet(stringRoles);
        OurUserDetails customUserDetails = new OurUserDetails(userId, username, null, userRolesSet);

        Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        return authentication;
    }
}
