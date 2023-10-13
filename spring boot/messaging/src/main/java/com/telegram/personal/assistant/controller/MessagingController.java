package com.telegram.personal.assistant.controller;

import com.telegram.personal.assistant.model.request.DynamicValueRequest;
import com.telegram.personal.assistant.model.response.CommonResponse;
import com.telegram.personal.assistant.service.MessagingService;
import com.telegram.personal.assistant.utils.UserValidation;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Messaging", value = "Messaging")
@Slf4j
@RestController
@RequestMapping
public class MessagingController {

    @Autowired
    private UserValidation userValidation;

    @Autowired
    private MessagingService messagingService;

    @PostMapping("/telegram")
    public CommonResponse sendTelegramMessage(@RequestHeader String uniqueInteractionId,
                                              @RequestParam(name = "chat-Id") String chatId,
                                              @RequestParam(name = "content-name", required = false) String messagingContentName,
                                              @RequestHeader(name = "user-name") String userName,
                                              @RequestHeader(name = "password") String password,
                                              @RequestParam(name = "custom-message", required = false) String customMessage,
                                              @RequestBody(required = false) DynamicValueRequest dynamicValues) throws Exception {
        log.info("interactionId :: [{}] chat-id : {} content-name : {} customMessage : {} ", uniqueInteractionId, chatId, messagingContentName, customMessage);
        userValidation.validate(userName, password);
        return messagingService.sendTextMessage(chatId, messagingContentName, customMessage, dynamicValues);
    }
}
