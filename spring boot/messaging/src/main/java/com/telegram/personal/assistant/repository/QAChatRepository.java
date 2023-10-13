package com.telegram.personal.assistant.repository;

import com.telegram.personal.assistant.model.entity.QAChat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QAChatRepository extends MongoRepository<QAChat, String> {

}
