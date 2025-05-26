package com.almaz.capstone_project.controller;

import com.almaz.capstone_project.model.User;
import com.almaz.capstone_project.security.SecurityUtil;
import com.almaz.capstone_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        User existingUsername = userService.findByUsername(user.getUsername());
        Optional<User> existingEmail = userService.findByEmail(user.getEmail());
        if (existingUsername != null) {
            return "redirect:/register?fail";
        }
        if (existingEmail.isPresent()) {
            return "redirect:/register?fail";
        }

        userService.createUser(user);
        return "redirect:/login?success";
    }

    @GetMapping("/about-me")
    public String getUser(Model model) {
        User user = userService.findByUsername(SecurityUtil.getSessionUser());
        model.addAttribute("user", user);
        return "users-detail";
    }
}
