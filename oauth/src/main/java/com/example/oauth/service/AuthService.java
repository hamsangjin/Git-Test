package com.example.oauth.service;

import com.example.oauth.dto.request.auth.*;
import com.example.oauth.dto.response.auth.*;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<? super IdCheckRspDto> idCheck(IdCheckReqDto reqDto);
    ResponseEntity<? super EmailCertificationRspDto> emailCertification(EmailCertificationReqDto reqDto);
    ResponseEntity<? super CheckCertificationRspDto> checkCertification(CheckCertificationReqDto reqDto);
    ResponseEntity<? super SignUpRspDto> signUp(SignUpReqDto reqDto);
    ResponseEntity<? super SignInRspDto> signIn(SignInReqDto reqDto);
}
