package com.users.utils;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.users.UserRepository;

@Component
public class RoleCheck {
    
    UserRepository userRepository;

    public RoleCheck(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean checkAccess(Map<String,Object> requestBody, Long _id){
        try{
            if(requestBody.get("role").toString().equalsIgnoreCase("admin")){
                return true;
            }
            else if(requestBody.get("role").toString().equalsIgnoreCase("user") && Long.parseLong(requestBody.get("_id").toString()) == _id){
                Long id = Long.parseLong(requestBody.get("_id").toString());
                if(userRepository.findById(id).isPresent()){
                    return true;
                }
                else{
                    return false;
                }
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }


}
