package com.telegram.personal.assistant.config;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.telegram.personal.assistant.constant.AppConstant;
import com.telegram.personal.assistant.model.response.QAChatResponse;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Slf4j
@Configuration
public class TelegramBotAutoChatConfig extends TelegramLongPollingBot {

    @Bean
    public MongoCollection<Document> mongoCollection() {
        try {
            return MongoClients.create(AppConstant.ASSISTANT_DB_URL)
                    .getDatabase(AppConstant.ASSISTANT_DB_NAME)
                    .getCollection(AppConstant.ASSISTANT_COLLECTION);
        } catch (Exception ex) {
            return null;
        }
    }


    @Override
    public String getBotUsername() {
        return AppConstant.TELEGRAM_BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return AppConstant.TELEGRAM_BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            String chatId = message.getChatId().toString();
            if (message.hasText()) {
                log.info("incoming message : [{}]", message.getText());
                replyTextGenerator(message.getText(), chatId);
            } else {
                sendTextMessage("\uD83E\uDD72 ", chatId);
                sendTextMessage("I did not get you ! ,  \n\nAs of now i am able address only text formatted message ..... ", chatId);
            }
        }
    }

    private void sendTextMessage(String text, String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("telegram update and send reply getting exception : [{}] ", e.getMessage());
        }
    }

    public void replyTextGenerator(String key, String chatId) {
        try {

            String queryKey = key;
            queryKey = queryKey.endsWith("/all") ? queryKey.replace("/all", "") : queryKey;
            Document regQuery = new Document();
            regQuery.append("$regex", "^(?)" + Pattern.quote(queryKey));
            regQuery.append("$options", "i");

            Document findQuery = new Document();
            findQuery.append("question", regQuery);

            if (Objects.nonNull(mongoCollection())) {

                FindIterable<Document> iterable = mongoCollection().find(findQuery);
                List<QAChatResponse> responses = new ArrayList<>();
                for (Document document : iterable) {
                    responses.add(QAChatResponse.builder()
                            .question(document.getString("question"))
                            .answer(document.getString("answer"))
                            .build());
                }
                if (responses.isEmpty()) {

                    sendTextMessage("\uD83E\uDD37\uD83C\uDFFB\u200D♀", chatId);
                    sendTextMessage(" i don't have record related to your query , come again plz", chatId);

                } else if (responses.size() == 1) {
                    sendTextMessage(responses.get(0).getAnswer(), chatId);
                } else {
                    if (key.endsWith("/all")) {
                        StringBuilder result = new StringBuilder("results for the key " + queryKey + " \uD83D\uDC47\uD83C\uDFFB\n\n");
                        int init = 1;
                        for (QAChatResponse response : responses) {
                            result.append(String.format("%d : %s \n\n", init++, response.getAnswer()));
                        }
                        sendTextMessage(result.toString(), chatId);
                    } else {
                        sendTextMessage("\uD83D\uDE40", chatId);
                        sendTextMessage("i have a list of result for this key", chatId);
                        sendTextMessage("if you want to see all result \"/all\" is must in end with the key \n\n ( or ) \n\n give me more key for my findings", chatId);
                    }
                }
            }
        } catch (Exception ex) {
            log.info("something went wrong in mongo DB connection : {} ", ex.getMessage());
            sendTextMessage("i am unable to response now , something went wrong .....", chatId);
        }
    }


}



