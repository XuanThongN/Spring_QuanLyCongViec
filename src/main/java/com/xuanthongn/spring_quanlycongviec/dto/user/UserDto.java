package com.xuanthongn.spring_quanlycongviec.dto.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xuanthongn.spring_quanlycongviec.common.Constant;
import com.xuanthongn.spring_quanlycongviec.entities.Task;
import com.xuanthongn.spring_quanlycongviec.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Collection;
import java.util.Date;


@Data // annotation này sẽ tự động khai báo getter và setter cho class
@AllArgsConstructor // dùng để khai báo constructor với tất cả các properties
@NoArgsConstructor //
@Builder
@JsonIgnoreProperties({"tasks"}) // Bỏ qua thuộc tính 'tasks' khi chuyển đổi thành JSON
public class UserDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @NotBlank(message = "Tên người dùng không thể để trống")
    private String name;

    private Date birthday;

    @NotBlank(message = "Tên người dùng không thể để trống")
    private String username;
    private String roles;

    private String avatar;

    private Collection<Task> tasks;


}
