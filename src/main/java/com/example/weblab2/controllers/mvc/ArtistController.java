package com.example.weblab2.controllers.mvc;

import com.example.weblab2.data.ArtistData;
import com.example.weblab2.dto.ArtistDto;
import com.example.weblab2.services.ArtistService;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/artists")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ArtistController {
  ArtistService artistService;

  @GetMapping("")
  public String getAllArtists(Model model) {
    List<ArtistDto> artists = artistService.getAll();
    model.addAttribute("artists", artists);
    return "artists";
  }

  @GetMapping("/create")
  public String createArtist() {
    return "addArtist";
  }

  @PostMapping("/create")
  public String createArtist(@RequestParam String name,
                            @RequestParam String surname,
                            @RequestParam String dateOfBirth) {
    ArtistData artistData = new ArtistData(name, surname, dateOfBirth);
    artistService.create(artistData);
    return "redirect:/artists";
  }

  @GetMapping("/update")
  public String updateArtist(@RequestParam Long id,
                            Model model) {
    ArtistDto artist = artistService.getById(id);
    model.addAttribute("artist", artist);
    return "updateArtist";
  }

  @PostMapping("/update")
  public String updateArtist(@RequestParam Long id,
                            @RequestParam String name,
                            @RequestParam String surname,
                            @RequestParam String dateOfBirth) {
    ArtistData artistData = new ArtistData(name, surname, dateOfBirth);
    artistService.update(id, artistData);
    return "redirect:/artists";
  }

  @PostMapping("/delete")
  public String deleteArtist(@RequestParam Long id) {
    artistService.delete(id);
    return "redirect:/artists";
  }
}
