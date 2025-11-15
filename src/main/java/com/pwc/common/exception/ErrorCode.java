package com.pwc.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // 인증/인가
    NO_PERMISSION(403, "권한이 없습니다."),

    // 유저
    ALREADY_USER(500, "이미 존재하는 유저입니다."),
    NOT_FOUND_USER(404, "존재하지 않는 유저입니다."),
    INCORRECT_PASSWORD(500, "비밀번호가 일치하지 않습니다."),

    // 투두
    NOT_FOUND_TODO(404, "존재하지 않는 todo입니다."),
    ;

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}