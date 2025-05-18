package com.APIGateway;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.APIGateway.utils.JWTUtil;

@RestController
@RequestMapping("/api")
public class APIGatewayController {

    @Value("${users.service.url}")
    private String usersServiceUrl;

    @Value("${auth.service.url}")
    private String authServiceUrl;

    private JWTUtil jwtUtil;

    public APIGatewayController(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<Map<String, Object>> signIn(@RequestBody Map<String, Object> request) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(request);
        String url = authServiceUrl+"/signin";
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
                new ParameterizedTypeReference<Map<String, Object>>() {
                });
        return response;
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<Map<String, Object>> signUp(@RequestBody Map<String, Object> request) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(request);
        String url = authServiceUrl+"/signup";
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
                new ParameterizedTypeReference<Map<String, Object>>() {
                });
        return response;
    }

    @PostMapping("/auth/generateToken")
    public ResponseEntity<Map<String, Object>> generateToken(@RequestBody Map<String, Object> request) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(request);
        String url = authServiceUrl+"/generateToken";
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
                new ParameterizedTypeReference<Map<String, Object>>() {
                });
        return response;
    }

    @GetMapping("/user")
    public ResponseEntity<Map<String, Object>> getAllUsers(@RequestHeader("Authorization") String authorization) {
        String token = authorization.substring(7);
        Map<String, Object> tokenValidation = jwtUtil.validateToken(token);
        if (tokenValidation.get("status").equals("valid")) {
            RestTemplate restTemplate = new RestTemplate();
            Map<String,Object> claims = jwtUtil.getClaims(token);
            System.out.println("claims: "+claims);
            HttpEntity<Map<String,Object>> requestEntity = new HttpEntity<>(claims,null);
            String url = usersServiceUrl;
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
                    new ParameterizedTypeReference<Map<String, Object>>() {
                    });
            return new ResponseEntity(response.getBody(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity(Map.of("status", "Failed", "message", "Token is invalid"),HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Map<String, Object>> getUserById(@RequestHeader("Authorization") String authorization, @PathVariable Long id) {
        String token = authorization.substring(7);
        Map<String, Object> tokenValidation = jwtUtil.validateToken(token);
        if (tokenValidation.get("status").equals("valid")) {
            RestTemplate restTemplate = new RestTemplate();
            Map<String,Object> claims = jwtUtil.getClaims(token);
            System.out.println("claims: "+claims);
            HttpEntity<Map<String,Object>> requestEntity = new HttpEntity<>(claims,null);
            String url = usersServiceUrl + "/" + id;
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
                    new ParameterizedTypeReference<Map<String, Object>>() {
                    });
            return new ResponseEntity(response.getBody(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity(Map.of("status", "Failed", "message", "Token is invalid"),HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@RequestHeader("Authorization") String authorization, @PathVariable Long id,
            @RequestBody Map<String, Object> request) {
        String token = authorization.substring(7);
        Map<String, Object> tokenValidation = jwtUtil.validateToken(token);
        if (tokenValidation.get("status").equals("valid")) {
            RestTemplate restTemplate = new RestTemplate();
            Map<String,Object> claims = jwtUtil.getClaims(token);
            System.out.println("claims: "+claims);
            request.put("claims", claims);
            HttpEntity<Map<String,Object>> requestEntity = new HttpEntity<>(request,null);
            String url = usersServiceUrl+ "/" + id;
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity,
                    new ParameterizedTypeReference<Map<String, Object>>() {
                    });
            return new ResponseEntity(response.getBody(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity(Map.of("status", "Failed", "message", "Token is invalid"),HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@RequestHeader("Authorization") String authorization, @PathVariable Long id) {
        String token = authorization.substring(7);
        Map<String, Object> tokenValidation = jwtUtil.validateToken(token);
        if (tokenValidation.get("status").equals("valid")) {
            RestTemplate restTemplate = new RestTemplate();
            Map<String,Object> claims = jwtUtil.getClaims(token);
            System.out.println("claims: "+claims);
            HttpEntity<Map<String,Object>> requestEntity = new HttpEntity<>(claims,null);
            String url = usersServiceUrl+ "/" + id;
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity,
                    new ParameterizedTypeReference<Map<String, Object>>() {
                    });
            return new ResponseEntity(response.getBody(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity(Map.of("status", "Failed", "message", "Token is invalid"),HttpStatus.UNAUTHORIZED);
        }
    }
    

}
