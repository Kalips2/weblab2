package com.example.weblab2.controllers.rest;

import com.example.weblab2.data.LabelData;
import com.example.weblab2.dto.LabelDto;
import com.example.weblab2.dto.SearchDto;
import com.example.weblab2.services.LabelService;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

  @GetMapping("/pagination")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<List<LabelDto>> getAllLabelsPagination(@RequestBody(required = false)
                                                               SearchDto filter) {
    List<LabelDto> labels = labelService.getAll(filter);
    return ResponseEntity.ok(labels);
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN') or hasRole('PREMIUM') or hasRole('USER')")
  public ResponseEntity<LabelDto> getLabelById(@PathVariable Long id) {
    LabelDto label = labelService.getById(id);
    return ResponseEntity.ok(label);
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<LabelDto> getLabelByName(@PathVariable String name) {
    LabelDto label = labelService.getByName(name).get();
    return ResponseEntity.ok(label);
  }

  @GetMapping("/findByName/{name}")
  public @ResponseBody List<String> getLabelsByName(@PathVariable String name) {
    return labelService.getNamesThatContain(name);
  }

  @GetMapping("/albums/{id}")
  public ResponseEntity<Long> getAmountOfAlbumsOfLabel(@PathVariable Long id) {
    Long count = labelService.getAmountOfAlbums(id);
    return ResponseEntity.ok(count);
  }

  @PostMapping("/create")
  @PreAuthorize("hasRole('ADMIN') or hasRole('PREMIUM') or hasRole('USER')")
  public ResponseEntity<?> createLabel(@RequestParam String name,
                                       @RequestParam String coordinates) {
    try {
      LabelData labelData = new LabelData(name, coordinates);
      Long id = labelService.create(labelData);
      return ResponseEntity.status(HttpStatus.OK).body(id);
    } catch (RuntimeException e) {
      return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body(e.getMessage());
    }
  }

  @PutMapping("/update")
  @PreAuthorize("hasRole('ADMIN') or hasRole('PREMIUM') or hasRole('USER')")
  public ResponseEntity<?> updateLabel(@RequestParam Long id,
                                       @RequestParam String name,
                                       @RequestParam String coordinates) {
    try {
      LabelData labelData = new LabelData(name, coordinates);
      labelService.update(id, labelData);
      return ResponseEntity.ok().build();
    } catch (RuntimeException e) {
      return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body(e.getMessage());
    }
  }

  @DeleteMapping("/delete")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> deleteLabel(@RequestParam Long id) {
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
