package com.devpedrod.apiuserregister.services.impl;

import com.devpedrod.apiuserregister.domain.enums.Status;
import com.devpedrod.apiuserregister.services.ISqsService;
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

    @Override
    @JmsListener(destination = "atualizar-status-usuario")
    public void sqsListener(String statusContent) {
        try {
            log.info("status received = {}", statusContent);
            Status status = Status.valueOf(statusContent);
        }catch (Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    @Async
    public void sqsSendStatus(String status) {
        try{
            jmsTemplate.convertAndSend("atualizar-status-usuario", status);
            log.info("status send = {}", status);
        }catch (Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }
}
