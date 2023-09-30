package com.xuanthongn.spring_quanlycongviec.services;

import com.xuanthongn.spring_quanlycongviec.entities.Task;
import com.xuanthongn.spring_quanlycongviec.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TaskService implements IGenericService<Task> {

    @Autowired
    private TaskRepository repository;

    @Override
    public List<Task> findAll() {
        return repository.findAll();
    }

    @Override
    public Task save(Task entity) {
        return repository.save(entity);
    }

    @Override
    public Task findById(long id) {
        return repository.findById(id).get();
    }

    @Override
    public void delete(Task entity) {
        repository.delete(entity);
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }

    @Override
    public long count() {
        return repository.count();
    }
}
