package com.xuanthongn.spring_quanlycongviec.controllers;

import com.xuanthongn.spring_quanlycongviec.dto.task.TaskDto;
import com.xuanthongn.spring_quanlycongviec.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.xuanthongn.spring_quanlycongviec.entities.Task;
import com.xuanthongn.spring_quanlycongviec.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;
//    @RequestMapping("/")
//    public String Index(Model model) {
//        List<TaskDto> tasks = taskService.findAll();
//        model.addAttribute("tasks", tasks);
//        model.addAttribute("users", userService.findAll());
//        return "index";
//    }
@GetMapping("/error")
public String error() {
    return "error"; // Trả về tên của trang đăng nhập (ví dụ: "login.html")
}

    @GetMapping("/")
    public String Dashboard() {
        return "dashboard"; // Trả về tên của trang đăng nhập (ví dụ: "login.html")
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Trả về tên của trang đăng nhập (ví dụ: "login.html")
    }

    @GetMapping("/test")
    @ResponseBody
    public ModelAndView test(){
        ModelAndView modelAndView = new ModelAndView("_task-create");
        modelAndView.addObject("nxt","Nguyễn Xuân Thông");
        return modelAndView;
    }
}
