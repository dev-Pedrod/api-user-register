package com.devpedrod.apiuserregister.dao;

import com.devpedrod.apiuserregister.domain.DomainEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.function.Function;

public interface IGenericDAO<T extends DomainEntity>{
    void save(T object);
    void save(T object, Function<T, ?> function);
    void delete(Long id);
    void disable(Long id);
    void disable(Long id, Function<T, ?> function);
    T update(T object);
    T update(T object, Function<T, ?> function);
    Page<T> getAll(Pageable pageable);
    T getById(Long id);
}
