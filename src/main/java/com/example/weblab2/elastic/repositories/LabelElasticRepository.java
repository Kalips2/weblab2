package com.example.weblab2.elastic.repositories;

import com.example.weblab2.elastic.dto.LabelElasticDto;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

@ConditionalOnProperty(name = "elastic-search-enabled", havingValue = "true")
public interface LabelElasticRepository extends ElasticsearchRepository<LabelElasticDto, String> {
  @Query("{\"match\": {\"name\": {\"query\": \"?0\"}}}")
  Page<LabelElasticDto> findByName(String name, Pageable pageable);
}
