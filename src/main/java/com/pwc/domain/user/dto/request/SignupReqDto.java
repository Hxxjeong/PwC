package com.pwc.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class SignupReqDto {
    @Schema(name = "nickname", example = "user1")
    private String nickname;

    @Schema(name = "password", example = "0000")
    private String password;
}
