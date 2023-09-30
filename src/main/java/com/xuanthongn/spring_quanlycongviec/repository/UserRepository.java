package com.xuanthongn.spring_quanlycongviec.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.xuanthongn.spring_quanlycongviec.entities.User;

import java.util.Optional;

/**
 * Created by HachNV on 29/05/2023
 */

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}