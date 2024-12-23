package com.wondrous.oauth2_JWT.jwt.controller;

import com.wondrous.oauth2_JWT.jwt.dto.ReissueTokenResponse;
import com.wondrous.oauth2_JWT.jwt.service.JwtTokenProvider;
import com.wondrous.oauth2_JWT.jwt.service.JwtTokenReissueService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor

@RestController
public class ReissuController {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenReissueService jwtTokenReissueService;

    @PostMapping("/reissue")
    public String reissue(@CookieValue(name = "refresh") String refreshToken,
                          HttpServletResponse response) {
        System.out.println("reissu controller, refresh token: " + refreshToken);

        ReissueTokenResponse  reissueToken=  jwtTokenReissueService.reissue(refreshToken);

        String newAccessToken = reissueToken.getAccessToken();
        String newRefreshToken = reissueToken.getRefreshToken();

        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader("access", newAccessToken);
        response.addCookie(createCookie("refresh", newRefreshToken));

        return newAccessToken;
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setHttpOnly(true);
        return cookie;
    }
}
