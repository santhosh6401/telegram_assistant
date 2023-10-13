package com.telegram.personal.assistant.controller;


import com.telegram.personal.assistant.model.request.MessageContentConfigRequest;
import com.telegram.personal.assistant.model.response.CommonResponse;
import com.telegram.personal.assistant.model.response.MessageContentConfigResponse;
import com.telegram.personal.assistant.service.MessageContentConfigService;
import com.telegram.personal.assistant.utils.UserValidation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Message Content Configuration", value = "Message Content Configuration")
@Slf4j
@RestController
@RequestMapping("/message-content")
public class MessageContentConfigController {                       /* for the scheduled future reference */

    @Autowired
    private UserValidation userValidation;

    @Autowired
    private MessageContentConfigService messageContentConfigService;

    @PostMapping
    public MessageContentConfigResponse createMessageContentConfig(@RequestHeader String uniqueInteractionId,
                                                                   @RequestBody MessageContentConfigRequest request,
                                                                   @RequestHeader(name = "user-name") String userName,
                                                                   @RequestHeader(name = "password") String password) throws Exception {
        log.info("interactionId :: [{}] request : {} create the content config", uniqueInteractionId, request);
        userValidation.validate(userName, password);
        return messageContentConfigService.createMessageContent(request, uniqueInteractionId);
    }

    @PutMapping
    public MessageContentConfigResponse updateMessageContentConfig(@RequestHeader String uniqueInteractionId,
                                                                   @RequestBody MessageContentConfigRequest request,
                                                                   @RequestHeader(name = "user-name") String userName,
                                                                   @RequestHeader(name = "password") String password) throws Exception {
        log.info("interactionId :: [{}] request : {} update the content config", uniqueInteractionId, request);
        userValidation.validate(userName, password);
        return messageContentConfigService.updateMessageContent(request, uniqueInteractionId);
    }

    @DeleteMapping
    public CommonResponse deleteMessageContentConfig(@RequestHeader String uniqueInteractionId,
                                                     @RequestParam String name,
                                                     @RequestHeader(name = "user-name") String userName,
                                                     @RequestHeader(name = "password") String password) throws Exception {
        log.info("interactionId :: [{}] content-name : {} delete the content config", uniqueInteractionId, name);
        userValidation.validate(userName, password);
        return messageContentConfigService.deleteMessageContent(name);
    }

    @GetMapping
    public List<MessageContentConfigResponse> getMessageContentConfig(
            @RequestHeader String uniqueInteractionId,
            @RequestParam(required = false, value = "content-name", name = "content-name") @ApiParam(name = "content-name") String name,
            @RequestHeader(name = "user-name") String userName,
            @RequestHeader(name = "password") String password) throws Exception {
        log.info("interactionId :: [{}] content-name : {}  get the content config", uniqueInteractionId, name);
        userValidation.validate(userName, password);
        return messageContentConfigService.getMessageContent(name);
    }
}
