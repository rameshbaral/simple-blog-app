package com.ibriztech.springboot.service;

import com.ibriztech.springboot.dto.RegistrationDto;
import com.ibriztech.springboot.entity.User;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);

    User findByEmail(String email);
}
