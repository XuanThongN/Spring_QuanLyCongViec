package com.xuanthongn.spring_quanlycongviec.dto.notification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xuanthongn.spring_quanlycongviec.common.NotificationType;
import com.xuanthongn.spring_quanlycongviec.dto.user.UserDto;
import com.xuanthongn.spring_quanlycongviec.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationDto {
    private NotificationType type;
    private String content;
    @JsonIgnore
    private UserDto sender;
    private Instant createdOn;

}
