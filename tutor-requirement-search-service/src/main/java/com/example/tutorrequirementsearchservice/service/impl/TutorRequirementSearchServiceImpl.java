package com.example.tutorrequirementsearchservice.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import com.example.commonsmodule.DTOs.TutorRequirementDTO;
import com.example.tutorrequirementsearchservice.service.TutorRequirementSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class TutorRequirementSearchServiceImpl implements TutorRequirementSearchService {

    private static final String MY_INDEX = "tutor_requirement_index";
    private final ElasticsearchClient elasticsearchClient;

    @Override
    public TutorRequirementDTO save(TutorRequirementDTO tutorRequirementDTO) {
        log.info("Saving Tutor Requirements of ID {}", tutorRequirementDTO.getId());
        IndexRequest<TutorRequirementDTO> indexRequest = IndexRequest.of(b -> b
                .index(MY_INDEX)
                .id(tutorRequirementDTO.getId())
                .document(tutorRequirementDTO)
                .refresh(Refresh.True));

        try{
            elasticsearchClient.index(indexRequest);
        } catch (IOException e){
            log.error("Error elastic search ", e.getMessage());
            throw new RuntimeException(e);
        }

        return tutorRequirementDTO;
    }
}
