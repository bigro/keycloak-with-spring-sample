package com.example.samplebackend.domain.model.member;

import com.example.samplebackend.domain.model.account.AccountIdentifier;

public class MemberProfile {
    AccountIdentifier accountIdentifier;
    NickName nickName;

    public MemberProfile(AccountIdentifier accountIdentifier, NickName nickName) {
        this.accountIdentifier = accountIdentifier;
        this.nickName = nickName;
    }

    public AccountIdentifier accountIdentifier() {
        return accountIdentifier;
    }

    public NickName nickName() {
        return nickName;
    }
}
