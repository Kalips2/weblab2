package com.example.weblab2.elastic.repositories;

import com.example.weblab2.elastic.dto.ArtistElasticDto;
import com.example.weblab2.elastic.dto.LabelElasticDto;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

@ConditionalOnProperty(name = "elastic-search-enabled", havingValue = "true")
public interface ArtistElasticRepository extends ElasticsearchRepository<ArtistElasticDto, Long> {
  @Query("{\"match\": {\"surname\": {\"query\": \"?0\"}}}")
  Page<ArtistElasticDto> findBySurname(String surname, Pageable pageable);
}
