package com.devpedrod.apiuserregister.services.impl;

import com.devpedrod.apiuserregister.dao.IUserDAO;
import com.devpedrod.apiuserregister.dao.impl.PermissionDAO;
import com.devpedrod.apiuserregister.domain.Permission;
import com.devpedrod.apiuserregister.domain.User;
import com.devpedrod.apiuserregister.domain.enums.Status;
import com.devpedrod.apiuserregister.services.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.devpedrod.apiuserregister.domain.enums.Status.BLOCKED;

@Service
@Slf4j
public class UserService implements IUserService {

    @Autowired
    private IUserDAO userDAO;

    @Autowired
    private PermissionDAO permissionDAO;

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

    @Override
    public void addPermisions(User user, Set<Long> permisionsIds) {
        if(permisionsIds.isEmpty()) return;
        for(Long id : permisionsIds ){
            Permission permission = permissionDAO.getById(id);
            permission.getUsers().add(user);
            user.getPermissions().add(permission);
        }
        userDAO.save(user);
    }
}
