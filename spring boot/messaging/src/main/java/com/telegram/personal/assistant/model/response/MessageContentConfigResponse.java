package com.telegram.personal.assistant.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageContentConfigResponse {
    private String name;
    private Map<String, String> messageContent;
    private String response;
}
