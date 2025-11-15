package com.pwc.domain.user.entity;

import com.pwc.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(unique = true)
    private String nickname;

    @Column(name = "is_delete")
    private boolean isDelete = false;

    @Builder
    public User(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
        this.role = Role.ROLE_USER;
        this.isDelete = false;
    }
}
