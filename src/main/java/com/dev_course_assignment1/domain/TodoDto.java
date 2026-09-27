package com.dev_course_assignment1.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class TodoDto {
    private int id;
    private String title;
    private boolean completionStatus;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
}
