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
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final TodoRepository todoRepository;
    private final TagRepository tagRepository;

    // 수정
    @Transactional
    public TodoRspDto updateTodo(Long todoId, TodoReqDto dto) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_TODO));

        if(dto.getTitle() != null) todo.setTitle(dto.getTitle());
        if(dto.getDueDate() != null) todo.setDueDate(dto.getDueDate());

        // 태그 수정 (기존 태그 삭제 후 새로 등록)
        if(dto.getTagNames() != null) {
            todo.getTags().clear();
            Set<Tag> tags = dto.getTagNames().stream()
                    .map(tagName -> tagRepository.findByName(tagName)
                            .orElseGet(() -> tagRepository.save(Tag.builder().name(tagName).build())))
                    .collect(Collectors.toSet());
            todo.getTags().addAll(tags);
        }

        return TodoRspDto.from(todoRepository.save(todo));
    }

    // 삭제
    @Transactional
    public void deleteTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_TODO));

        todoRepository.deleteTodo(todo.getId());
    }

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
