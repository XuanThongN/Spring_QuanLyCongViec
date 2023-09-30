package com.xuanthongn.spring_quanlycongviec.services;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IGenericService<T> {
    List<T> findAll();
    T save(T entity);
    T findById(long id);
    void delete(T entity);
    void deleteById(long id);
    long count();
}
