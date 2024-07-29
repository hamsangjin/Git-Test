package com.example.oauth.entity;

import com.example.oauth.dto.request.auth.SignUpReqDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "user_id")
    private String userId;
    private String password;
    private String email;
    private String type;
    private String role;

    public User(SignUpReqDto reqDto) {
        this.userId = reqDto.getId();
        this.password = reqDto.getPassword();
        this.email = reqDto.getEmail();
        this.type = "app";
        this.role = "ROLE_USER";
    }

    public User(String userId, String email, String type) {
        this.userId = userId;
        this.password = "password1234"; // 소셜 로그인이기 때문에 의미없음
        this.email = email;
        this.type = type;
        this.role = "ROLE_USER";
    }
}
