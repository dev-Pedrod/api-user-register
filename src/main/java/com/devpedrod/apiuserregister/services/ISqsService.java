package com.devpedrod.apiuserregister.services;

import com.devpedrod.apiuserregister.domain.enums.Status;

public interface ISqsService {
    void  sqsListener(String status);
    void sqsSendStatus(String status);
}
