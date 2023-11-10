package com.example.weblab2.controllers.mvc;

import com.example.weblab2.data.LabelData;
import com.example.weblab2.dto.LabelDto;
import com.example.weblab2.services.LabelService;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/labels")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LabelController {
  LabelService labelService;

  @GetMapping("")
  public String getAllLabels(Model model) {
    List<LabelDto> labels = labelService.getAll();
    model.addAttribute("labels", labels);
    return "labels";
  }

  @GetMapping("/find")
  public String getLabelByName(Model model) {
    return "findLabel";
  }

  @GetMapping("/map")
  public String getMap(Model model) {
    List<LabelDto> labels = labelService.getAll();
    model.addAttribute("labels", labels);
    return "map";
  }

  @GetMapping("/create")
  public String createLabel() {
    return "addLabel";
  }

  @PostMapping("/create")
  public String createLabel(@RequestParam String name,
                            @RequestParam String coordinates) {
    LabelData labelData = new LabelData(name, coordinates);
    labelService.create(labelData);
    return "redirect:/labels";
  }

  @GetMapping("/update")
  public String updateLabel(@RequestParam Long id,
                            Model model) {
    LabelDto label = labelService.getById(id);
    model.addAttribute("label", label);
    return "updateLabel";
  }

  @PostMapping("/update")
  public String updateLabel(@RequestParam Long id,
                            @RequestParam String name,
                            @RequestParam String coordinates) {
    LabelData labelData = new LabelData(name, coordinates);
    labelService.update(id, labelData);
    return "redirect:/labels";
  }

  @PostMapping("/delete")
  public String deleteLabel(@RequestParam Long id) {
    labelService.delete(id);
    return "redirect:/labels";
  }
}
