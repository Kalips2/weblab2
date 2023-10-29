package com.example.weblab2.controllers.mvc;

import com.example.weblab2.dto.LabelDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/premium")
public class SecurityController {

  @GetMapping("")
  @PreAuthorize("hasRole('PREMIUM')")
  public String getAllLabels() {
    return "premiumPage";
  }
}
