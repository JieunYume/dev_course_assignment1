package com.dev_course_assignment1.service;

import com.dev_course_assignment1.domain.Todo;
import com.dev_course_assignment1.domain.TodoCreateRequest;
import com.dev_course_assignment1.domain.TodoDto;
import com.dev_course_assignment1.domain.TodoPolicy;
import com.dev_course_assignment1.global.result.Result;
import com.dev_course_assignment1.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    public Result<TodoDto> createTodo(TodoCreateRequest request) {
        String title = request.getTitle();
        if(title == null || title.isBlank())
            return new Result<>(400, "제목이 비어있습니다.");
        if(title.length() > TodoPolicy.MAX_TITLE_LENGTH)
            return new Result<>(400, "제목의 길이는 "+TodoPolicy.MAX_TITLE_LENGTH+"자 미만이어야 합니다.");

        Todo todo = todoRepository.save(new Todo(title));
        return new Result<>(201,"할 일이 성공적으로 생성되었습니다.",todo.toDto());
    }

    public Result<List<TodoDto>> getTodos() {
        List<TodoDto> todos = todoRepository.findAll()
                                .stream()
                                .map(Todo::toDto)
                                .toList();
        return new Result<>(200, "할 일 목록 조회에 성공했습니다.", todos);
    }
}
