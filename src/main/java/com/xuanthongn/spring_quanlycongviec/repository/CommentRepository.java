package com.xuanthongn.spring_quanlycongviec.repository;

import com.xuanthongn.spring_quanlycongviec.entities.Comment;
import com.xuanthongn.spring_quanlycongviec.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Collection<Comment> findCommentsByTaskId(Long id);
}
