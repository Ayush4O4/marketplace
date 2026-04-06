package com.Projects.marketplace.service;

import com.Projects.marketplace.dto.UserLoginDto;
import com.Projects.marketplace.dto.UserLoginResponseDto;
import com.Projects.marketplace.dto.UserRegisterDto;
import com.Projects.marketplace.dto.UserRegisterResponseDto;
import com.Projects.marketplace.entity.User;
import com.Projects.marketplace.exception.EmailDomainNotFoundException;
import com.Projects.marketplace.exception.UserAlreadyExistException;
import com.Projects.marketplace.jwt.JwtUtil;
import com.Projects.marketplace.repositories.CollegeRepositoy;
import com.Projects.marketplace.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CollegeRepositoy collegeRepositoy;

    public UserLoginResponseDto login(UserLoginDto userLoginDto){
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDto.getUsername(),userLoginDto.getPassword()));
      User user=userRepository.findByUsername(userLoginDto.getUsername())
              .orElseThrow(()-> new UsernameNotFoundException("user not found"));
      return new UserLoginResponseDto(user.getUsername(),user.getRole(), jwtUtil.generateTokenFromUserName(user.getUsername()));
    }

    public UserRegisterResponseDto registerUser(UserRegisterDto userRegisterDto){
        if(userRepository.findByUsername(userRegisterDto.getUsername()).isPresent()|| userRepository.findByEmail(userRegisterDto.getEmail()).isPresent()){
            throw new UserAlreadyExistException("user already exist");
        }
        String email=userRegisterDto.getEmail();
        String domain= email.substring(email.indexOf("@")+1);
        if(collegeRepositoy.findByDomain(domain).isEmpty()){
            throw new EmailDomainNotFoundException("college email domain not found");
        }
        User user=new User();
        user.setUsername(userRegisterDto.getUsername());
        user.setEmail(userRegisterDto.getEmail());
        String hashedPassword= passwordEncoder.encode(userRegisterDto.getPassword());
        user.setPassword(hashedPassword);

        User savedUser=userRepository.save(user);

        return  new UserRegisterResponseDto(savedUser.getUsername(),savedUser.getRole());
    }

}
