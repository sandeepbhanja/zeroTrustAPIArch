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
        System.out.println("User==="+user);
        Map<String, Object> response = userService.signUp(user);
        if(response.get("status").equals("error")){
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<Map<String,Object>> signIn(@RequestBody Map<String,String> loginDetails) {
        Map<String,Object> response = userService.signIn(loginDetails.get("email"),loginDetails.get("password"));
        if(response.get("status").equals("error")){
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/generateToken")
    public ResponseEntity<Map<String,Object>> generateToken(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");
        Map<String, Object> response = userService.generateToken(email, password);
        if (response.get("status").equals("error")) {
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
