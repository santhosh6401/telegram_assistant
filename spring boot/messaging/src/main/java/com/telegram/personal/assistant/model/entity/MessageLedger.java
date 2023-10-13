package com.telegram.personal.assistant.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Data
@Document(collection = "message-ledger")
public class MessageLedger {
    @Id
    private String historyId;
    private String receiverId;                  /* email id */
    private String status;
    private String messageContent;
    private LocalDateTime createdOn;
}
