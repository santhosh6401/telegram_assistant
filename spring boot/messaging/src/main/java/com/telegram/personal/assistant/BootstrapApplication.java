package com.telegram.personal.assistant;

import com.telegram.personal.assistant.config.TelegramBotAutoChatConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.telegram.telegrambots.meta.ApiConstants;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@SpringBootApplication(scanBasePackages = {"com.telegram.personal.assistant"})
@EnableMongoRepositories("com.telegram.personal.assistant.repository")
public class BootstrapApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootstrapApplication.class, args);

        /* telegram bot auto-chat configuration*/

        TelegramBotAutoChatConfig telegramBot = new TelegramBotAutoChatConfig();
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(telegramBot);
        } catch (TelegramApiException e) {
            log.error("telegram auto-chat config exception : [{}] ", e.getMessage());
        }

    }

}
