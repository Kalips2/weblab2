package com.example.weblab2.elastic.dto;

import java.util.Date;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Builder
@Document(indexName = "artist")
public class ArtistElasticDto {
  @Id
  private Long id;

  @Field(type = FieldType.Text, name = "name")
  private String name;

  @Field(type = FieldType.Text, name = "surname")
  private String surname;

  @Field(type = FieldType.Date, name = "dateOfBirth")
  private Date dateOfBirth;
}
