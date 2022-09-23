package com.devpedrod.apiuserregister.services.impl;

import com.devpedrod.apiuserregister.dao.IUserDAO;
import com.devpedrod.apiuserregister.domain.User;
import com.devpedrod.apiuserregister.dto.UpdateStatusDto;
import com.devpedrod.apiuserregister.services.ISqsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SqsService implements ISqsService {

    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private IUserDAO userDAO;

    @Override
    @JmsListener(destination = "atualizar-status-usuario")
    public void updateStatusListener(String statusContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            UpdateStatusDto statusDto = objectMapper.readValue(statusContent, UpdateStatusDto.class);
            log.info("status received = {}", statusDto.getStatus());
            User user = userDAO.getById(statusDto.getUserId());
            user.setStatus(statusDto.getStatus());
            userDAO.save(user);
        }catch (Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    @Async
    public void sqsSendStatus(UpdateStatusDto status) {
        try{
            jmsTemplate.convertAndSend("atualizar-status-usuario", status.toJson());
            log.info("status send = {}", status);
        }catch (Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }
}
