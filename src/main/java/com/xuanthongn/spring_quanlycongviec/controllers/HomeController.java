package com.xuanthongn.spring_quanlycongviec.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    @RequestMapping("/")
    public String Index() {
        return "index";
    }
    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello is exception");
    }
    @GetMapping("/login")
    public String login() {
        return "login"; // Trả về tên của trang đăng nhập (ví dụ: "login.html")
    }
}
