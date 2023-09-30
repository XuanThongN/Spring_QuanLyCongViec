package com.xuanthongn.spring_quanlycongviec.repository;

import com.xuanthongn.spring_quanlycongviec.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}
