package com.auth;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping("/signup")
    public ResponseEntity<Map<String,Object>> signUp(@RequestBody User user){
        Map<String, Object> response = userService.signUp(user);
        if(response.get("status").equals("error")){
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<Map<String,Object>> signIn(@RequestBody String email,@RequestBody String password) {
        Map<String,Object> response = userService.signIn(email,password);
        if(response.get("status").equals("error")){
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/generateToken")
    public ResponseEntity<String> generateToken(@RequestBody Map<String, String> request) {
        return new ResponseEntity<>("Token Generated", HttpStatus.OK);
    }

}
