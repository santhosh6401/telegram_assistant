package com.telegram.personal.assistant.repository;

import com.telegram.personal.assistant.model.entity.MessageLedger;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageLedgerRepository extends MongoRepository<MessageLedger, String> {
}
