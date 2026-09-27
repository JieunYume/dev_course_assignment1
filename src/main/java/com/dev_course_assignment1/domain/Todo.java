package com.dev_course_assignment1.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Todo{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String title;
        @ColumnDefault("false")
        private boolean completionStatus;
        @CreatedDate
        private LocalDateTime createDate;
        @LastModifiedDate
        private LocalDateTime modifyDate;

        public Todo(String title){
                this.title = title;
        }

        public TodoDto toDto(){
                return new TodoDto(
                        id,
                        title,
                        completionStatus,
                        createDate,
                        modifyDate
                );
        }
}
