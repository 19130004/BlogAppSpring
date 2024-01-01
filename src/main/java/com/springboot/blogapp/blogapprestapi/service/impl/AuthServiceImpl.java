package com.springboot.blogapp.blogapprestapi.service.impl;

import com.springboot.blogapp.blogapprestapi.entity.Role;
import com.springboot.blogapp.blogapprestapi.entity.User;
import com.springboot.blogapp.blogapprestapi.exception.BlogAPIException;
import com.springboot.blogapp.blogapprestapi.payload.LoginDto;
import com.springboot.blogapp.blogapprestapi.payload.RegisterDto;
import com.springboot.blogapp.blogapprestapi.repository.RoleReponsitory;
import com.springboot.blogapp.blogapprestapi.repository.UserReponsitory;
import com.springboot.blogapp.blogapprestapi.security.JwtTokenProvider;
import com.springboot.blogapp.blogapprestapi.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    private AuthenticationManager authenticationManager;
    private UserReponsitory userReponsitory;
    private RoleReponsitory roleReponsitory;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserReponsitory userReponsitory, RoleReponsitory roleReponsitory, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userReponsitory = userReponsitory;
        this.roleReponsitory = roleReponsitory;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);
        return jwt;
    }

    @Override
    public String register(RegisterDto registerDto) {
        if(userReponsitory.existsByUsername(registerDto.getUsername())){
          throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Username is already exists");

        }
        if(userReponsitory.existsByEmail(registerDto.getEmail())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Email is already exists");
        }
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role role = roleReponsitory.findByName("ROLE_USER").get();
        roles.add(role);
        user.setRoles(roles);
        userReponsitory.save(user);
        return "User registered successfully";
    }
}
