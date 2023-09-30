package com.xuanthongn.spring_quanlycongviec.entities;

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

import java.util.Collection;
import java.util.Date;


@Entity // dùng để khai báo với Spring Boot rằng đây là 1 entity biểu diễn table trong database
@Data // annotation này sẽ tự động khai báo getter và setter cho class
@AllArgsConstructor // dùng để khai báo constructor với tất cả các properties
@NoArgsConstructor //
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @NotBlank(message = "Tên người dùng không thể để trống")
    private String name;

    @NotNull(message = "Ngày sinh không thể để trống")
    @Temporal(TemporalType.DATE)
    private Date birthday;


    @NotNull
    @NotBlank(message = "Tên người dùng không thể để trống")
    private String username;
    private String password;
    private String roles;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // Quan hệ n-n với đối tượng ở dưới (Quiz) (1 người dùng có thể tham gia nhiều công vệc)
    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
    @ToString.Exclude // Không sử dụng trong toString()
    @JoinTable(name = "task_user", //Tạo ra một join Table tên là "task_user"
            joinColumns = @JoinColumn(name = "user_id"),  // TRong đó, khóa ngoại chính là user_id trỏ tới class hiện tại (User)
            inverseJoinColumns = @JoinColumn(name = "task_id") //Khóa ngoại thứ 2 trỏ tới thuộc tính ở dưới (Task)
    )
    private Collection<Task> tasks;

//	public User() {
//		super();
//		// TODO Auto-generated constructor stub
//	}

//	public User(Long id, @NotNull @NotBlank(message = "Tên người dùng không thể để trống") String name,
//			@NotNull @NotBlank(message = "Họ tên không thể để trống") @Size(min = 5, max = 1000) Date birthday,
//			@NotNull @NotBlank(message = "Tên người dùng không thể để trống") String username, String password,
//			String roles, Collection<Task> tasks) {
//		super();
//		this.id = id;
//		this.name = name;
//		this.birthday = birthday;
//		this.username = username;
//		this.password = password;
//		this.roles = roles;
//		this.tasks = tasks;
//	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public Collection<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Collection<Task> tasks) {
		this.tasks = tasks;
	}

    
}
