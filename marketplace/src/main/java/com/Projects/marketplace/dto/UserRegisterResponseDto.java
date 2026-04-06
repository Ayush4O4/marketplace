package com.Projects.marketplace.dto;

import com.Projects.marketplace.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterResponseDto {

    private String username;
    private Role role;
}
