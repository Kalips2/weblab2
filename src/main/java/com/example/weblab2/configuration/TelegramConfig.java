package com.example.weblab2.configuration;

import com.example.weblab2.services.impl.TelegramService;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class TelegramConfig {
  @Bean
  public List<BotCommand> botCommands() {
    return List.of(
        new BotCommand("/start", "Welcome"),
        new BotCommand("/help", "Help"),
        new BotCommand("/labels", "Get labels"),
        new BotCommand("/album", "Get album by id"));
  }

  @Bean
  public TelegramBotsApi telegramBotsApi(TelegramService telegramService) throws
      TelegramApiException {
    var telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
    telegramBotsApi.registerBot(telegramService);
    return telegramBotsApi;
  }
}
