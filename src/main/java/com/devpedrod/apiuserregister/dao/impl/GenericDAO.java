package com.devpedrod.apiuserregister.dao.impl;

import com.devpedrod.apiuserregister.dao.IGenericDAO;
import com.devpedrod.apiuserregister.exceptions.ObjectNotFoundException;
import com.devpedrod.apiuserregister.domain.DomainEntity;
import com.devpedrod.apiuserregister.repositories.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.function.Function;

import static java.time.LocalDateTime.now;

@Service
public abstract class GenericDAO<T extends DomainEntity> implements IGenericDAO<T> {

    @Autowired
    private GenericRepository<T> genericRepository;

    @Override
    public void save(T object) {
        genericRepository.save(object);
    }

    @Override
    public void save(T object, Function<T, ?> function) {
        function.apply(object);
        genericRepository.save(object);
    }

    @Override
    public void delete(Long id) {
        genericRepository.delete(getById(id));
    }

    @Override
    public void disable(Long id) {
        T object = getById(id);
        object.setDisabledAt(now());
        genericRepository.save(object);
    }

    @Override
    public void disable(Long id, Function<T, ?> function) {
        T object = getById(id);
        object.setDisabledAt(now());
        function.apply(object);
        genericRepository.save(object);
    }

    @Override
    public T update(T object) {
        return genericRepository.saveAndFlush(object);
    }

    @Override
    public T update(T object, Function<T, ?> function) {
        function.apply(object);
        return genericRepository.saveAndFlush(object);
    }

    @Override
    public Page<T> getAll(Pageable pageable) {
        return genericRepository.findAll(pageable);
    }

    @Override
    public T getById(Long id) {
       return genericRepository.findById(id)
               .orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado, id: "+id));
    }
}
