package com.xuanthongn.spring_quanlycongviec.entities;

import jakarta.persistence.*;
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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @NotBlank(message = "Tên người dùng không thể để trống")
    private String name;

    @NotNull
    @NotBlank(message = "Họ tên không thể để trống")
    @Size(min = 5, max = 1000)
    private Date birthday;

    @NotNull
    @NotBlank(message = "Tên người dùng không thể để trống")
    private String username;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // Quan hệ n-n với đối tượng ở dưới (Quiz) (1 người dùng có thể tham gia nhiều công vệc)
    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
    @ToString.Exclude // Không sử dụng trong toString()
    @JoinTable(name = "task_user", //Tạo ra một join Table tên là "task_user"
            joinColumns = @JoinColumn(name = "user_id"),  // TRong đó, khóa ngoại chính là user_id trỏ tới class hiện tại (User)
            inverseJoinColumns = @JoinColumn(name = "task_id") //Khóa ngoại thứ 2 trỏ tới thuộc tính ở dưới (Task)
    )
    private Collection<Task> tasks;

}
