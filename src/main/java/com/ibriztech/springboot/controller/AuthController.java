package com.ibriztech.springboot.controller;

import com.ibriztech.springboot.dto.RegistrationDto;
import com.ibriztech.springboot.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    //handler method to handle user registration request
    @GetMapping("/register")
    public String showRegistrationForm(Model model){

        RegistrationDto user = new RegistrationDto();
        model.addAttribute("user", user);
        return "register";
    }
    //handler method to handle user registeration form submit request
    @PostMapping("/register/save")
    public String register(@ModelAttribute("user") RegistrationDto user){
        userService.saveUser(user);
        return "redirect:/register?success";
    }
}
