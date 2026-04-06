package com.Projects.marketplace.dto;

import com.Projects.marketplace.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponseDto {

    private String username;
    private Role role;
    private String token;
}
