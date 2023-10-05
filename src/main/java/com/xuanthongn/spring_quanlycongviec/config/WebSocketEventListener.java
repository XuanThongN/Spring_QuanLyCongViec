package com.xuanthongn.spring_quanlycongviec.config;

import com.xuanthongn.spring_quanlycongviec.common.MessageType;
import com.xuanthongn.spring_quanlycongviec.common.NotificationType;
import com.xuanthongn.spring_quanlycongviec.dto.notification.NotificationDto;
import com.xuanthongn.spring_quanlycongviec.dto.user.UserDto;
import com.xuanthongn.spring_quanlycongviec.entities.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            log.info("user disconnected: {}", username);
            var chatMessage = NotificationDto
                    .builder()
                    .type(NotificationType.LEAVE)
                    .sender(UserDto.builder().username(username).build())
                    .build();
//            messagingTemplate.convertAndSend("/topic/notification", chatMessage);
            //Đã tắt thông báo
        }
    }


}
