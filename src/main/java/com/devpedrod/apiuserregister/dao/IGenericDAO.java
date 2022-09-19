package com.devpedrod.apiuserregister.dao;

import com.devpedrod.apiuserregister.domain.DomainEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.function.Function;

public interface IGenericDAO<T extends DomainEntity>{
    void save(T object);
    void save(T object, Function<T, T> function);
    void delete(Long id);
    void delete(Long id, Function<T, T> function);
    void disable(Long id);
    void disable(Long id, Function<T, T> function);
    void update(T object);
    void update(T object, Function<T, T> function);
    Page<T> getAll(Pageable pageable);
    T getById(Long id);
}
