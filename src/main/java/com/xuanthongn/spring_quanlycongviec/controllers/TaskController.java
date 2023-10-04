package com.xuanthongn.spring_quanlycongviec.controllers;

import com.xuanthongn.spring_quanlycongviec.dto.task.CreateTaskDto;
import com.xuanthongn.spring_quanlycongviec.dto.task.TaskDto;
import com.xuanthongn.spring_quanlycongviec.dto.task.UpdateTaskDto;
import com.xuanthongn.spring_quanlycongviec.entities.Task;
import com.xuanthongn.spring_quanlycongviec.entities.User;
import com.xuanthongn.spring_quanlycongviec.repository.TaskRepository;
import com.xuanthongn.spring_quanlycongviec.services.TaskService;
import com.xuanthongn.spring_quanlycongviec.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/Task")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;
    @Autowired
    private TaskRepository taskRepository;

    @RequestMapping("")
    public String Index(Model model) {
        try {
            model.addAttribute("users", userService.findAll());
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
    public String Create(@RequestBody @Valid CreateTaskDto task, BindingResult bindingResult) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "index";
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
    public String Update(@RequestBody @Valid UpdateTaskDto task, BindingResult bindingResult) {
        try {
            // Khi có BindingResult thì lỗi được tạm bỏ qua để xử lý thủ công
            // Nếu có lỗi thì chặn lại
            if (bindingResult.hasErrors()) return null;
            taskService.update(task);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "index";
    }
}
