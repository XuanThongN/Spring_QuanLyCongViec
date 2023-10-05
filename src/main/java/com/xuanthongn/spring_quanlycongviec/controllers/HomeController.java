package com.xuanthongn.spring_quanlycongviec.controllers;

import com.xuanthongn.spring_quanlycongviec.dto.meeting.MeetingDto;
import com.xuanthongn.spring_quanlycongviec.dto.task.TaskDto;
import com.xuanthongn.spring_quanlycongviec.dto.user.UserDto;
import com.xuanthongn.spring_quanlycongviec.entities.Meeting;
import com.xuanthongn.spring_quanlycongviec.entities.User;
import com.xuanthongn.spring_quanlycongviec.services.MeetingService;
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

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;

    @Autowired
    private MeetingService meetingService;

    @GetMapping("/error")
    public String error() {
        return "error"; // Trả về tên của trang đăng nhập (ví dụ: "login.html")
    }

    @GetMapping("/")
    public String Dashboard(Model model, Principal principal) {
        // Gọi các phương thức từ các controller khác để lấy dữ liệu
        List<TaskDto> tasks = taskService.findAll();
        List<User> users = userService.findAll();
        List<Meeting> meetings = meetingService.findAll();

        // Tính toán các số liệu bạn cần
        int totalTasks = tasks.size();
        int totalUsers = users.size();
        int totalMeetings = meetings.size();

        // Truyền các số liệu vào Model
        model.addAttribute("tasks", tasks);
        model.addAttribute("users", users);
        model.addAttribute("meetings", meetings);
        model.addAttribute("totalTasks", totalTasks);
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalMeetings", totalMeetings);
        model.addAttribute("currentUser", userService.findByUsername(principal.getName()));

        return "dashboard"; // Trả về tên của trang dashboard.html
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