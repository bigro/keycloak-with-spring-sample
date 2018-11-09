package com.example.client.presentation.controller;

import com.example.client.application.service.PointService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContentsController {
    
    private PointService pointService;

    public ContentsController(PointService pointService) {
        this.pointService = pointService;
    }

    @GetMapping("contents")
    public String contents(Model model, Authentication authentication) {
        int point = pointService.get();
        model.addAttribute("point", point);
        return "contents";
    }
}
