package com.example.resource.presentation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleResourceController {
    
    @GetMapping("point")
    int point() {
        //サンプルのため固定値
        return 10;
    }
}
