package com.dev_course_assignment1.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class TodoDto {
    private int id;
    private String title;
    private Boolean completed;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
}
