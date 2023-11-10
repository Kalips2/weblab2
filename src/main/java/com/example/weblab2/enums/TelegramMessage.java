package com.example.weblab2.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TelegramMessage {
  WELCOME_MESSAGE("Вітаю у моєму боті!"),
  HELP_MESSAGE("/labels - отримати інформацію про лейбли"),
  DEFAULT_MESSAGE("Такої команди немає! Введіть /help для просмотра можливих команд."),
  SUCCESS_MESSAGE("Відповідь без помилок."),
  LABELS_EMPTY("Ніякої інформації про лейбли!"),
  ALBUM_INPUT("Введіть айді альбому для просмотру:"),
  ERROR_MESSAGE("Помилка під час відповіді");

  private final String message;
}
