package com.xuanthongn.spring_quanlycongviec.controllers;

import java.util.List;

import com.xuanthongn.spring_quanlycongviec.entities.User;
import com.xuanthongn.spring_quanlycongviec.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
@CrossOrigin(origins = "*")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping("/userList")
    public ResponseEntity<List<User>> getUserList() {
        return new ResponseEntity<List<User>>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id) {
        return new ResponseEntity<User>(userService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/user/save")
    public ResponseEntity<Void> saveOrUpdateUser(@RequestBody User user) {
        userService.save(user);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteById(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}