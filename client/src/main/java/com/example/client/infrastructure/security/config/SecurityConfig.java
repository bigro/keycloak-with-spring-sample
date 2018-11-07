package com.example.client.infrastructure.security.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    

    @Override
    public void configure(WebSecurity web) throws Exception {
        // /css/**, /js/**, /images/**, /webjars/**, /**/favicon.ico は􏰁セキュリティ保護対象外
        web.ignoring().requestMatchers(
                PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.oauth2Login()
                .loginPage("/login")
                .permitAll();
        http.authorizeRequests()
                .anyRequest().authenticated();
    }

}
