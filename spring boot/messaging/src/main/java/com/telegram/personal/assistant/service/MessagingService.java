package com.telegram.personal.assistant.service;

import com.telegram.personal.assistant.constant.AppConstant;
import com.telegram.personal.assistant.model.entity.MessageContentConfig;
import com.telegram.personal.assistant.model.request.DynamicValueRequest;
import com.telegram.personal.assistant.model.response.CommonResponse;
import com.telegram.personal.assistant.repository.MessageContentConfigRepository;
import com.telegram.personal.assistant.tpservice.TelegramBotSendMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessagingService {

    private final MessageContentConfigRepository repository;

    private final TelegramBotSendMessageService telegramBotSendMessageService;


    public CommonResponse sendTextMessage(String chatId, String messagingContentName, String customMessage, DynamicValueRequest dynamicValuesRequest) {

        CommonResponse response = new CommonResponse();
        String text;

        if (Objects.nonNull(customMessage)) {
            text = customMessage;
        } else {

            if (Objects.isNull(messagingContentName)) {
                response.setResponse(AppConstant.FAILED + " content name is empty .... ");
                return response;
            }

            Optional<MessageContentConfig> contentConfigOptional = repository.findByName(messagingContentName);

            if (contentConfigOptional.isEmpty()) {
                response.setResponse(AppConstant.FAILED + " content config is not exists .... ");
                return response;
            }

            MessageContentConfig contentConfig = contentConfigOptional.get();

            if (Objects.isNull(contentConfig.getMessageContent().get("text"))) {
                response.setResponse(AppConstant.FAILED + " content config is required field is missing .... ");
                return response;
            }
            text = contentConfig.getMessageContent().get("text");

            if (Objects.nonNull(dynamicValuesRequest) && Objects.nonNull(dynamicValuesRequest.getDynamicValue())) {
                for (Map.Entry<String, String> entry : dynamicValuesRequest.getDynamicValue().entrySet()) {
                    if (Objects.nonNull(entry.getKey()) && text.contains(entry.getKey()))
                        text = text.replace(entry.getKey(), entry.getValue());
                }
            }

        }
        String messageResult = telegramBotSendMessageService.sendMessage(text, chatId);
        response.setResponse(messageResult);
        return response;
    }
}
