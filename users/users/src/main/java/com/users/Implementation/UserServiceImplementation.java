package com.users.Implementation;

import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.users.UserRepository;
import com.users.UserService;
import com.users.ext.User;

@Service
public class UserServiceImplementation implements UserService {
    // This class is a placeholder for the actual service implementation.
    // In a real application, this would contain the business logic for user
    // management.

    UserRepository userRepository;
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Map<String, Object> getUserById(Long id) {
        try {
            User user = userRepository.findById(id).orElse(null);
            if (user == null) {
                return Map.of("status", "error", "message", "User not found");
            }
            return Map.of("status", "success", "user", user);
        } catch (Exception e) {
            return Map.of("status", "error", "message", "An error occurred while fetching the user");
        }
    }

    @Override
    public Map<String, Object> createUser(User user) {
        try {
            userRepository.save(user);
            return Map.of("status", "success", "message", "User created successfully");
        } catch (Exception e) {
            return Map.of("status", "error", "message", "An error occurred while creating the user");
        }
    }

    @Override
    public Map<String, Object> updateUser(Long id, User user,boolean isAdmin) {
        try {
            User existingUser = userRepository.findById(id).orElse(null);
            if (existingUser == null) {
                return Map.of("status", "error", "message", "User not found");
            }
            existingUser.setUsername(user.getUsername()!=null?user.getUsername():existingUser.getUsername());
            existingUser.setFirstName(user.getFirstName()!=null?user.getFirstName():existingUser.getFirstName());
            existingUser.setLastName(user.getLastName()!=null?user.getLastName():existingUser.getLastName());
            existingUser.setEmail(user.getEmail()!=null?user.getEmail():existingUser.getEmail());
            existingUser.setCompany(user.getCompany()!=null?user.getCompany():existingUser.getCompany());
            if(isAdmin){
                existingUser.setRole(user.getRole()!=null?user.getRole():existingUser.getRole());
            }
            if(!isAdmin){
                String updatedPassword = user.getPassword();
                if(updatedPassword==null){
                    updatedPassword = existingUser.getPassword();
                }
                String cryptedPassword = encoder.encode(updatedPassword);
                existingUser.setPassword(cryptedPassword);
            }
            userRepository.deleteById(id);
            userRepository.save(existingUser);
            return Map.of("status", "success", "message", "User updated successfully");
        } catch (Exception e) {
            return Map.of("status", "error", "message", "An error occurred while updating the user");
        }
    }

    @Override
    public Map<String, Object> deleteUser(Long id) {
        try {
            User user = userRepository.findById(id).orElse(null);
            if (user == null) {
                return Map.of("status", "error", "message", "User not found");
            }
            userRepository.delete(user);
            return Map.of("status", "success", "message", "User deleted successfully");
        } catch (Exception e) {
            return Map.of("status", "error", "message", "An error occurred while deleting the user");
        }
    }

    @Override
    public Map<String, Object> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            return Map.of("status", "success", "users", users);
        } catch (Exception e) {
            return Map.of("status", "error", "message", "An error occurred while fetching the users");
        }
    }

}