package com.basket.BasketballSystem.generics;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public abstract class GenericService<T,ID> {

    @Autowired
    private  GenericRepository<T, ID> repository;

    public List<T> readAll() {
        return repository.findAll();
    }
    public List<T> readAll(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<T> resultPage = repository.findAll(pageable);
        return resultPage.getContent();
    }

    public long count() {
        return repository.count();
    }

    public T insert(T entity) {
        return this.repository.save(entity);
    }

    public T getById(ID id) {
        return this.repository.findById(id).orElseThrow(()-> new EntityNotFoundException("Entity not found"));
    }

    public T update(T entity, ID id) {
        T existingEntity = getById(id);
        updateFields(entity, existingEntity);
        return repository.save(existingEntity);
    }
    protected abstract void updateFields(T source, T target);

}
