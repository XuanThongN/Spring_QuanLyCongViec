package com.xuanthongn.spring_quanlycongviec.services;

import com.xuanthongn.spring_quanlycongviec.dto.task.CreateTaskDto;
import com.xuanthongn.spring_quanlycongviec.dto.task.TaskDto;
import com.xuanthongn.spring_quanlycongviec.dto.task.UpdateTaskDto;
import com.xuanthongn.spring_quanlycongviec.entities.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ITaskService {
    List<TaskDto> findAll();
    TaskDto save(CreateTaskDto entity);
    TaskDto update(UpdateTaskDto entity);
    TaskDto findById(long id);
    void delete(Task entity);
    void deleteById(long id);
    long count();
}
