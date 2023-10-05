package com.xuanthongn.spring_quanlycongviec.services;

import com.xuanthongn.spring_quanlycongviec.dto.notification.NotificationDto;
import com.xuanthongn.spring_quanlycongviec.dto.task.CreateTaskDto;
import com.xuanthongn.spring_quanlycongviec.dto.task.TaskDto;
import com.xuanthongn.spring_quanlycongviec.dto.task.UpdateTaskDto;
import com.xuanthongn.spring_quanlycongviec.entities.*;
import com.xuanthongn.spring_quanlycongviec.repository.NotificationRepository;
import com.xuanthongn.spring_quanlycongviec.repository.TaskRepository;
import com.xuanthongn.spring_quanlycongviec.repository.UserRepository;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void sendNotification(NotificationDto notification) {
        // lưu thông báo vào cơ sở dữ liệu
        Notification entity = mapper.map(notification, Notification.class);
        User sender = userRepository.findByUsername(notification.getSender().getUsername()).get();
        entity.setSender(sender);
        notificationRepository.save(entity);
        // gửi thông báo đến các người dùng đã đăng ký
        simpMessagingTemplate.convertAndSend("/topic/notification", notification);
    }

    public Collection<NotificationDto> findAll() {
        Collection<Notification> notifications = notificationRepository.findFirst10ByOrderByCreatedOnDesc();
        return mapper.map(notifications, new TypeToken<Collection<NotificationDto>>() {
        }.getType());
    }

}
