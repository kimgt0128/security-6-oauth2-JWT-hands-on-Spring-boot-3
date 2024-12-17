package com.wondrous.oauth2_JWT.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserDto {

    private String role;
    private String name;
    private String username;


    public UserDto UserDto(String role, String name, String username) {
        return builder()
                .role(role)
                .name(name)
                .username(username).build();

    }
}
