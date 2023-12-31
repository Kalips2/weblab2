package com.example.weblab2.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.lang.NonNull;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.example.weblab2.elastic.repositories")
@ConditionalOnProperty(name = "elastic-search-enabled", havingValue = "true")
public class ElasticConfiguration extends ElasticsearchConfiguration {
  @Value("${elastic-host}")
  private String host;

  @Value("${elastic-port}")
  private String port;

  @Override
  @NonNull
  public ClientConfiguration clientConfiguration() {
    return ClientConfiguration.builder()
        .connectedTo(host + ":" + port)
        .build();
  }
}
