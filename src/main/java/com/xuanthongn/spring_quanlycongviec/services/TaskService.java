package com.xuanthongn.spring_quanlycongviec.services;

import com.xuanthongn.spring_quanlycongviec.dto.task.CreateTaskDto;
import com.xuanthongn.spring_quanlycongviec.dto.task.TaskDto;
import com.xuanthongn.spring_quanlycongviec.entities.SubTask;
import com.xuanthongn.spring_quanlycongviec.entities.Task;
import com.xuanthongn.spring_quanlycongviec.entities.User;
import com.xuanthongn.spring_quanlycongviec.repository.TaskRepository;
import com.xuanthongn.spring_quanlycongviec.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService implements ITaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public List<TaskDto> findAll() {

        List<TaskDto> taskDtos = mapper.map(taskRepository.findAll(), new TypeToken<List<TaskDto>>(){}.getType());
        taskDtos.forEach(e -> e.setIsDone(
                e.getSubtasks().stream().filter(SubTask::isDone).toList().size()
        )

        );
        return taskDtos;
    }

    @Override
    public TaskDto save(CreateTaskDto input) {
        Task entity = mapper.map(input, Task.class);
        Collection<User> users = userRepository.findAllById(input.getUsers().stream().map(Long::valueOf).collect(Collectors.toList()));
        entity.setUsers(users);
        Task savedEntity = taskRepository.save(entity);
        return mapper.map(savedEntity, TaskDto.class);
    }

    @Override
    public TaskDto findById(long id) {
        TaskDto data = mapper.map(taskRepository.findById(id).get(), TaskDto.class);
        data.setIsDone(data.getSubtasks().stream().filter(SubTask::isDone).toList().size());
        return data;
    }

    @Override
    public void delete(Task entity) {
        taskRepository.delete(entity);
    }

    @Override
    public void deleteById(long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public long count() {
        return taskRepository.count();
    }
}
