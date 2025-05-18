package com.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.users.ext.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    
}