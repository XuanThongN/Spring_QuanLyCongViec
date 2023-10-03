package com.xuanthongn.spring_quanlycongviec.controllers;

import java.util.List;

import com.xuanthongn.spring_quanlycongviec.entities.User;
import com.xuanthongn.spring_quanlycongviec.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String listUsers(Model theModel) {
        return "user/index";
    }
    @GetMapping("/detail/{userId}")
    public String showUserDetail(@PathVariable int userId, Model theModel) {
        // Lấy thông tin chi tiết của người dùng theo userId từ service
        User user = userService.findById(userId);

        // Đưa thông tin người dùng vào model
        theModel.addAttribute("user", user);

        // Chuyển đến trang chi tiết
        return "user/detail";
    }

}
