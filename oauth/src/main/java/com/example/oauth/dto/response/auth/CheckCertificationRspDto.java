package com.example.oauth.dto.response.auth;

import com.example.oauth.common.ResponseCode;
import com.example.oauth.common.ResponseMessage;
import com.example.oauth.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class CheckCertificationRspDto extends ResponseDto {
    private CheckCertificationRspDto() {
        super();
    }

    public static ResponseEntity<CheckCertificationRspDto> success() {
        CheckCertificationRspDto responseBody = new CheckCertificationRspDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> certificationFail() {
        // 아래 코드와 리턴값 동일
//        ResponseDto responseBody = new ResponseDto(ResponseCode.CERTIFICATION_FAIL, ResponseMessage.CERTIFICATION_FAIL);
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
        return SignUpRspDto.certificationFail();
    }
}
