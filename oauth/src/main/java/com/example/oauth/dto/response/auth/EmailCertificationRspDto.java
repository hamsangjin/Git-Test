package com.example.oauth.dto.response.auth;

import com.example.oauth.common.ResponseCode;
import com.example.oauth.common.ResponseMessage;
import com.example.oauth.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class EmailCertificationRspDto extends ResponseDto {
    public EmailCertificationRspDto() {
        super();
    }

    public static ResponseEntity<EmailCertificationRspDto> success() {
        EmailCertificationRspDto responseBody = new EmailCertificationRspDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> duplicateId() {
//        ResponseDto responseBody = new ResponseDto(ResponseCode.DUPLICATE_ID, ResponseMessage.DUPLICATE_ID);
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        return IdCheckRspDto.duplicateId(); // 위와 같은 역할 (중복 아이디 확인)
    }

    public static ResponseEntity<ResponseDto> mailSendFail() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.MAIL_FAIL, ResponseMessage.MAIL_FAIL);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }
}
