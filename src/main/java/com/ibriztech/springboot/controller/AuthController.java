package com.ibriztech.springboot.controller;

import com.ibriztech.springboot.dto.RegistrationDto;
import com.ibriztech.springboot.entity.User;
import com.ibriztech.springboot.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // handler method to handle login page request
    @GetMapping("/login")
    public String loginPage(){
        return "login";
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
    public String register(@Valid @ModelAttribute("user") RegistrationDto user,
                        BindingResult result,
                           Model model){
        //checks if the user with the provided email exists or not
        User existingUser = userService.findByEmail(user.getEmail());
        if(existingUser != null && existingUser.getEmail() !=null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email",null, "there is already user registered with same email id");
        }
        if(result.hasErrors()){
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/register?success";
    }
}
