package com.xuanthongn.spring_quanlycongviec.controllers;

import com.xuanthongn.spring_quanlycongviec.common.NotificationType;
import com.xuanthongn.spring_quanlycongviec.dto.notification.NotificationDto;
import com.xuanthongn.spring_quanlycongviec.entities.Notification;
import com.xuanthongn.spring_quanlycongviec.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.nio.file.attribute.UserPrincipal;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/notification.createTask")
    @SendTo("/topic/public")
    public Notification createTask(@Payload Notification notification) {
        return notification;
    }

    @MessageMapping("/notification.addUser")
    @SendTo("/topic/notification")
    public void addUser(
            @Payload NotificationDto notification,
            SimpMessageHeaderAccessor headerAccessor,
            Principal principal
    ) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", principal.getName());
        if (notification.getType() == NotificationType.JOIN) {
            notification.setContent(String.format("%s đã đăng nhập vào hệ thống!", principal.getName()));
        }
//        return notification;
    }

    @RequestMapping("/Notification/GetAll")
    @ResponseBody
    public Collection<NotificationDto> GetAll() {
        try {
            return notificationService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/GetAllNotification")
    @ResponseBody
    public ModelAndView GetAllNotification(Model model) {
        try {
            ModelAndView modelAndView = new ModelAndView("shared/_top-bar_notification");
            modelAndView.addObject("notifications", notificationService.findAll());
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView();
    }

}
