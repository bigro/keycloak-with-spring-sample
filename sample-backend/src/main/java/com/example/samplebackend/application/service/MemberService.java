package com.example.samplebackend.application.service;

import com.example.samplebackend.domain.model.member.MemberProfile;
import com.example.samplebackend.domain.model.member.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    MemberRepository repository;

    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    public void registerProfile(MemberProfile profile) {
        repository.registerProfile(profile);
    }
}
