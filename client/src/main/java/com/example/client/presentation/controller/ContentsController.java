package com.example.client.presentation.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContentsController {
    
    @GetMapping("contents")
    public String contents(Model model, Authentication authentication) {
        return "contents";
    }
}
