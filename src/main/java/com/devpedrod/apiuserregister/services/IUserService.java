package com.devpedrod.apiuserregister.services;

import com.devpedrod.apiuserregister.domain.User;
import com.devpedrod.apiuserregister.domain.enums.Status;

import java.util.Set;

public interface IUserService {
    void updateStatus(Long id, Status status);
    void addPermisions(User user, Set<Long> PermisionsIds);
}
