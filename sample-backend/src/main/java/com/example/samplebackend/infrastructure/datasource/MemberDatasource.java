package com.example.samplebackend.infrastructure.datasource;

import com.example.samplebackend.domain.model.member.MemberProfile;
import com.example.samplebackend.domain.model.member.MemberRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDatasource implements MemberRepository {
    JdbcTemplate jdbcTemplate;

    public MemberDatasource(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public void registerProfile(MemberProfile profile) {
        jdbcTemplate.update(
                "insert into member.profile (account_id, nick_name) values (?, ?)",
                profile.accountIdentifier().value(), profile.nickName().value());
    }
}
