package com.xuanthongn.spring_quanlycongviec.controllers;

import com.xuanthongn.spring_quanlycongviec.dto.task.CreateTaskDto;
import com.xuanthongn.spring_quanlycongviec.entities.Task;
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

    @RequestMapping("/")
    public String Index(Model model) {
        List<Task> tasks = taskService.findAll();
        model.addAttribute("tasks", tasks);
        model.addAttribute("users", userService.findAll());
        return "index";
    }

    @PostMapping("/Create")
    public String Create(@RequestBody @Valid CreateTaskDto task, BindingResult bindingResult) {
        // Khi có BindingResult thì lỗi được tạm bỏ qua để xử lý thủ công
        // Nếu có lỗi thì chặn lại
        if (bindingResult.hasErrors())
            try {
                throw new Exception("...");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        taskService.save(task);
        return "index";
    }

    @GetMapping("/Details/{id}")
    @ResponseBody
    public ModelAndView Details(@PathVariable Long id) {
        Task task = taskService.findById(id);
        ModelAndView modelAndView = new ModelAndView("_task-detail");
        modelAndView.addObject("task", task);
        return modelAndView;
    }
}
