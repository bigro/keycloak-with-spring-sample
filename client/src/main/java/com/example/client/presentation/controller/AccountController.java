package com.example.client.presentation.controller;

import com.example.client.application.service.AccountService;
import com.example.client.domain.model.account.Account;
import com.example.client.domain.model.account.AccountIdentifier;
import com.example.client.domain.model.account.AccountMailAddress;
import com.example.client.domain.model.account.ConfirmationCode;
import com.example.client.domain.model.auth.AuthenticationStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/account")
public class AccountController {

    AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping("register")
    String register() {
        return "register";
    }

    @PostMapping("confirm")
    // TODO: 他がmailAddressなのにここだけemail
    String confirm(RedirectAttributes redirectAttributes, @RequestParam(name = "email") String email) {
        Account account = service.register(email);
        // TODO:redirectAttributes.addAttributeにすると何故かエラーになる
        redirectAttributes.addFlashAttribute("account", account);
        
        return "redirect:/account/confirm";
    }

    @GetMapping("confirm")
        // TODO: 他がmailAddressなのにここだけemail
    String confirm(@ModelAttribute Account account, Model model) {
        model.addAttribute("account", account);
        return "confirm";
    }

    @PostMapping("auth")
    String auth(Model model,
                RedirectAttributes redirectAttributes,
                @RequestParam(name = "confirmationCode") String confirmationCode,
                @RequestParam(name = "accountIdentifier") String accountIdentifier,
                @RequestParam(name = "mailAddress") String mailAddress) {
        AuthenticationStatus status = service.auth(new AccountIdentifier(accountIdentifier), new ConfirmationCode(confirmationCode));

        if (status.isFailed()) {
            // TODO: 認証できなかった旨のエラーメッセージを表示する
            Account account = new Account(new AccountIdentifier(accountIdentifier), new AccountMailAddress(mailAddress));
            redirectAttributes.addFlashAttribute("account", account);
            return "redirect:/account/confirm";
        }

        model.addAttribute("mailAddress", mailAddress);
        return "password";
    }
}
