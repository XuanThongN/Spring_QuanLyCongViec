package com.xuanthongn.spring_quanlycongviec.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xuanthongn.spring_quanlycongviec.common.MessageType;
import com.xuanthongn.spring_quanlycongviec.common.NotificationType;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User sender;

    private String content;

    private LocalDateTime updatedAt;

    @CreationTimestamp
    private Instant createdOn;
}

