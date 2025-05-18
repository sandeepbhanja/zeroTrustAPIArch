package com.auth;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public Map<String,Object> signUp(User user);
    public Map<String,Object> signIn(String email , String password);
    public Map<String,Object> generateToken(String username, String password);
}
