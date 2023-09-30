package com.xuanthongn.spring_quanlycongviec.services;


import com.xuanthongn.spring_quanlycongviec.entities.User;
import com.xuanthongn.spring_quanlycongviec.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Tìm user từ cơ sở dữ liệu dựa trên username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Convert user roles to a collection of GrantedAuthority objects
        List<GrantedAuthority> authorities = new ArrayList<>();
        String roles = user.getRoles();
        if (roles != null && !roles.isEmpty()) {
            Arrays.stream(roles.split(","))
                  .forEach(role -> authorities.add(new SimpleGrantedAuthority(role.trim())));
        }

        // Create UserDetails with the authorities
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}
