package com.example.weblab2.controllers.mvc;

import com.example.weblab2.data.SongData;
import com.example.weblab2.dto.SongDto;
import com.example.weblab2.enums.Genre;
import com.example.weblab2.services.SongService;
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
@RequestMapping("/songs")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SongController {
  SongService songService;

  @GetMapping("")
  public String getAllSongs(Model model) {
    List<SongDto> songs = songService.getAll();
    model.addAttribute("songs", songs);
    return "songs";
  }

  @GetMapping("/create")
  public String createSong() {
    return "addSong";
  }

  @PostMapping("/create")
  public String createSong(@RequestParam String title,
                           @RequestParam double duration,
                           @RequestParam Genre genre,
                           @RequestParam Long album) {
    SongData songData = new SongData(title, duration, genre, album);
    songService.create(songData);
    return "redirect:/songs";
  }

  @GetMapping("/update")
  public String updateSong(@RequestParam Long id,
                           Model model) {
    SongDto song = songService.getById(id);
    model.addAttribute("song", song);
    return "updateSong";
  }

  @PostMapping("/update")
  public String updateSong(@RequestParam Long id,
                           @RequestParam String title,
                           @RequestParam double duration,
                           @RequestParam Genre genre,
                           @RequestParam Long album) {
    SongData songData = new SongData(title, duration, genre, album);
    songService.update(id, songData);
    return "redirect:/songs";
  }

  @PostMapping("/delete")
  public String deleteSong(@RequestParam Long id) {
    songService.delete(id);
    return "redirect:/songs";
  }
}
