package com.Projects.marketplace.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {
    @NotBlank(message = "username can't be blank")
    private String username;
//    @Pattern(
//            regexp = "^[a-zA-Z0-9+_.-]+@bietjhs\\.edu$",
//            message = "only college email allowed"
//    ) only for a single college domain . or multiple collegs we store domain of
    //colleges in db and can check in service if given domain exists
    @Email
    private String email;
    @Pattern(
            regexp ="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$",
            message = "password must contain atleast 2 lower case, 1 uppercase , 1 symbol,1 number and min length=8 and max length=16"
    )
    private String password;
}
