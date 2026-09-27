package com.dev_course_assignment1.controller;

import com.dev_course_assignment1.domain.TodoCreateRequest;
import com.dev_course_assignment1.domain.TodoDto;
import com.dev_course_assignment1.global.result.Result;
import com.dev_course_assignment1.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/todo")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;
    @PostMapping
    public Result<TodoDto> createTodo(@RequestBody TodoCreateRequest request){
        return todoService.createTodo(request);
    }
}
