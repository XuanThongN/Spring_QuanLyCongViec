package com.xuanthongn.spring_quanlycongviec.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;


@Entity // dùng để khai báo với Spring Boot rằng đây là 1 entity biểu diễn table trong database
@Data
@ToString
 // annotation này sẽ tự động khai báo getter và setter cho class
@AllArgsConstructor // dùng để khai báo constructor với tất cả các properties
@NoArgsConstructor //
@Builder
@JsonIgnoreProperties({"tasks", "meetings"})

// Bỏ qua thuộc tính 'tasks' khi chuyển đổi thành JSON
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @NotBlank(message = "Tên người dùng không thể để trống")
    private String name;

    //        @NotNull(message = "Ngày sinh không thể để trống")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd") // Định dạng ngày tháng
    private Date birthday;


    //            @NotNull
    @NotBlank(message = "Tên người dùng không thể để trống")
    private String username;
    private String password;
    private String roles;
    private String avatar;


    // mappedBy trỏ tới tên biến tasks ở trong User.
    @ManyToMany(mappedBy = "users")
    // LAZY để tránh việc truy xuất dữ liệu không cần thiết. Lúc nào cần thì mới query
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<Task> tasks;

    @ManyToMany(mappedBy = "attendees", cascade = CascadeType.DETACH)
    // LAZY để tránh việc truy xuất dữ liệu không cần thiết. Lúc nào cần thì mới query
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<Meeting> meetings;


    @JsonIgnore
    @OneToMany(mappedBy = "sender",fetch = FetchType.LAZY)
    @ToString.Exclude
    private Collection<Notification> notifications;

}
