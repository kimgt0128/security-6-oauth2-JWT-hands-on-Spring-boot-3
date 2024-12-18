package com.wondrous.oauth2_JWT.repository;

import com.wondrous.oauth2_JWT.entity.OAuth2JwtMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<OAuth2JwtMember, Long> {
    OAuth2JwtMember findByUsername(String username);
}
