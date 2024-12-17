package com.wondrous.oauth2_JWT.service;

import com.wondrous.oauth2_JWT.dto.GoogleResponse;
import com.wondrous.oauth2_JWT.dto.KaKaoResponse;
import com.wondrous.oauth2_JWT.dto.NaverResponse;
import com.wondrous.oauth2_JWT.dto.OAuth2Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();



        if (registrationId == null) {
            throw new OAuth2AuthenticationException(new OAuth2Error("invalid_user_request", "잘못된 유저 요청입니다.", null));
        }

        OAuth2Response oAuth2Response = null;

        if (registrationId.equals("naver")) {
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }
        else if (registrationId.equals("google")) {
            oAuth2Response =  new GoogleResponse(oAuth2User.getAttributes());
        }

        else if (registrationId.equals("kakao")) {
            oAuth2Response = new KaKaoResponse(oAuth2User.getAttributes());
        }

        else {
            throw new OAuth2AuthenticationException(new OAuth2Error("invalid_social_account", "잘못된 소셜 계정입니다.", null));
        }



        return super.loadUser(userRequest);
    }
}
