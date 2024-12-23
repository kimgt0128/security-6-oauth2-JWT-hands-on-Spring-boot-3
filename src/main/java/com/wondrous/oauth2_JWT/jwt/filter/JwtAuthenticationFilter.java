package com.wondrous.oauth2_JWT.jwt.filter;


import com.wondrous.oauth2_JWT.dto.CustomOAuth2User;
import com.wondrous.oauth2_JWT.dto.UserDto;
import com.wondrous.oauth2_JWT.jwt.service.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor

//필터는 한 번만 요청되면 되므로 OncePerRequestFIlter 상속
public class JwtAuthenticationFilter extends OncePerRequestFilter {

     private final JwtTokenProvider jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = null;
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("Authorization")) {
                authorization = cookie.getValue();
            }
        }

        //Authorization 검증
        if (authorization == null) {
            System.out.println("Authorization cookie not found");
            filterChain.doFilter(request, response);

            // 조건에 해당되면 다음 필터로 넘기기
            return;
        }

        String token = authorization;

        //토큰 검증
        if (jwtUtil.isExpired(token)) {
            System.out.println("token is expired");
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰에서 username과 role 획득
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        //UserDTO를 생성하여 값 설정
        UserDto userDto = UserDto.builder()
                .name(username)
                .role(role).build();

        //UserDetails에 회원 정보 객체 담기
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDto);

        // 스프링 시큐리티에 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());

        // 세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);

    }
}
