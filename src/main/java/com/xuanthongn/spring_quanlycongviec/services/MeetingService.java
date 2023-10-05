package com.xuanthongn.spring_quanlycongviec.services;

import com.xuanthongn.spring_quanlycongviec.entities.Meeting;
import com.xuanthongn.spring_quanlycongviec.entities.User;
import com.xuanthongn.spring_quanlycongviec.repository.MeetingRepository;
import com.xuanthongn.spring_quanlycongviec.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MeetingService implements IGenericService<Meeting> {

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private UserRepository userRepository; // Autowire UserRepository

    @Override
    public List<Meeting> findAll() {
        return meetingRepository.findAll();
    }

    @Override
    @Transactional // Thêm chú thích này để bật quản lý giao dịch
    public Meeting save(Meeting entity) {
        // Giả sử rằng entity.getAttendees() chứa danh sách các đối tượng User
        // Bạn có thể điều chỉnh logic này để lấy các đối tượng User từ UserRepository của bạn
        for (User user : entity.getAttendees()) {
            User existingUser = userRepository.findById(user.getId()).orElse(null);
            if (existingUser != null) {
                // Thêm người dùng vào cuộc họp
                entity.getAttendees().add(existingUser);
            }
        }
        return meetingRepository.save(entity);
    }


    @Override
    public Meeting findById(long id) {
        return meetingRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Meeting entity) {
        meetingRepository.delete(entity);
    }

    @Override
    public void deleteById(long id) {
        meetingRepository.deleteById(id);
    }

    @Override
    public long count() {
        return meetingRepository.count();
    }
}
