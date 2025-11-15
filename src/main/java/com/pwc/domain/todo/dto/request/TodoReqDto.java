package com.pwc.domain.todo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class TodoReqDto {
    @Schema(name = "title", example = "todo1")
    private String title;

    @Schema(name = "dueDate", example = "2025-11-30")
    private LocalDate dueDate;

    @Schema(name = "tagNames", type = "List")
    private List<String> tagNames;

    @Getter
    @Builder
    public static class SearchDto {
        private String createUser;
        private String tagName;
        private Boolean isDone;
    }

    @Getter
    public static class UpdateDto {
        @Schema(name = "title", example = "todo1")
        private String title;

        @Schema(name = "dueDate", example = "2025-11-30")
        private LocalDate dueDate;

        @Schema(name = "tagNames", type = "List")
        private List<String> tagNames;

        @Schema(name = "seq", type = "Long")
        private Long seq;
    }
}
