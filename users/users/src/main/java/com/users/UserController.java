package com.users;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.users.utils.RoleCheck;

@RestController
@RequestMapping("/users")
public class UserController {

    UserService userService;
    RoleCheck roleCheck;

    public UserController(UserService userService,RoleCheck roleCheck) {
        this.userService = userService;
        this.roleCheck = roleCheck;
    }

    @PostMapping()
    public ResponseEntity<Map<String, Object>> getAllUsers(@RequestBody Map<String,Object> requestBody) {
        if(!requestBody.get("role").toString().equalsIgnoreCase("admin")) {
            Map<String, Object> Map = new HashMap<>();
            Map.put("status", "error");
            Map.put("message", "Unauthorized access");
            return new ResponseEntity<>(Map, HttpStatus.UNAUTHORIZED);
        }
        Map<String, Object> response = userService.getAllUsers();
        if(response.get("status")=="error") {
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable Long id,@RequestBody Map<String,Object> requestBody) {
        boolean isAuthorized = roleCheck.checkAccess(requestBody,id);
        if(!isAuthorized) {
            Map<String, Object> Map = new HashMap<>();
            Map.put("status", "error");
            Map.put("message", "Unauthorized access");
            return new ResponseEntity<>(Map, HttpStatus.UNAUTHORIZED);
        }
        Map<String, Object> response = userService.getUserById(id);
        if(response.get("status")=="error") {
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long id, @RequestBody Map<String,Object> requestBody) {
        boolean isAuthorized = roleCheck.checkAccess((Map<String,Object>)requestBody.get("claims"),id);
        if(!isAuthorized) {
            Map<String, Object> Map = new HashMap<>();
            Map.put("status", "error");
            Map.put("message", "Unauthorized access");
            return new ResponseEntity<>(Map, HttpStatus.UNAUTHORIZED);
        }
        requestBody.remove("claims");
        // User updatedUser = new User();
        Map<String, Object> response = new HashMap<>();;
        response.put("message", "User updated successfully for ID: " + id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long id,@RequestBody Map<String,Object> requestBody) {
        boolean isAuthorized = roleCheck.checkAccess(requestBody,id);
        if(!isAuthorized) {
            Map<String, Object> Map = new HashMap<>();
            Map.put("status", "error");
            Map.put("message", "Unauthorized access");
            return new ResponseEntity<>(Map, HttpStatus.UNAUTHORIZED);
        }
        Map<String, Object> response = userService.deleteUser(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
