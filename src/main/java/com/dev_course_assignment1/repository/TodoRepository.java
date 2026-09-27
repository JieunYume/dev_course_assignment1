package com.dev_course_assignment1.repository;

import com.dev_course_assignment1.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
}
