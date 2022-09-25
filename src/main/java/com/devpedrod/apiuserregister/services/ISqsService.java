package com.devpedrod.apiuserregister.services;

import com.devpedrod.apiuserregister.dto.UpdateStatusDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ISqsService {
    void updateStatusListener(String status) throws JsonProcessingException;
    void sqsSendStatus(UpdateStatusDto status);
}
