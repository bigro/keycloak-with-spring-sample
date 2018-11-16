package com.example.samplebackend.presentation.controller;

import com.example.samplebackend.application.service.MemberService;
import com.example.samplebackend.domain.model.member.MemberProfile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {
    
    MemberService service;

    public MemberController(MemberService service) {
        this.service = service;
    }

    @PostMapping("profile/register")
    ProfileRegisterResponse registerProfile(@RequestBody ProfileRegisterRequest request) {
        MemberProfile profile = request.toMemberProfile();
        service.registerProfile(profile);
        return ProfileRegisterResponse.success();
    }
}
