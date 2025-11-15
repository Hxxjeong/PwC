package com.pwc.domain.todo.controller;

import com.pwc.common.RspTemplate;
import com.pwc.domain.todo.dto.request.TodoReqDto;
import com.pwc.domain.todo.dto.response.TodoRspDto;
import com.pwc.domain.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {
    private final TodoService todoService;

    // 생성
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MASTER')")
    @PostMapping
    public RspTemplate<TodoRspDto> createTodo(@RequestBody TodoReqDto dto) {
        return new RspTemplate<>(HttpStatus.CREATED, "Todo가 생성되었습니다.", todoService.createTodo(dto));
    }

    @GetMapping
    public RspTemplate<List<TodoRspDto>> getTodos(
            @RequestParam(required = false) String createUser,
            @RequestParam(required = false) String tagName,
            @RequestParam(required = false) Boolean isDone) {
        TodoReqDto.SearchDto dto = TodoReqDto.SearchDto.builder()
                .createUser(createUser)
                .tagName(tagName)
                .isDone(isDone)
                .build();
        return new RspTemplate<>(HttpStatus.OK, "Todo 조회 완료", todoService.getTodos(dto));
    }

    // 수정
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MASTER')")
    @PatchMapping("/{todoId}")
    public RspTemplate<TodoRspDto> updateTodo(@PathVariable("todoId") Long todoId, @RequestBody TodoReqDto.UpdateDto dto) {
        return new RspTemplate<>(HttpStatus.OK, "Todo가 수정되었습니다.", todoService.updateTodo(todoId, dto));
    }

    // 삭제
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MASTER')")
    @DeleteMapping("/{todoId}")
    public RspTemplate<Void> deleteTodo(@PathVariable("todoId") Long todoId) {
        todoService.deleteTodo(todoId);
        return new RspTemplate<>(HttpStatus.OK, "Todo가 삭제되었습니다.");
    }
}
