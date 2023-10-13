package com.telegram.personal.assistant.model.request;

import lombok.Data;

import java.util.Map;

@Data
public class MessageContentConfigRequest {
    private String name;
    private Map<String, String> messageContent;
}
