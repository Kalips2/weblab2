package com.example.weblab2.controllers;

import com.example.weblab2.data.AlbumData;
import com.example.weblab2.dto.AlbumDto;
import com.example.weblab2.services.AlbumService;
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
@RequestMapping("/albums")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AlbumController {
  AlbumService albumService;

  @GetMapping("")
  public String getAllAlbums(Model model) {
    List<AlbumDto> albums = albumService.getAll();
    model.addAttribute("albums", albums);
    return "albums";
  }

  @GetMapping("/create")
  public String createAlbum() {
    return "addAlbum";
  }

  @PostMapping("/create")
  public String createAlbum(@RequestParam String title,
                            @RequestParam String data,
                            @RequestParam Long artist,
                            @RequestParam Long label,
                            @RequestParam MultipartFile file) {
    AlbumData albumData = new AlbumData(title, data, artist, label);
    albumService.create(albumData, file);
    return "redirect:/albums";
  }

  @GetMapping("/update")
  public String updateAlbum(@RequestParam Long id,
                            Model model) {
    AlbumDto album = albumService.getById(id);
    model.addAttribute("album", album);
    return "updateAlbum";
  }

  @PostMapping("/update")
  public String updateAlbum(@RequestParam Long id,
                            @RequestParam String title,
                            @RequestParam String data,
                            @RequestParam Long artist,
                            @RequestParam Long label,
                            @RequestParam MultipartFile file) {
    AlbumData albumData = new AlbumData(title, data, artist, label);
    albumService.update(id, albumData, file);
    return "redirect:/albums";
  }

  @PostMapping("/delete")
  public String deleteAlbum(@RequestParam Long id) {
    albumService.delete(id);
    return "redirect:/albums";
  }
}
