package com.example.oauth.controller;

import com.example.oauth.dto.request.auth.*;
import com.example.oauth.dto.response.auth.*;
import com.example.oauth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/id-check")
    public ResponseEntity<? super IdCheckRspDto> idCheck(@RequestBody @Valid IdCheckReqDto reqDto) {
        ResponseEntity<? super IdCheckRspDto> response = authService.idCheck(reqDto);
        return response;
    }

    @PostMapping("/email-certification")
    public ResponseEntity<? super EmailCertificationRspDto> emailCertification(@RequestBody @Valid EmailCertificationReqDto reqDto) {
        ResponseEntity<? super EmailCertificationRspDto> response = authService.emailCertification(reqDto);
        return response;
    }

    @PostMapping("/check-certification")
    public ResponseEntity<? super CheckCertificationRspDto> checkCertification(@RequestBody @Valid CheckCertificationReqDto reqDto) {
        ResponseEntity<? super CheckCertificationRspDto> response = authService.checkCertification(reqDto);
        return response;
    }

    // 회원가입
    @PostMapping("/sign-up")
    public ResponseEntity<? super SignUpRspDto> signUp(@RequestBody @Valid SignUpReqDto reqDto) {
        ResponseEntity<? super SignUpRspDto> response = authService.signUp(reqDto);
        return response;
    }

    // 로그인
    @PostMapping("/sign-in")
    public ResponseEntity<? super SignInRspDto> signIn(@RequestBody @Valid SignInReqDto reqDto) {
        ResponseEntity<? super SignInRspDto> response = authService.signIn(reqDto);
        return response;
    }
}
