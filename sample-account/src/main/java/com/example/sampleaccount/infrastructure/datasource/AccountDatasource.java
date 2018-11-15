package com.example.sampleaccount.infrastructure.datasource;

import com.example.sampleaccount.domain.model.account.AccountIdentifier;
import com.example.sampleaccount.domain.model.account.AccountMailAddress;
import com.example.sampleaccount.domain.model.account.AccountRepository;
import com.example.sampleaccount.domain.model.account.ConfirmationCode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDatasource implements AccountRepository {
    JdbcTemplate jdbcTemplate;

    public AccountDatasource(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void register(AccountIdentifier identifier, AccountMailAddress mailAddress, ConfirmationCode confirmationCode) {
        jdbcTemplate.update(
                "insert into account.entry_account (account_id, email, confirmation_code) values (?, ?, ?)",
                identifier.value(), mailAddress.value(), confirmationCode.value());
    }
}
