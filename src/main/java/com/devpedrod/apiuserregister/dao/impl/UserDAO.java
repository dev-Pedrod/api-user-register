package com.devpedrod.apiuserregister.dao.impl;

import com.devpedrod.apiuserregister.dao.IUserDAO;
import com.devpedrod.apiuserregister.domain.User;
import org.springframework.stereotype.Service;

@Service
public class UserDAO extends GenericDAO<User> implements IUserDAO {
}
