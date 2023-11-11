package com.example.weblab2.elastic.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Builder
@Document(indexName = "label")
public class LabelElasticDto {
  @Id
  private String id;

  @Field(type = FieldType.Text, name = "name")
  private String name;

  @Field(type = FieldType.Text, name = "coordinates")
  private String coordinates;
}
