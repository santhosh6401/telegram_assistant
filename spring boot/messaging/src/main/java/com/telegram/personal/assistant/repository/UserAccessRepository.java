package com.telegram.personal.assistant.repository;

import com.telegram.personal.assistant.model.entity.UserAccess;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccessRepository extends MongoRepository<UserAccess, String> {
    Optional<UserAccess> findByUserNameAndPasswordAndActive(String userName, String password, boolean active);
}
