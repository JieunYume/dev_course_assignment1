package com.dev_course_assignment1.controller;

import com.dev_course_assignment1.domain.TodoCreateRequest;
import com.dev_course_assignment1.domain.TodoDto;
import com.dev_course_assignment1.global.result.Result;
import com.dev_course_assignment1.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;
    @PostMapping
    public Result<TodoDto> createTodo(@RequestBody TodoCreateRequest request){
        return todoService.createTodo(request);
    }

    @GetMapping
    public Result<List<TodoDto>> getTodos(){
        return todoService.getTodos();
    }
}
