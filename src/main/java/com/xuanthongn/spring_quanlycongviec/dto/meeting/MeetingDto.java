    package com.xuanthongn.spring_quanlycongviec.dto.meeting;

    import com.fasterxml.jackson.annotation.JsonIgnore;
    import com.xuanthongn.spring_quanlycongviec.entities.User;
    import jakarta.persistence.*;
    import jakarta.validation.constraints.NotNull;
    import lombok.*;

    import java.time.LocalDateTime;
    import java.util.Collection;

    @Data // annotation này sẽ tự động khai báo getter và setter cho class
    @AllArgsConstructor // dùng để khai báo constructor với tất cả các properties
    @NoArgsConstructor //
    @Builder
    public class MeetingDto {
        private Long id;

        @NotNull
        private String title;

        private String content;

        @NotNull
        @Temporal(TemporalType.TIMESTAMP)
        private LocalDateTime startTime;

        @NotNull
        @Temporal(TemporalType.TIMESTAMP)
        private LocalDateTime endTime;

        @JsonIgnore
        private Collection<User> attendees;
        private Collection<Integer> users;
        private Collection<String> userNames;


    }
