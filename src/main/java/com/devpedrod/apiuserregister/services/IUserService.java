package com.devpedrod.apiuserregister.services;

import com.devpedrod.apiuserregister.domain.enums.Status;

public interface IUserService {
    void updateStatus(Long id, Status status);
}
