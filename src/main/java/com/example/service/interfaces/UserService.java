package com.example.service.interfaces;

import com.example.dto.UserRegistrationDto;
import com.example.model.User;


import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
   
   User save(UserRegistrationDto registrationDto);
   List<User> getAll();

   
}