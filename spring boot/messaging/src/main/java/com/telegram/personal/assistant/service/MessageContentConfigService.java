package com.telegram.personal.assistant.service;

import com.telegram.personal.assistant.constant.AppConstant;
import com.telegram.personal.assistant.model.entity.MessageContentConfig;
import com.telegram.personal.assistant.model.request.MessageContentConfigRequest;
import com.telegram.personal.assistant.model.response.CommonResponse;
import com.telegram.personal.assistant.model.response.MessageContentConfigResponse;
import com.telegram.personal.assistant.repository.MessageContentConfigRepository;
import com.telegram.personal.assistant.utils.HelperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MessageContentConfigService {

    private final MessageContentConfigRepository repository;

    private final HelperUtils utils;

    private final MongoTemplate mongoTemplate;

    public MessageContentConfigResponse createMessageContent(MessageContentConfigRequest request, String uniqueInteractionId) {
        MessageContentConfigResponse response = new MessageContentConfigResponse();

        if (Objects.isNull(request.getName()) || Objects.isNull(request.getMessageContent())) {
            response.setResponse(AppConstant.FAILED + " name | credentials | messaging channel is missing .... ");
            return response;
        }

        Optional<MessageContentConfig> contentConfigOptional = repository.findByName(request.getName());

        if (contentConfigOptional.isPresent()) {
            response.setResponse(AppConstant.FAILED + " content config is already exists .... ");
            return response;
        }

        BeanUtils.copyProperties(request, response);
        try {
            MessageContentConfig contentConfig = new MessageContentConfig();
            contentConfig.setId(utils.generateId("MC"));
            BeanUtils.copyProperties(request, contentConfig);
            contentConfig.setAudit(utils.createAudit(uniqueInteractionId));
            contentConfig.setStatusLifeCycles(utils.upsertStatusLifeCycles(" new message content created for the  message content " + request.getMessageContent(), contentConfig.getStatusLifeCycles()));
            repository.save(contentConfig);

            response.setResponse(AppConstant.SUCCESS);
        } catch (Exception e) {
            response.setResponse(AppConstant.FAILED + e.getMessage());
        }
        return response;
    }

    public MessageContentConfigResponse updateMessageContent(MessageContentConfigRequest request, String uniqueInteractionId) {
        MessageContentConfigResponse response = new MessageContentConfigResponse();

        if (Objects.isNull(request.getName()) || Objects.isNull(request.getMessageContent())) {
            response.setResponse(AppConstant.FAILED + " name | credentials  is missing .... ");
            return response;
        }
        Optional<MessageContentConfig> contentConfigOptional = repository.findByName(request.getName());

        if (contentConfigOptional.isEmpty()) {
            response.setResponse(AppConstant.FAILED + " content config is not exists .... ");
            return response;
        }

        BeanUtils.copyProperties(request, response);
        try {
            MessageContentConfig contentConfig = contentConfigOptional.get();
            BeanUtils.copyProperties(request, contentConfig);
            contentConfig.setAudit(utils.updateAudit(uniqueInteractionId, contentConfig.getAudit()));
            contentConfig.setStatusLifeCycles(utils.upsertStatusLifeCycles("message config updated for the  message content " + request.getMessageContent(), contentConfig.getStatusLifeCycles()));
            repository.save(contentConfig);

            response.setResponse(AppConstant.SUCCESS);
        } catch (Exception e) {
            response.setResponse(AppConstant.FAILED + e.getMessage());
        }
        return response;
    }

    public CommonResponse deleteMessageContent(String name) {
        CommonResponse response = new CommonResponse();
        if (Objects.isNull(name)) {
            response.setResponse(AppConstant.FAILED + " name is missing .... ");
            return response;
        }
        Optional<MessageContentConfig> contentConfigOptional = repository.findByName(name);
        if (contentConfigOptional.isEmpty()) {
            response.setResponse(AppConstant.FAILED + " message config is not present for this name .... ");
            return response;
        }
        try {
            repository.delete(contentConfigOptional.get());
            response.setResponse(AppConstant.SUCCESS);
        } catch (Exception e) {
            response.setResponse(AppConstant.FAILED + e.getMessage());
        }
        return response;
    }

    public List<MessageContentConfigResponse> getMessageContent(String name) {
        Query query = new Query();
        if (Objects.nonNull(name)) {
            query.addCriteria(Criteria.where("name").is(name));
        }

        List<MessageContentConfig> contentConfigs = mongoTemplate.find(query, MessageContentConfig.class);

        if (contentConfigs.isEmpty()) {
            MessageContentConfigResponse response = MessageContentConfigResponse.builder()
                    .response(AppConstant.FAILED + " no record found")
                    .build();
            return Collections.singletonList(response);
        }

        List<MessageContentConfigResponse> responses = new ArrayList<>();

        for (MessageContentConfig contentResponse : contentConfigs) {
            MessageContentConfigResponse response = new MessageContentConfigResponse();
            BeanUtils.copyProperties(contentResponse, response);
            responses.add(response);
        }
        return responses;
    }
}
