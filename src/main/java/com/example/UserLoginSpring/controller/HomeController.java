package com.example.UserLoginSpring.controller;

import com.example.UserLoginSpring.service.UserService;
import com.example.UserLoginSpring.usersession.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private UserSession userSession;

    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String home(Model model) {
        if (userService.isAuthenticated()) {
            String username = userSession.getUser().getUsername();
            model.addAttribute("username", username);

            return "home";
        } else {
            return "redirect:/login";
        }
    }
}