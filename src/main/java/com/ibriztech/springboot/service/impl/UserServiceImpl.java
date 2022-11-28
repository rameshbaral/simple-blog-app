package com.ibriztech.springboot.service.impl;

import com.ibriztech.springboot.dto.RegistrationDto;
import com.ibriztech.springboot.entity.Role;
import com.ibriztech.springboot.entity.User;
import com.ibriztech.springboot.repository.RoleRepository;
import com.ibriztech.springboot.repository.UserRepository;
import com.ibriztech.springboot.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void saveUser(RegistrationDto registrationDto) {
        User user = new User();
        user.setName(registrationDto.getFirstName() + " " + registrationDto.getLastName());
        user.setEmail(registrationDto.getEmail());
        // use spring security to encrypt the password
        user.setPassword(registrationDto.getPassword());
        Role role = roleRepository.findByName("ROLE_GUEST");
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }
}
