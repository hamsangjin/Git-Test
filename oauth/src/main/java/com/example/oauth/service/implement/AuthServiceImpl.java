package com.example.oauth.service.implement;

import com.example.oauth.common.CertificationNumber;
import com.example.oauth.dto.request.auth.*;
import com.example.oauth.dto.response.ResponseDto;
import com.example.oauth.dto.response.auth.*;
import com.example.oauth.entity.Certification;
import com.example.oauth.entity.User;
import com.example.oauth.provider.EmailProvider;
import com.example.oauth.provider.JwtProvider;
import com.example.oauth.repository.CertificationRepository;
import com.example.oauth.repository.UserRepository;
import com.example.oauth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final CertificationRepository certificationRepository;

    private final JwtProvider jwtProvider;
    private final EmailProvider emailProvider;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<? super IdCheckRspDto> idCheck(IdCheckReqDto reqDto) {
        try {
            String userId = reqDto.getId();
            boolean isExistUserId = userRepository.existsByUserId(userId);
            if(isExistUserId) return IdCheckRspDto.duplicateId();
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return IdCheckRspDto.success();
    }

    @Override
    public ResponseEntity<? super EmailCertificationRspDto> emailCertification(EmailCertificationReqDto reqDto) {
        try{
            String id = reqDto.getId();
            String email = reqDto.getEmail();

            boolean isExistUserId = userRepository.existsByUserId(id);
            if(isExistUserId) return EmailCertificationRspDto.duplicateId();    // id가 중복될 경우

            String certificationNumber = CertificationNumber.certificationNumber();

            // 메일 전송
            boolean isSucceed = emailProvider.sendCertificationMail(email, certificationNumber);
            if(!isSucceed) return EmailCertificationRspDto.mailSendFail();

            Certification certification = new Certification(id, email, certificationNumber);
            certificationRepository.save(certification);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return EmailCertificationRspDto.success();
    }

    // 인증 번호 확인
    @Override
    public ResponseEntity<? super CheckCertificationRspDto> checkCertification(CheckCertificationReqDto reqDto) {
        try {
            String id = reqDto.getId();
            String email = reqDto.getEmail();
            String certificationNumber = reqDto.getCertificationNumber();

            Certification certification = certificationRepository.findByUserId(id);
            if(certification == null) return CheckCertificationRspDto.certificationFail();

            boolean isMatched = certification.getEmail().equals(email) && certification.getCertificationNumber().equals(certificationNumber);
            if(!isMatched) return CheckCertificationRspDto.certificationFail(); // 인증 번호가 일치하지 않을 때
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return CheckCertificationRspDto.success();
    }

    // 회원가입
    @Override
    public ResponseEntity<? super SignUpRspDto> signUp(SignUpReqDto reqDto) {
        try {
            String id = reqDto.getId();
            boolean isExistUserId = userRepository.existsByUserId(id);
            if(isExistUserId) return SignUpRspDto.duplicateId();

            String email = reqDto.getEmail();
            String certificationNumber = reqDto.getCertificationNumber();

            Certification certification = certificationRepository.findByUserId(id);
            boolean isMatched = certification.getEmail().equals(email) && certification.getCertificationNumber().equals(certificationNumber);
            if(!isMatched) return SignUpRspDto.certificationFail();

            String password = reqDto.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            reqDto.setPassword(encodedPassword);

            User user = new User(reqDto);
            userRepository.save(user);

            certificationRepository.deleteByUserId(id); // 인증이 완료되면 내역 지우기
//            certificationRepository.delete(certification);    // 유저 당 인증이 하나만 만들어지므로 위와 작업이 같음
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return SignUpRspDto.success();
    }

    // 로그인
    @Override
    public ResponseEntity<? super SignInRspDto> signIn(SignInReqDto reqDto) {
        String token = null;
        try {
            String userId = reqDto.getId();
            User user = userRepository.findByUserId(userId);
            if(user == null) return SignInRspDto.signInFail();

            String password = reqDto.getPassword();
            String encodedPassword = user.getPassword();
            boolean isMatched = passwordEncoder.matches(password, encodedPassword);
            if(!isMatched) return SignInRspDto.signInFail();

            // 토큰 생성
            token = jwtProvider.createJwt(userId);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return SignInRspDto.success(token);
    }
}
