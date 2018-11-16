package com.example.client.presentation.controller;

import com.example.client.application.service.MemberService;
import com.example.client.domain.model.account.AccountIdentifier;
import com.example.client.domain.model.member.NickName;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/member")
public class MemberController {
    
    MemberService service;

    public MemberController(MemberService service) {
        this.service = service;
    }

    @GetMapping("register-profile")
    String registerProfile() {
        return "profile";
    }

    @PostMapping("register-profile")
    String registerProfile(@RequestParam(name = "accountIdentifier") String accountIdentifier,
                           @RequestParam(name = "nickName") String nickName) {
        service.registerProfile(new AccountIdentifier(accountIdentifier), new NickName(nickName));
        return "result";
    }
}
