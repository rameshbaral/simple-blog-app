package com.ibriztech.springboot.service;

import com.ibriztech.springboot.dto.RegistrationDto;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);
}
