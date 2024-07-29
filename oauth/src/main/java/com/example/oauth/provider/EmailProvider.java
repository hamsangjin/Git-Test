package com.example.oauth.provider;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailProvider {
    private final JavaMailSender mailSender;
    private final String SUBJECT = "[서비스 이름] 인증 메일입니다.";

    // 인증 메일 전송
    public boolean sendCertificationMail(String email, String certificationNumber) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            String htmlContent = getCertificationMessage(certificationNumber);

            helper.setTo(email);    // 메일 전송
            helper.setSubject(SUBJECT);
            helper.setText(htmlContent, true);  // html 적용하여 메시지 전송

            mailSender.send(message);
        }
        catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private String getCertificationMessage(String certificationNumber) {
        String certificationMessage = "";
        certificationMessage += """
                <h1 style="text-align: center">[서비스 이름] 인증 메일</h1>
                    <h3 style="text-align: center">인증 코드: <strong style="font-size: 32px; letter-spacing: 8px">
                """;
        certificationMessage += certificationNumber;
        certificationMessage += "</strong></h3>";
        return certificationMessage;
    }
}
