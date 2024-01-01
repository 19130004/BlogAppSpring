package com.springboot.blogapp.blogapprestapi.service;

import com.springboot.blogapp.blogapprestapi.payload.LoginDto;
import com.springboot.blogapp.blogapprestapi.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}
