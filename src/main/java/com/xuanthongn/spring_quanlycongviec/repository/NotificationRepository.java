package com.xuanthongn.spring_quanlycongviec.repository;

import com.xuanthongn.spring_quanlycongviec.entities.Meeting;
import com.xuanthongn.spring_quanlycongviec.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Collection<Notification> findFirst10ByOrderByCreatedOnDesc();
}
