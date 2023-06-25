package com.example.UserLoginSpring.controller;

import com.example.UserLoginSpring.model.User;
import com.example.UserLoginSpring.repository.UserRepository;
import com.example.UserLoginSpring.service.UserService;
import com.example.UserLoginSpring.usersession.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    final private UserService userService;
    final private UserSession userSession;
    private final UserRepository userRepository;

    @Autowired
    public LoginController(UserService userService,
                           UserSession userSession,
                           UserRepository userRepository
    ) {
        this.userService = userService;
        this.userSession = userSession;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String login() {
        return "login";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        username = username.toUpperCase();

        if (userService.authenticate(username, password)) {
            userSession.setLoggedIn(true);

            userSession.getUser().setUsername(username);
            userSession.getUser().setPassword(password);

            model.addAttribute("username", username);

            return "redirect:/home";
        } else {
            model.addAttribute("error", "Credenciais inválidas");
            return "login";

            // Erro lançado via paramentro
            // return "redirect:/login?error";
        }
    }

        //    @PostMapping("/login")
        //    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        //        if (userService.authenticate(username, password)) {
        //
        //            return "redirect:/home";
        //        } else {
        //            model.addAttribute("error", "Invalid username or password");
        //            return "login";
        //        }
        //    }
        //    @GetMapping("/home")
        //    public String home() {
        //        if (userService.isAuthenticated()) {
        //            return "home";
        //        } else {
        //            return "redirect:/login";
        //        }
        //    }

    @GetMapping("/logout")
    public String logout() {
        userSession.setLoggedIn(false);
        userSession.setUser(null);
        return "redirect:/login";
    }
}
