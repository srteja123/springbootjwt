package com.example.demo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin
public class Dashboard {

    @GetMapping("/view")
    public String show_payments(HttpServletRequest request){
       // Long user_id = Auth.fetch_user_id_from_auth_header(request.getHeader("Authorization"));
        return "Here are payments of user with ID - ";
    }


}
