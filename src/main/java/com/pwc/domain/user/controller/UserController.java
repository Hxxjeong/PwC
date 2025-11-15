package com.pwc.domain.user.controller;

import com.pwc.common.RspTemplate;
import com.pwc.domain.user.dto.request.SignupReqDto;
import com.pwc.domain.user.dto.response.TokenRspDto;
import com.pwc.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public RspTemplate<String> signup(@RequestBody SignupReqDto dto) {
        userService.signUp(dto);
        return new RspTemplate<>(HttpStatus.OK, "회원가입이 완료되었습니다.");
    }

    @PostMapping("/login")
    public RspTemplate<TokenRspDto> login(@RequestBody SignupReqDto dto) {
        String token = userService.login(dto.getNickname(), dto.getPassword());
        return new RspTemplate<>(HttpStatus.OK, "로그인 완료", new TokenRspDto(token));
    }
}
