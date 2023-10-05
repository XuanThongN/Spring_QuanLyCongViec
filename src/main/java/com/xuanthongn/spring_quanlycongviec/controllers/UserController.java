package com.xuanthongn.spring_quanlycongviec.controllers;

import java.security.Principal;
import java.util.List;

import com.xuanthongn.spring_quanlycongviec.dto.user.UserDto;
import com.xuanthongn.spring_quanlycongviec.entities.User;
import com.xuanthongn.spring_quanlycongviec.services.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String listUsers(Model theModel, Principal principal) {
        theModel.addAttribute("currentUser", userService.findByUsername(principal.getName()));
        return "user/index";
    }
    @GetMapping("/detail/{userId}")
    public String showUserDetail(@PathVariable int userId, Model theModel, Principal principal) {
        // Lấy thông tin chi tiết của người dùng theo userId từ service
        User user = userService.findById(userId);

        // Đưa thông tin người dùng vào model
        theModel.addAttribute("user", user);

        theModel.addAttribute("currentUser", userService.findByUsername(principal.getName()));
        // Chuyển đến trang chi tiết
        return "user/detail";
    }
    @ResponseBody
    @GetMapping("/userList")
    public ResponseEntity<List<UserDto>> getUserList() {
        List<UserDto> userDtos = mapper.map(userService.findAll(), new TypeToken<List<UserDto>>(){}.getType());
        return new ResponseEntity<List<UserDto>>(userDtos, HttpStatus.OK);
    }
    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id) {
        return new ResponseEntity<User>(userService.findById(id), HttpStatus.OK);
    }
    @ResponseBody
    @PostMapping("/save")
    public ResponseEntity<Void> saveOrUpdateUser(@RequestBody User user) {
        userService.save(user);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    @ResponseBody
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteById(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
