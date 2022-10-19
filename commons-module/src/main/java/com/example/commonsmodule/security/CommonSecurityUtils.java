package com.example.commonsmodule.security;

import com.example.commonsmodule.security.enums.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class CommonSecurityUtils {

    public static Optional<Long> getCurrentUserId(){
        return Optional.ofNullable(getCurrentUserDetails()).map(ud -> ud.getId());
    }

    public static OurUserDetails getCurrentUserDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            if(authentication.getPrincipal() instanceof OurUserDetails)
                return (OurUserDetails) authentication.getPrincipal();
        }
        return null;
    }

    public static Set<UserRole> stringSetToUserRoleSet(Set<String> roleString){
        Set<UserRole> userRoles = new HashSet<>();
        for(String r: roleString) userRoles.add(Enum.valueOf(UserRole.class, r));
        return userRoles;
    }
}
