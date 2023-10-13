package com.telegram.personal.assistant.repository;

import com.telegram.personal.assistant.model.entity.MessageContentConfig;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageContentConfigRepository extends MongoRepository<MessageContentConfig, String> {
    Optional<MessageContentConfig> findByName(String name);
}
