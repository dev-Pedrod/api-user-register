package com.devpedrod.apiuserregister.dao.impl;

import com.devpedrod.apiuserregister.dao.IUserDAO;
import com.devpedrod.apiuserregister.domain.User;
import com.devpedrod.apiuserregister.exceptions.ObjectNotFoundException;
import com.devpedrod.apiuserregister.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDAO extends GenericDAO<User> implements IUserDAO {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User getByCpf(String cpf) {
        return userRepository.findByCpf(cpf)
                .orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado, CPF: "+ cpf));
    }
}
