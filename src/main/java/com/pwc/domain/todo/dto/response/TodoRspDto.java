package com.pwc.domain.todo.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pwc.domain.tag.entity.Tag;
import com.pwc.domain.todo.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoRspDto {
    private Long id;

    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    private Set<String> tagNames;

    private Long seq;

    private boolean isDone;

    private Long dDay;

    private boolean isDelete;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String createUser;

    private String updateUser;

    public static TodoRspDto from(Todo todo) {
        // D-Day 계산
        long dDay = 0;
        if(todo.getDueDate() != null) dDay = ChronoUnit.DAYS.between(LocalDate.now(), todo.getDueDate());

        return TodoRspDto.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .dueDate(todo.getDueDate())
                .tagNames(todo.getTags() == null ? null : todo.getTags().stream().map(Tag::getName).collect(Collectors.toSet()))
                .seq(todo.getSeq())
                .isDone(todo.isDone())
                .dDay(dDay)
                .createTime(todo.getCreateTime())
                .updateTime(todo.getUpdateTime())
                .createUser(todo.getCreateUser())
                .updateUser(todo.getUpdateUser())
                .build();
    }
}
