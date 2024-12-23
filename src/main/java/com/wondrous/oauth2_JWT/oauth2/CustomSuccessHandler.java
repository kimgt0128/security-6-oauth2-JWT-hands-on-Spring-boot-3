package com.wondrous.oauth2_JWT.oauth2;

import com.wondrous.oauth2_JWT.dto.CustomOAuth2User;
import com.wondrous.oauth2_JWT.jwt.service.JwtTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@RequiredArgsConstructor

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtUtil;




    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //OAuth2User
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        String username = customOAuth2User.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> authoritiesIterator = authorities.iterator();
        GrantedAuthority auth = authoritiesIterator.next();
        String role = auth.getAuthority();

        // 토큰 생성
        String access = jwtUtil.createJwt("access", username, role, 60*60*60L);
        String refresh = jwtUtil.createJwt("refresh", username, role, 86400000L);



        // 토큰 저장 - 이후 구현

        // 응답 설정
        response.setStatus(HttpStatus.OK.value());
        response.setHeader("access", access);
        response.addCookie(createCookie("access", access));
        response.sendRedirect("http//localhost:3000/");


    }

    // 이후 모듈화
    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        //cookie.setSecure(true); - https에서만 허용하도록(실제 배포시)
        cookie.setMaxAge(60*60*60);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
}
