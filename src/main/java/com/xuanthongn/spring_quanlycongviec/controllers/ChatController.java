package com.xuanthongn.spring_quanlycongviec.controllers;

import com.xuanthongn.spring_quanlycongviec.dto.notification.NotificationDto;
import com.xuanthongn.spring_quanlycongviec.entities.Notification;
import com.xuanthongn.spring_quanlycongviec.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    @RequestMapping("/chat")
    public String Index() {
        return "chat/index";
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public Notification sendMessage(
            @Payload Notification notification
    ) {
        return notification;
    }


    @MessageMapping("/chat.thongbao")
    @SendTo("/topic/public")
    public Notification thongbao(
            @Payload Notification notification
    ) {
        return notification;
    }
}
