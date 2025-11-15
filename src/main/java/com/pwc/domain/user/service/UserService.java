package com.pwc.domain.user.service;

import com.pwc.common.auth.JwtProvider;
import com.pwc.common.exception.BusinessException;
import com.pwc.common.exception.ErrorCode;
import com.pwc.domain.user.dto.request.SignupReqDto;
import com.pwc.domain.user.entity.User;
import com.pwc.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    // 회원가입
    @Transactional
    public User signUp(SignupReqDto dto) {
        if(userRepository.findByNickname(dto.getNickname()).isPresent())
            throw new BusinessException(ErrorCode.ALREADY_USER);

        User user = User.builder()
                .nickname(dto.getNickname())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();
        return userRepository.save(user);
    }

    // 로그인
    @Transactional(readOnly = true)
    public String login(String nickname, String rawPassword) {
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

        if(!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new BusinessException(ErrorCode.INCORRECT_PASSWORD);
        }

        return jwtProvider.generateToken(user.getNickname(), user.getRole());
    }
}
