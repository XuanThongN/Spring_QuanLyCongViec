package com.xuanthongn.spring_quanlycongviec.controllers;

import com.xuanthongn.spring_quanlycongviec.common.NotificationType;
import com.xuanthongn.spring_quanlycongviec.dto.notification.NotificationDto;
import com.xuanthongn.spring_quanlycongviec.dto.task.CreateTaskDto;
import com.xuanthongn.spring_quanlycongviec.dto.task.TaskDto;
import com.xuanthongn.spring_quanlycongviec.dto.task.UpdateTaskDto;
import com.xuanthongn.spring_quanlycongviec.dto.user.UserDto;
import com.xuanthongn.spring_quanlycongviec.entities.Notification;
import com.xuanthongn.spring_quanlycongviec.entities.Task;
import com.xuanthongn.spring_quanlycongviec.entities.User;
import com.xuanthongn.spring_quanlycongviec.repository.TaskRepository;
import com.xuanthongn.spring_quanlycongviec.services.NotificationService;
import com.xuanthongn.spring_quanlycongviec.services.TaskService;
import com.xuanthongn.spring_quanlycongviec.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
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
import java.util.List;

@Controller
@RequestMapping("/Task")
public class TaskController {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;
    @Autowired
    private NotificationService notificationService;

    @RequestMapping("")
    public String Index(Model model,Principal principal) {
        try {
            model.addAttribute("users", userService.findAll());
            model.addAttribute("currentUser", userService.findByUsername(principal.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "index";
    }

    @RequestMapping("/LoadBoard")
    @ResponseBody
    public ModelAndView LoadBoard(Model model) {
        try {
            ModelAndView modelAndView = new ModelAndView("_task-load-board");
            modelAndView.addObject("tasks", taskService.findAll());
            modelAndView.addObject("users", userService.findAll());
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView();
    }

    @PostMapping("/Create")
    public ResponseEntity<String> Create(@RequestBody @Valid CreateTaskDto task, BindingResult bindingResult, Principal principal) {
        try {
            // Khi có BindingResult thì lỗi được tạm bỏ qua để xử lý thủ công
            // Nếu có lỗi thì chặn lại
            if (bindingResult.hasErrors())
                try {
                    throw new Exception("...");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            taskService.save(task);
            NotificationDto notification = NotificationDto.builder()
                    .content("Đã tạo mới thẻ "+task.getTitle().toUpperCase())
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

    @GetMapping("/Details/{id}")
    @ResponseBody
    public ModelAndView Details(@PathVariable Long id) {
        try {
            TaskDto task = taskService.findById(id);
            ModelAndView modelAndView = new ModelAndView("_task-detail");
            modelAndView.addObject("task", task);
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/Update/{id}")
    @ResponseBody
    public ModelAndView Update(@PathVariable Long id) {
        try {
            TaskDto task = taskService.findById(id);
            ModelAndView modelAndView = new ModelAndView("_task-update");
            modelAndView.addObject("task", task);
            modelAndView.addObject("users", userService.findAll());
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/Update")
    public ResponseEntity<String> updateTask(@RequestBody @Valid UpdateTaskDto task, BindingResult bindingResult, Principal principal) {
        try {
            // Check for validation errors
            if (bindingResult.hasErrors()) {
                return ResponseEntity.badRequest().body("Validation failed");
            }

            // Update the task
            taskService.update(task);

            // Send a notification
            NotificationDto notification = NotificationDto.builder()
                    .content("Đã cập nhật thẻ " + task.getTitle().toUpperCase())
                    .sender(UserDto.builder()
                            .username(principal.getName())
                            .build())
                    .type(NotificationType.UPDATE)
                    .build();
            notificationService.sendNotification(notification);

            return ResponseEntity.ok("Task updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

}
