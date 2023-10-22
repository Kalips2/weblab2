package com.example.weblab2.controllers.rest;

import com.example.weblab2.data.LabelData;
import com.example.weblab2.dto.LabelDto;
import com.example.weblab2.services.LabelService;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/rest/labels")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LabelRestController {
  LabelService labelService;

  @GetMapping("")
  public ResponseEntity<List<LabelDto>> getAllLabels() {
    List<LabelDto> labels = labelService.getAll();
    return ResponseEntity.ok(labels);
  }

  @GetMapping("/{id}")
  public ResponseEntity<LabelDto> getLabelById(@PathVariable Long id) {
    LabelDto label = labelService.getById(id);
    return ResponseEntity.ok(label);
  }

  @PostMapping("/create")
  public ResponseEntity<?> createLabel(@RequestPart("name") String name) {
    try {
      LabelData labelData = new LabelData(name);
      labelService.create(labelData);
      return ResponseEntity.status(HttpStatus.OK).build();
    } catch (RuntimeException e) {
      return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body(e.getMessage());
    }
  }

  @PostMapping("/update")
  public ResponseEntity<?> updateLabel(@RequestPart("id") Long id,
                                       @RequestPart("name") String name) {
    try {
      LabelData labelData = new LabelData(name);
      labelService.update(id, labelData);
      return ResponseEntity.ok().build();
    } catch (RuntimeException e) {
      return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body(e.getMessage());
    }
  }

  @PostMapping("/delete")
  public ResponseEntity<?> deleteLabel(@RequestPart Long id) {
    try {
      labelService.delete(id);
      return ResponseEntity.ok().build();
    } catch (RuntimeException e) {
      return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body(e.getMessage());
    }
  }
}
