package com.dev_course_assignment1.domain;

import lombok.Getter;

@Getter
public class TodoUpdateRequest {
    private String title;
    private Boolean completed;
}
