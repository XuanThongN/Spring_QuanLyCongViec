package com.xuanthongn.spring_quanlycongviec.dto.task;

import com.xuanthongn.spring_quanlycongviec.common.CollectionConverter;
import com.xuanthongn.spring_quanlycongviec.common.TaskPriority;
import com.xuanthongn.spring_quanlycongviec.common.TaskState;
import com.xuanthongn.spring_quanlycongviec.entities.Comment;
import com.xuanthongn.spring_quanlycongviec.entities.SubTask;
import com.xuanthongn.spring_quanlycongviec.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;

@Data
public class TaskDto {
    private Long id;

    @NotNull
    @NotBlank(message = "Tiêu đề không được để trống")
    private String title;

    @NotNull
    private String description;

    @NotNull
    private Date dueDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TaskState state;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    private Collection<SubTask> subtasks;

    private Collection<User> users;
    private Collection<Integer> userIds;
    private Integer isDone;
    private Instant createdOn;
    private Collection<Comment> comments;


}
