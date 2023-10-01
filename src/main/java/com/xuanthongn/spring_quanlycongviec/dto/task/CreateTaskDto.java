package com.xuanthongn.spring_quanlycongviec.dto.task;

import com.xuanthongn.spring_quanlycongviec.common.TaskPriority;
import com.xuanthongn.spring_quanlycongviec.common.TaskState;
import com.xuanthongn.spring_quanlycongviec.entities.SubTask;
import com.xuanthongn.spring_quanlycongviec.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Collection;
import java.util.Date;

@Data // annotation này sẽ tự động khai báo getter và setter cho class
@AllArgsConstructor // dùng để khai báo constructor với tất cả các properties
@NoArgsConstructor //
@Builder
public class CreateTaskDto {
    @NotNull
    @NotBlank(message = "Tiêu đề không được để trống")
    private String title;

    @NotNull
    @Size(min = 5, max = 1000)
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

    @NotNull
    private Collection<Integer> users;
}
