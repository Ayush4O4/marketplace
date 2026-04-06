package com.Projects.marketplace.controller;


import com.Projects.marketplace.dto.UserLoginDto;
import com.Projects.marketplace.dto.UserLoginResponseDto;
import com.Projects.marketplace.dto.UserRegisterDto;
import com.Projects.marketplace.dto.UserRegisterResponseDto;
import com.Projects.marketplace.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public UserLoginResponseDto login(@Valid @RequestBody UserLoginDto userLoginDto){
        return authService.login(userLoginDto);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserRegisterResponseDto registerUser(@Valid @RequestBody UserRegisterDto userRegisterDto){
        return authService.registerUser(userRegisterDto);
    }


}
