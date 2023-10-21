package com.example.weblab2.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Exceptions {
  ALBUM_IS_NOT_FOUND("Album with this id isn't present!"),
  ARTIST_IS_NOT_FOUND("Artist with this id isn't present!"),
  LABEL_IS_NOT_FOUND("Label with this id isn't present!"),
  SONG_IS_NOT_FOUND("Song with this id isn't present!");

  private final String message;
}
