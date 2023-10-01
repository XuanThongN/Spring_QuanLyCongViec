package com.xuanthongn.spring_quanlycongviec.services;

import com.xuanthongn.spring_quanlycongviec.dto.task.CreateTaskDto;
import com.xuanthongn.spring_quanlycongviec.entities.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ITaskService {
    List<Task> findAll();
    Task save(CreateTaskDto entity);
    Task findById(long id);
    void delete(Task entity);
    void deleteById(long id);
    long count();
}
