package com.telegram.personal.assistant.model.request;

import lombok.Data;

import java.util.Map;

@Data
public class DynamicValueRequest {
    private Map<String, String> dynamicValue;
}
