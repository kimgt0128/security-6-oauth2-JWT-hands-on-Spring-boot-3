package com.wondrous.oauth2_JWT.entity;

import com.wondrous.oauth2_JWT.dto.UserDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class OAuth2JwtMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String name;

    private String email;

    private String role;

    public static OAuth2JwtMember newMember(UserDto userDto) {
        return builder()
                .name(userDto.getName())
                .username(userDto.getUsername())
                .role(userDto.getRole()).build();
    }

    public void updateMember(UserDto userDto) {
        this.name = userDto.getName();
        this.username = userDto.getUsername();
        this.role = userDto.getRole();
    }


}
