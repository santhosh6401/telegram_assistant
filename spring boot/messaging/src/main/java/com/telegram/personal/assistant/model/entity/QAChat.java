package com.telegram.personal.assistant.model.entity;

import com.telegram.personal.assistant.model.common.Audit;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "query_answer")
public class QAChat {
    @Id
    private String id;
    private String question;
    private String answer;
    private Audit audit;
}
