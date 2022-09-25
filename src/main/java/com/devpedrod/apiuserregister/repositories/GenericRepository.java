package com.devpedrod.apiuserregister.repositories;

import com.devpedrod.apiuserregister.domain.DomainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenericRepository<T extends DomainEntity> extends JpaRepository<T, Long> {
}
