package com.wondrous.oauth2_JWT.jwt.service;

import com.wondrous.oauth2_JWT.jwt.dto.ReissueTokenResponse;
import com.wondrous.oauth2_JWT.jwt.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor

@Service
public class JwtTokenReissueService {

    private final JwtTokenProvider jwtTokenProvider;

    public ReissueTokenResponse reissue(String refreshToken) {
        System.out.println("refreshToken: " + refreshToken);

        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new IllegalArgumentException("Refresh token cannot be null or empty");
            //이후 Exception Handler로 대체
        }

        if (jwtTokenProvider.isExpired(refreshToken)) {
            throw new IllegalArgumentException("Refresh token is expired");
        }

        String category = jwtTokenProvider.getCategory(refreshToken);
        System.out.println("category: " + category);
        if (!category.equals("refresh")) {
            throw new IllegalArgumentException("Refresh token does not match expected category");
        }

        String username = jwtTokenProvider.getUsername(refreshToken);
        String role = jwtTokenProvider.getRole(refreshToken);

        // RTR(Refresh Token Rotate) 방식 적용
        System.out.println("reissued token created");
        String newAccessToken = jwtTokenProvider.createJwt("access", username, role,  600000L);
        String newRefreshToken = jwtTokenProvider.createJwt("refresh", username, role, 86400000L);

        return new ReissueTokenResponse(newRefreshToken, newAccessToken);

    }




}
