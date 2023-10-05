package com.xuanthongn.spring_quanlycongviec.services;

import com.xuanthongn.spring_quanlycongviec.dto.comment.CommentDto;
import com.xuanthongn.spring_quanlycongviec.dto.comment.CreateCommentDto;
import com.xuanthongn.spring_quanlycongviec.dto.notification.NotificationDto;
import com.xuanthongn.spring_quanlycongviec.entities.Comment;
import com.xuanthongn.spring_quanlycongviec.entities.Notification;
import com.xuanthongn.spring_quanlycongviec.entities.Task;
import com.xuanthongn.spring_quanlycongviec.entities.User;
import com.xuanthongn.spring_quanlycongviec.repository.CommentRepository;
import com.xuanthongn.spring_quanlycongviec.repository.NotificationRepository;
import com.xuanthongn.spring_quanlycongviec.repository.TaskRepository;
import com.xuanthongn.spring_quanlycongviec.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class CommentService implements IGenericService<Comment> {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private TaskRepository taskRepository;


    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment save(Comment entity) {
        return null;
    }

    public Comment save(CreateCommentDto input) {
        User sender = userRepository.findById(input.getSenderId().longValue()).get();
        Task task = taskRepository.findById(input.getTaskId().longValue()).get();
        Comment entity = Comment.builder()
                .content(input.getContent())
                .sender(sender)
                .task(task).build();
        return commentRepository.save(entity);
    }

    @Override
    public Comment findById(long id) {
        return null;
    }

    @Override
    public void delete(Comment entity) {

    }

    @Override
    public void deleteById(long id) {

    }

    @Override
    public long count() {
        return 0;
    }

    public Collection<Comment> findAllByTaskId(Long id) {
        return commentRepository.findCommentsByTaskId(id);
    }
}
