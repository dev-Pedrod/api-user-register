package com.devpedrod.apiuserregister.repositories;

import com.devpedrod.apiuserregister.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends GenericRepository<User> {
}
