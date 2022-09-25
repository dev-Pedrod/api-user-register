package com.devpedrod.apiuserregister.facade;

import com.devpedrod.apiuserregister.domain.DomainEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IFacade {
    void save(DomainEntity object);
    void delete(Long id, String className);
    void disable(Long id, String className);
    void update(DomainEntity object);
    Page<DomainEntity> getAll(Pageable pageable, String className);
    DomainEntity getById(Long id, String className);
}
