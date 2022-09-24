package com.devpedrod.apiuserregister.services.impl;

import com.devpedrod.apiuserregister.dao.IUserDAO;
import com.devpedrod.apiuserregister.domain.User;
import com.devpedrod.apiuserregister.domain.enums.Status;
import com.devpedrod.apiuserregister.services.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.devpedrod.apiuserregister.domain.enums.Status.BLOCKED;

@Service
@Slf4j
public class UserService implements IUserService {

    @Autowired
    private IUserDAO userDAO;

    @Override
    public void updateStatus(Long id, Status status) {
        User user = userDAO.getById(id);
        user.setStatus(status);
        if(status == BLOCKED && user.getPermissions() != null) {
            user.getPermissions().forEach(permission -> permission.getUsers().remove(user));
            user.getPermissions().clear();
        }
        userDAO.save(user);
        log.info("User with id {} updated with {} status", id, status);
    }
}
