package com.ktateishi.webnazo.controller;

import com.ktateishi.webnazo.model.User;
import com.ktateishi.webnazo.service.QuizService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class AdminController {

    private final QuizService service;

    @GetMapping(path = "/admin")
    public String adminPage(Model model) {
        List<User> users = service.getUserList();
        model.addAttribute("users", users);
        return "admin";
    }
}
