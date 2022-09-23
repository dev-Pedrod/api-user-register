package com.devpedrod.apiuserregister.dao;

import com.devpedrod.apiuserregister.domain.User;

public interface IUserDAO extends IGenericDAO<User>{
    User getByCpf(String cpf);
}
