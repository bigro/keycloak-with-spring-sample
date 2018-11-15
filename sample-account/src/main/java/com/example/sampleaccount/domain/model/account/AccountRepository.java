package com.example.sampleaccount.domain.model.account;

public interface AccountRepository {
    void register(AccountIdentifier identifier, AccountMailAddress mailAddress, ConfirmationCode confirmationCode);

    boolean exists(AuthenticationKey authenticationKey);
}
