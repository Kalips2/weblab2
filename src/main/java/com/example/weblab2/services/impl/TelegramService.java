package com.example.weblab2.services.impl;

import static com.example.weblab2.enums.TelegramMessage.ALBUM_INPUT;
import static com.example.weblab2.enums.TelegramMessage.DEFAULT_MESSAGE;
import static com.example.weblab2.enums.TelegramMessage.ERROR_MESSAGE;
import static com.example.weblab2.enums.TelegramMessage.HELP_MESSAGE;
import static com.example.weblab2.enums.TelegramMessage.LABELS_EMPTY;
import static com.example.weblab2.enums.TelegramMessage.SUCCESS_MESSAGE;
import static com.example.weblab2.enums.TelegramMessage.WELCOME_MESSAGE;

import com.example.weblab2.dto.AlbumDto;
import com.example.weblab2.dto.LabelDto;
import com.example.weblab2.entities.TelegramLogs;
import com.example.weblab2.repositories.TelegramLogsRepository;
import com.example.weblab2.services.AlbumService;
import com.example.weblab2.services.LabelService;
import jakarta.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Service
@Getter
@RequiredArgsConstructor
public class TelegramService extends TelegramLongPollingBot {
  private final List<BotCommand> botCommands;
  private final TelegramLogsRepository telegramLogsRepository;
  private final LabelService labelService;
  private final AlbumService albumService;

  private final Set<Integer> awaitingChats = new HashSet<>();

  @Value("${telegram.bot.name}")
  private String name;

  @Value("${telegram.bot.token}")
  private String token;

  @PostConstruct
  private void setCommandsToMyBot() {
    try {
      execute(new SetMyCommands(botCommands, new BotCommandScopeDefault(), null));
    } catch (TelegramApiException e) {
      log.error("Telegram API error occurred: " + e.getMessage());
    }
  }

  @Override
  public String getBotUsername() {
    return name;
  }

  @Override
  public String getBotToken() {
    return token;
  }

  @Override
  public void onUpdateReceived(Update update) {
    if (update.hasMessage() && update.getMessage().hasText()) {
      final var message = update.getMessage();
      final var text = message.getText();
      final var chatId = message.getChatId().intValue();

      if (awaitingChats.contains(chatId)) {
        getAlbumInfo(message);
        awaitingChats.remove(chatId);
      } else {
        switch (text) {
          case "/start" -> answer(message, WELCOME_MESSAGE.getMessage());
          case "/help" -> answer(message, HELP_MESSAGE.getMessage());
          case "/labels" -> getLabels(message);
          case "/album" -> {
            answer(message, ALBUM_INPUT.getMessage());
            awaitingChats.add(chatId);
          }
          default -> answer(message, DEFAULT_MESSAGE.getMessage());
        }
      }
    }
  }

  public void answer(Message message, String answer) {
    SendMessage sendMessage = new SendMessage(String.valueOf(message.getChatId()), answer);
    try {
      execute(sendMessage);
      saveLogs(message, SUCCESS_MESSAGE.getMessage());
    } catch (TelegramApiException e) {
      saveLogs(message, ERROR_MESSAGE.getMessage());
    }
  }

  public void saveLogs(Message message, String response) {
    var logData = TelegramLogs.builder()
        .chatId(message.getChatId())
        .firstName(message.getChat().getFirstName())
        .lastName(message.getChat().getLastName())
        .username(message.getChat().getUserName())
        .request(message.getText())
        .response(response)
        .build();
    telegramLogsRepository.saveAndFlush(logData);
  }

  private void getLabels(Message message) {
    final List<LabelDto> payload = labelService
        .getAll();
    String answer = getAnswerFromPayload(payload);
    answer(message, answer);
  }

  private String getAnswerFromPayload(List<LabelDto> payload) {
    if (Objects.isNull(payload) || payload.isEmpty()) {
      return LABELS_EMPTY.getMessage();
    }
    final StringBuilder sb = new StringBuilder();
    sb.append("Labels: [\n");
    payload.forEach(p -> {
      sb.append(p.toString());
      sb.append("\n");
    });
    sb.append("]");
    return sb.toString();
  }

  private void getAlbumInfo(Message message) {
    try {
      Long albumId = Long.valueOf(message.getText());
      AlbumDto albumDto = albumService.getById(albumId);
      sendAlbumInfo(message, albumDto);
      saveLogs(message, SUCCESS_MESSAGE.getMessage());
    } catch (Exception e) {
      answer(message, ERROR_MESSAGE.getMessage() + "\n" + e.getMessage());
      saveLogs(message, ERROR_MESSAGE.getMessage() + "\n" + e.getMessage());
    }
  }

  @SneakyThrows
  private void sendAlbumInfo(Message message, AlbumDto albumDto) {
    SendPhoto sendPhoto = new SendPhoto();
    sendPhoto.setChatId(String.valueOf(message.getChatId()));
    sendPhoto.setPhoto(new InputFile(new ByteArrayInputStream(albumDto.getPhoto()), "album.jpg"));

    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(String.valueOf(message.getChatId()));
    sendMessage.setText("Title: " + albumDto.getTitle() + "\nRelease Date: " + albumDto.releaseDateToString());
    execute(sendPhoto);
    execute(sendMessage);
  }

}
