package com.devpedrod.apiuserregister.repositories;

import com.devpedrod.apiuserregister.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends GenericRepository<User> {
    Optional<User> findByCpf(String cpf);
}
