package com.example.tutorrequirementsearchservice.configs;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticConfig {

    @Value("${app.elastic.host}")
    private String host;

    @Value("${app.elastic.port}")
    private int port;

    @Bean
    public ElasticsearchClient elasticsearchClient(){
        RestClient httpClient = RestClient.builder(new HttpHost(host, port))
                .build();
        ElasticsearchTransport transport = new RestClientTransport(httpClient, new JacksonJsonpMapper());
        ElasticsearchClient esClient = new ElasticsearchClient(transport);
        return esClient;
    }

}
