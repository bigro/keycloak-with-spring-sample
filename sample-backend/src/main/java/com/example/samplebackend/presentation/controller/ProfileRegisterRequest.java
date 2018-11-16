package com.example.samplebackend.presentation.controller;

import com.example.samplebackend.domain.model.account.AccountIdentifier;
import com.example.samplebackend.domain.model.member.MemberProfile;
import com.example.samplebackend.domain.model.member.NickName;

public class ProfileRegisterRequest {
    String accountIdentifier;
    String nickName;

    public String getAccountIdentifier() {
        return accountIdentifier;
    }

    public String getNickName() {
        return nickName;
    }

    public MemberProfile toMemberProfile() {
        return new MemberProfile(new AccountIdentifier(this.accountIdentifier), new NickName(this.nickName));
    }
}
