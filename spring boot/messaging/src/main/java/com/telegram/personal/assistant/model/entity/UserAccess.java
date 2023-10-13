package com.telegram.personal.assistant.model.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "user-access")
public class UserAccess {
    private String userName;
    private String password;
    private boolean active;
}
