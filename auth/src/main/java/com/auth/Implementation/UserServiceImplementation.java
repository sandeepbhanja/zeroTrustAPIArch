package com.auth.Implementation;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.User;
import com.auth.UserRepository;
import com.auth.UserService;

@Service
public class UserServiceImplementation implements UserService{

    UserRepository userRepository;
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Map<String,Object> signUp(User userToSignUp) {
        User userList = userRepository.findByEmail(userToSignUp.getEmail());
        if(userList == null){
            String encodedPassword = encoder.encode(userToSignUp.getPassword());
            userToSignUp.setPassword(encodedPassword);
            userRepository.save(userToSignUp);
            return Map.of("status", "success", "message", "User registered successfully");
        } else {
            return Map.of("status", "error", "message", "Email already exists");
        }
    }

    @Override
    public Map<String,Object> signIn(String email, String password) {
        User user = this.userRepository.findByEmail(email);
        if(user!=null){
            boolean isPasswordMatch = encoder.matches(password, user.getPassword());
            if(isPasswordMatch){
                return Map.of("status", "success", "message", "User logged in successfully");
            } else {
                return Map.of("status", "error", "message", "Invalid password");
            }
        }
        return Map.of("status", "error", "message", "User not found");
    }

    @Override
    public String generateToken(String username, String password) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateToken'");
    }
    
}
