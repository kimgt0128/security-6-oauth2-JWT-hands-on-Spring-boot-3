package com.wondrous.oauth2_JWT.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReissueTokenResponse {

    private String refreshToken;
    private String accessToken;

}
