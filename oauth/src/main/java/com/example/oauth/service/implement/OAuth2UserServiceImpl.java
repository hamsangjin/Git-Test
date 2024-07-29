package com.example.oauth.service.implement;

import com.example.oauth.entity.CustomOauth2User;
import com.example.oauth.entity.User;
import com.example.oauth.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImpl extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String oauth2ClientName = userRequest.getClientRegistration().getClientName();

        // 유저 정보 확인
//        try {
//            System.out.println(new ObjectMapper().writeValueAsString(oAuth2User.getAttributes()));
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }

        User user = null;
        String userId = null;
        String email = "email@email.com";

        if(oauth2ClientName.equals("kakao")) {
            userId = "kakao_" + oAuth2User.getAttributes().get("id");
            user = new User(userId, email, "kakao");
        }

        if(oauth2ClientName.equals("naver")) {
            Map<String, String> responseMap = (Map<String, String>) oAuth2User.getAttributes().get("response");
            userId = "naver_" + responseMap.get("id").substring(0, 14);
            email = responseMap.get("email");
            user = new User(userId, email, "naver");
        }

        userRepository.save(user);

        return new CustomOauth2User(userId);
    }
}
