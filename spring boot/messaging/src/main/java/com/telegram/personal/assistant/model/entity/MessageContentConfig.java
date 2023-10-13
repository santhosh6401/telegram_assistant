package com.telegram.personal.assistant.model.entity;


import com.telegram.personal.assistant.model.common.Audit;
import com.telegram.personal.assistant.model.common.StatusLifeCycle;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Document("message-config")
public class MessageContentConfig {
    @Id
    private String id;
    private String name;
    private Map<String, String> messageContent;
    private List<StatusLifeCycle> statusLifeCycles = new ArrayList<>();
    private Audit audit;
}
