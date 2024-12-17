package com.wondrous.oauth2_JWT.dto;

import java.util.Map;

public class GoogleResponse implements OAuth2Response{
    private final Map<String, Object> attributes;

    public GoogleResponse(Map<String, Object> attributed) {
        this.attributes = (Map<String, Object>) attributed;
    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getProviderId() {
        return attributes.get("sub").toString();
    }

    @Override
    public String getEmail() {
        return attributes.get("email").toString();
    }

    @Override
    public String getName() {
        return attributes.get("name").toString();
    }
}
