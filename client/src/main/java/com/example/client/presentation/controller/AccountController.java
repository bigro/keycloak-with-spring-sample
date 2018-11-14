package com.example.client.presentation.controller;

import com.example.client.application.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/account")
public class AccountController {
    
    AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping("register")
    String register(){
        return "register";
    }
    
    @PostMapping("confirm")
    String confirm(Model model, @RequestParam(name="email") String email) {
        AccountDto accountDto = service.register(email);
        model.addAttribute("account", accountDto);
        
        return "confirm";
    }
}
