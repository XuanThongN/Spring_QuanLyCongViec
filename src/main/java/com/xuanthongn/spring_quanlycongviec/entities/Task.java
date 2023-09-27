package com.xuanthongn.spring_quanlycongviec.entities;

import com.xuanthongn.spring_quanlycongviec.common.TaskState;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Collection;
import java.util.Date;

@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<SubTask> subtasks;

    // mappedBy trỏ tới tên biến tasks ở trong User.
    @ManyToMany(mappedBy = "tasks")
    // LAZY để tránh việc truy xuất dữ liệu không cần thiết. Lúc nào cần thì mới query
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<User> users;
}
