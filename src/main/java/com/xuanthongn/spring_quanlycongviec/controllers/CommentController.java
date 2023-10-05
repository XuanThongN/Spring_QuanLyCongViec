package com.xuanthongn.spring_quanlycongviec.controllers;

import com.xuanthongn.spring_quanlycongviec.common.NotificationType;
import com.xuanthongn.spring_quanlycongviec.dto.comment.CreateCommentDto;
import com.xuanthongn.spring_quanlycongviec.dto.notification.NotificationDto;
import com.xuanthongn.spring_quanlycongviec.dto.task.CreateTaskDto;
import com.xuanthongn.spring_quanlycongviec.dto.task.TaskDto;
import com.xuanthongn.spring_quanlycongviec.dto.task.UpdateTaskDto;
import com.xuanthongn.spring_quanlycongviec.dto.user.UserDto;
import com.xuanthongn.spring_quanlycongviec.entities.Comment;
import com.xuanthongn.spring_quanlycongviec.services.CommentService;
import com.xuanthongn.spring_quanlycongviec.services.NotificationService;
import com.xuanthongn.spring_quanlycongviec.services.TaskService;
import com.xuanthongn.spring_quanlycongviec.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("/Comment")
public class CommentController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private NotificationService notificationService;

    @PostMapping("/Create")
    public ResponseEntity<String> Create(@RequestBody @Valid CreateCommentDto comment, BindingResult bindingResult, Principal principal) {
        try {
            // Khi có BindingResult thì lỗi được tạm bỏ qua để xử lý thủ công
            // Nếu có lỗi thì chặn lại
            if (bindingResult.hasErrors())
                try {
                    throw new Exception("...");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            Comment newComment =  commentService.save(comment);
            NotificationDto notification = NotificationDto.builder()
                    .content("Đã thêm một bình luận ở thẻ " + newComment.getTask().getTitle().toUpperCase())
                    .sender(UserDto.builder()
                            .username(principal.getName())
                            .build())
                    .type(NotificationType.CREATE)
                    .build();
            notificationService.sendNotification(notification);
            return ResponseEntity.ok("Task added successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

}
