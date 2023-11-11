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
@Document(indexName = "album")
public class AlbumElasticDto {
  @Id
  private Long id;

  @Field(type = FieldType.Text, name = "title")
  private String title;

  @Field(type = FieldType.Date, name = "releaseDate")
  private Date releaseDate;
}
