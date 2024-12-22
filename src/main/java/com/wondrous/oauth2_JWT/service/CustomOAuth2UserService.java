package com.wondrous.oauth2_JWT.service;

import com.wondrous.oauth2_JWT.dto.*;
import com.wondrous.oauth2_JWT.entity.OAuth2JwtMember;
import com.wondrous.oauth2_JWT.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // 1. 인가 서버

        OAuth2User oAuth2User = super.loadUser(userRequest);

        System.out.println("oauth2Uaer: " + oAuth2User.toString());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();


        // 요청 유효성 검사
        if (registrationId == null) {
            throw new OAuth2AuthenticationException(new OAuth2Error("invalid_user_request", "잘못된 유저 요청입니다.", null));
        }

        // 유저 정보 Dto에 담기
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

        // 이미 가입된 유저인지 확인 후 가입 or 로그인 처리

        //이름이 겹칠 상황을 대비하여 이름 + 리소스 서버 Id로 usernmae 생성
        String username = oAuth2User.getName() + " " + oAuth2Response.getProviderId();
        OAuth2JwtMember existMember = memberRepository.findByUsername(username);


        UserDto userDto = new UserDto(
                "ROLE_USER",
                oAuth2Response.getName(),
                username);

        if (existMember == null) {
            System.out.println("새 유저 회원 가입 중");
            OAuth2JwtMember newMember  = OAuth2JwtMember.newMember(userDto);
            memberRepository.save(newMember);

        }
        else {
            System.out.println("인가 서버로부터 기존 유저 정보 업데이트 중");
            existMember.updateMember(userDto);
            memberRepository.save(existMember);
        }

        return new CustomOAuth2User(userDto);
    }
}
