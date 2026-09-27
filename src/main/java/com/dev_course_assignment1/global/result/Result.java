package com.dev_course_assignment1.global.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Result<T> {
    private int resultCode;
    private String message;
    private T data;

    public Result(int resultCode, String message){
        this(resultCode, message, null);
    }
}
