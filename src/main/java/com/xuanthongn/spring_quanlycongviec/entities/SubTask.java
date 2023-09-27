package com.xuanthongn.spring_quanlycongviec.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sub_task")
public class SubTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @NotBlank(message = "Tiêu đề không được để trống")
    private String title;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    private boolean isDone;
}