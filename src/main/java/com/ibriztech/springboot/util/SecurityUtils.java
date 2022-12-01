package com.ibriztech.springboot.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class SecurityUtils {
    public static org.springframework.security.core.userdetails.User getCurrentUser(){
        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principle instanceof org.springframework.security.core.userdetails.User){
            return (User) principle;
        }
        return null;
    }
}
