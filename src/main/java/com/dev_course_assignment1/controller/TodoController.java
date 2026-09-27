package com.dev_course_assignment1.controller;

import com.dev_course_assignment1.domain.TodoCreateRequest;
import com.dev_course_assignment1.domain.TodoDto;
import com.dev_course_assignment1.domain.TodoUpdateRequest;
import com.dev_course_assignment1.global.result.Result;
import com.dev_course_assignment1.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<Result<TodoDto>> createTodo(@RequestBody TodoCreateRequest request){
        Result<TodoDto> result = todoService.createTodo(request);
        return ResponseEntity
                .status(result.getResultCode())
                .body(result);
    }

    @GetMapping
    public ResponseEntity<Result<List<TodoDto>>> getTodos(){
        Result<List<TodoDto>> result = todoService.getTodos();
        return ResponseEntity
                .status(result.getResultCode())
                .body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result<TodoDto>> getTodo(@PathVariable int id){
        Result<TodoDto> result = todoService.getTodo(id);
        return ResponseEntity
                .status(result.getResultCode())
                .body(result);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Result<TodoDto>> updateTodo(
            @PathVariable int id,
            @RequestBody TodoUpdateRequest request){
        Result<TodoDto> result = todoService.updateTodo(id, request);
        return ResponseEntity
                .status(result.getResultCode())
                .body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result<Void>> deleteTodo(@PathVariable int id){
        Result result = todoService.deleteTodo(id);
        return ResponseEntity
                .status(result.getResultCode())
                .body(result);
    }
}
