package com.pwc.common.util;

import com.pwc.domain.user.entity.Role;
import com.pwc.domain.user.entity.User;
import com.pwc.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Optional<User> adminUser = userRepository.findByNickname("admin");

        if(adminUser.isEmpty()) {
            User user = new User();
            user.setNickname("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setRole(Role.ROLE_ADMIN);
            userRepository.save(user);
        }
    }
}
