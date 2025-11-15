package com.pwc.domain.todo.service;

import com.pwc.common.auth.SecurityUtil;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final TagRepository tagRepository;

    // 생성
    @Transactional
    public TodoRspDto createTodo(TodoReqDto dto) {
        Todo todo = Todo.builder()
                .title(dto.getTitle())
                .dueDate(dto.getDueDate())
//                .seq(dto.getSeq())  // 처음에 등록할 때는 제일 위에 오게
                .isDone(false)
                .isDelete(false)
                .tags(new HashSet<>())
                .build();

        // 태그 처리
        if(dto.getTagNames() != null) {
            Set<Tag> tags = dto.getTagNames().stream()
                    .map(tagName -> tagRepository.findByName(tagName)
                            .orElseGet(() -> tagRepository.save(Tag.builder().name(tagName).build())))
                    .collect(Collectors.toSet());

            todo.getTags().addAll(tags);
        }

        return TodoRspDto.from(todoRepository.save(todo));
    }

    // 조회 (필터링 포함)
    @Transactional(readOnly = true)
    public List<TodoRspDto> getTodos(TodoReqDto.SearchDto dto) {
        List<Todo> todos = todoRepository.searchTodo(
                dto.getCreateUser(),
                dto.getTagName(),
                dto.getIsDone()
        );

        return todos.stream()
                .map(TodoRspDto::from)
                .collect(Collectors.toList());
    }

    // 수정
    @Transactional
    public TodoRspDto updateTodo(Long todoId, TodoReqDto dto) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_TODO));

        String currentUser = SecurityUtil.getCurrentUsername(); // 로그인한 닉네임

        // 본인 글인지 확인
        if(!todo.getCreateUser().equals(currentUser)) throw new BusinessException(ErrorCode.NO_PERMISSION);

        // 수정
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

        String currentUser = SecurityUtil.getCurrentUsername();

        // 본인 글인지 확인
        if(!todo.getCreateUser().equals(currentUser)) throw new BusinessException(ErrorCode.NO_PERMISSION);

        todoRepository.deleteTodo(todo.getId());
    }
}
