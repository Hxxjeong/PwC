package com.pwc.common.admin.service;

import com.pwc.common.exception.BusinessException;
import com.pwc.common.exception.ErrorCode;
import com.pwc.domain.tag.entity.Tag;
import com.pwc.domain.tag.repository.TagRepository;
import com.pwc.domain.todo.dto.request.TodoReqDto;
import com.pwc.domain.todo.dto.response.TodoRspDto;
import com.pwc.domain.todo.entity.Todo;
import com.pwc.domain.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final TodoRepository todoRepository;

    // 모든 투두 조회 (삭제된 항목 포함)
    @Transactional(readOnly = true)
    public List<TodoRspDto> getTodo() {
        List<Todo> todos = todoRepository.findAll();

        return todos.stream()
                .map(TodoRspDto::from)
                .collect(Collectors.toList());
    }

    // 삭제된 항목 되돌리기
    @Transactional
    public void undoDelete(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_TODO));

        todoRepository.undoDeleteTodo(todo.getId());
    }
}
