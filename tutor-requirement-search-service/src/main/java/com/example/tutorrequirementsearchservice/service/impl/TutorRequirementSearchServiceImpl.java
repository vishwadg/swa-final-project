package com.example.tutorrequirementsearchservice.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.commonsmodule.DTOs.TutorRequirementDTO;
import com.example.commonsmodule.exceptions.NotFoundException;
import com.example.tutorrequirementsearchservice.service.TutorRequirementSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TutorRequirementSearchServiceImpl implements TutorRequirementSearchService {

    private static final String MY_INDEX = "tutor_requirement_index";
    private final ElasticsearchClient elasticsearchClient;

    @Override
    @Cacheable(value = "tutor-requirements", key = "#title", condition = "#result.size() > 0")
    public List<TutorRequirementDTO> findAllByTutorRequirement(String tutorRequirement) {
        log.info("Find all by tutor requirement {}", tutorRequirement);
        SearchRequest searchRequest = SearchRequest.of(s ->
                        s.index(MY_INDEX)
                        .q(tutorRequirement)
        );
        SearchResponse<TutorRequirementDTO> searchResponse = null;
        try {
            searchResponse = elasticsearchClient.search(searchRequest, TutorRequirementDTO.class);
        } catch (IOException e) {
            log.error("Error: elastic  search", e.getMessage());
            throw new RuntimeException(e);
        }
        return searchResponse.hits().hits().stream().map(Hit::source).toList();
    }

    @Override
    @Cacheable(value = "tutor-requirements", key = "#id")
    public TutorRequirementDTO findOne(String id) {
        log.info("Tutor Requirement searching by id {}", id);
        GetResponse<TutorRequirementDTO> response = null;
        try {
            response = elasticsearchClient.get(g -> g
                    .index(MY_INDEX)
                    .id(id), TutorRequirementDTO.class);
        } catch (IOException e) {
            log.error("Error: elastic  search", e.getMessage());
            throw new RuntimeException(e);
        }
        if (response.found()) {
            TutorRequirementDTO tutorRequirementDTO = response.source();
            return tutorRequirementDTO;
        } else {
            log.error("Tutor requirement not found!!");
            throw new NotFoundException("Requested Tutor not found !!!");
        }
    }
    @Override
    @CachePut(value = "tutor-requirements", condition = "#result.id != null", key = "#result.title")
    @CacheEvict(value = "tutor-requirements", key = "#tutorRequirementDTO.title")
    public TutorRequirementDTO save(TutorRequirementDTO tutorRequirementDTO) {
        log.info("Saving Tutor Requirements of ID {}", tutorRequirementDTO.getId());
        IndexRequest<TutorRequirementDTO> indexRequest = IndexRequest.of(b -> b
                .index(MY_INDEX)
                .id(tutorRequirementDTO.getId())
                .document(tutorRequirementDTO)
                .refresh(Refresh.True));

        try {
            elasticsearchClient.index(indexRequest);
        } catch (IOException e) {
            log.error("Error elastic search ", e.getMessage());
            throw new RuntimeException(e);
        }

        return tutorRequirementDTO;
    }

    @Override
    @CachePut(value = "tutor-requirements", key = "#tutorRequirementDTO.title")
    @CacheEvict(value = "tutor-requirements", key = "#tutorRequirementDTO.title")
    public TutorRequirementDTO update(TutorRequirementDTO tutorRequirementDTO) {
        log.info("Updating Tutor Requirement of id {} ", tutorRequirementDTO.getId());
        UpdateRequest updateRequest = UpdateRequest.of((b -> b
                .index(MY_INDEX)
                .id(tutorRequirementDTO.getId())
                .doc(tutorRequirementDTO)
                .refresh(Refresh.True)
        ));

        try {
            elasticsearchClient.update(updateRequest, TutorRequirementDTO.class);
        } catch (IOException e) {
            log.error("Error: elastic search", e.getMessage());
            throw new RuntimeException(e);
        }
        return tutorRequirementDTO;
    }

    @Override
    @CacheEvict(value = "tutor-requirements", key = "#id")
    public void deleteById(String id) {
        log.warn("Tutor Requirement delete by id {}", id);
        DeleteRequest deleteRequest = DeleteRequest.of(d -> d
                .index(MY_INDEX)
                .id(id));
        try {
            elasticsearchClient.delete(deleteRequest);
        } catch (IOException e) {
            log.error("Error: elastic search");
            throw new RuntimeException(e);
        }
    }
}
