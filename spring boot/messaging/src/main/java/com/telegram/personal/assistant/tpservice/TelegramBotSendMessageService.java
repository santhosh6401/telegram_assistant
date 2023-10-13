package com.telegram.personal.assistant.tpservice;

import com.telegram.personal.assistant.model.entity.MessageLedger;
import com.telegram.personal.assistant.repository.MessageLedgerRepository;
import com.telegram.personal.assistant.utils.HelperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

import static com.telegram.personal.assistant.constant.AppConstant.*;

@Service
@RequiredArgsConstructor
public class TelegramBotSendMessageService {
    private final HelperUtils utils;

    private final RestTemplate restTemplate = new RestTemplate();


    private final MessageLedgerRepository messageLedgerRepository;

    public String sendMessage(String text, String chatId) {


        String apiUrl = String.format(TELEGRAM_SEND_MESSAGE_URL, TELEGRAM_BOT_TOKEN, chatId, text);

        MessageLedger messageLedger = new MessageLedger();
        messageLedger.setHistoryId(utils.generateId("TL"));
        messageLedger.setReceiverId(chatId);
        messageLedger.setMessageContent(String.format("sender : %s,\ntext : %s\n", chatId, text));
        messageLedger.setCreatedOn(LocalDateTime.now());

        try {
            restTemplate.getForObject(apiUrl, String.class);
            messageLedger.setStatus(SUCCESS);
            messageLedgerRepository.save(messageLedger);
            return SUCCESS;
        } catch (Exception e) {
            messageLedger.setStatus(FAILED);
            messageLedgerRepository.save(messageLedger);
            return FAILED + " " + e.getMessage();
        }

    }
}
