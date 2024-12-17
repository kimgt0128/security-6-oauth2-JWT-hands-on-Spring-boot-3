package com.wondrous.oauth2_JWT.dto;

import java.util.Map;

public class KaKaoResponse implements OAuth2Response {
    private final Map<String, Object> attributes;

    public KaKaoResponse(Map<String, Object> attributes) {
        this.attributes = attributes;
        System.out.println("Kakao attribute: " + attributes);
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getEmail() {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        return kakaoAccount.get("email").toString();
    }

    @Override
    public String getName() {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        return profile.get("nickname").toString();
    }
}
