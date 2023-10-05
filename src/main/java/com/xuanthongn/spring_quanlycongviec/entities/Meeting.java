    package com.xuanthongn.spring_quanlycongviec.entities;

    import com.fasterxml.jackson.annotation.JsonIgnore;
    import jakarta.persistence.*;
    import jakarta.validation.constraints.NotBlank;
    import jakarta.validation.constraints.NotNull;
    import lombok.*;

    import java.time.LocalDateTime;
    import java.util.Collection;
    import java.util.Set;

    @Entity // dùng để khai báo với Spring Boot rằng đây là 1 entity biểu diễn table trong database
    @Data // annotation này sẽ tự động khai báo getter và setter cho class
    @AllArgsConstructor // dùng để khai báo constructor với tất cả các properties
    @NoArgsConstructor //
    @Builder
    public class Meeting {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        private Long id;

        @NotNull
        private String title;

        @Column(name = "content")
        private String content;

        @NotNull
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "startTime")
        private LocalDateTime startTime;

        @NotNull
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "endTime")
        private LocalDateTime endTime;

        @JsonIgnore
        @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
        @EqualsAndHashCode.Exclude
        @ToString.Exclude
        @JoinTable(name = "meeting_user",
                joinColumns = @JoinColumn(name = "meeting_id"),
                inverseJoinColumns = @JoinColumn(name = "user_id")
        )
        private Collection<User> attendees;


    }
