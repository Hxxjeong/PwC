package com.pwc.common.admin.controller;

import com.pwc.common.RspTemplate;
import com.pwc.common.admin.service.AdminService;
import com.pwc.domain.todo.dto.request.TodoReqDto;
import com.pwc.domain.todo.dto.response.TodoRspDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class AdminController {
    private final AdminService adminService;

    // 수정
    @PatchMapping("/{todoId}")
    public RspTemplate<TodoRspDto> updateTodo(@PathVariable("todoId") Long todoId, @RequestBody TodoReqDto dto) {
        return new RspTemplate<>(HttpStatus.OK, "todo가 수정되었습니다.", adminService.updateTodo(todoId, dto));
    }

    // 삭제
    @DeleteMapping("/{todoId}")
    public RspTemplate<Void> deleteTodo(@PathVariable("todoId") Long todoId) {
        adminService.deleteTodo(todoId);
        return new RspTemplate<>(HttpStatus.OK, "todo가 삭제되었습니다.");
    }

    // 모든 항목 조회
    @GetMapping
    public RspTemplate<List<TodoRspDto>> findAllTodos() {
        return new RspTemplate<>(HttpStatus.OK, "todo 조회 완료", adminService.getTodo());
    }

    // 삭제된 투두 복원
    @PatchMapping("/restore/{todoId}")
    public RspTemplate<Void> restoreTodo(@PathVariable("todoId") Long todoId) {
        adminService.undoDelete(todoId);
        return new RspTemplate<>(HttpStatus.OK, "todo 복원 완료");
    }
}
