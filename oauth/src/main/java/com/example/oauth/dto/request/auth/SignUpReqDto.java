package com.example.oauth.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignUpReqDto {
    @NotBlank
    private String id;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{8,13}$")   // 8~13 자리의 영문, 숫자 비밀번호 패턴
    private String password;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String certificationNumber;
}
