package com.xuanthongn.spring_quanlycongviec.entities;

import com.xuanthongn.spring_quanlycongviec.common.TaskPriority;
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

    @NotNull
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<SubTask> subtasks;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // Quan hệ n-n với đối tượng ở dưới (User) (1 người dùng có thể tham gia nhiều công vệc)
    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
    @ToString.Exclude // Không sử dụng trong toString()
    @JoinTable(name = "task_user", //Tạo ra một join Table tên là "task_user"
            joinColumns = @JoinColumn(name = "task_id"),  // TRong đó, khóa ngoại chính là task_id trỏ tới class hiện tại (Task)
            inverseJoinColumns = @JoinColumn(name = "user_id") //Khóa ngoại thứ 2 trỏ tới thuộc tính ở dưới (User)
    )
    private Collection<User> users;
}
