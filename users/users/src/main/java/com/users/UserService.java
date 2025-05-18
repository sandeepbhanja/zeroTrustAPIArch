package com.users;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.users.ext.User;


@Service
public interface UserService {

    Map<String,Object> getUserById(Long id);

    Map<String,Object> createUser(User user);

    Map<String,Object> updateUser(Long id, User user, boolean isAdmin);

    Map<String,Object> deleteUser(Long id);

    Map<String,Object> getAllUsers();

    // Other service methods can be added here as needed

}
