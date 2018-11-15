package com.example.sampleaccount.infrastructure.datasource;

import com.example.sampleaccount.domain.model.account.*;
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

    @Override
    public boolean exists(AuthenticationKey authenticationKey) {
        // TODO:ワンタイムにする
        int count = this.jdbcTemplate.queryForObject(
                "select count(*) from account.entry_account where account_id = ? AND confirmation_code = ?",
                Integer.class,
                authenticationKey.identifier().value(), authenticationKey.confirmationCode().value());
        return count > 0;
    }
}
