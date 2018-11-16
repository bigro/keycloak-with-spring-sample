package com.example.client.presentation.controller;

import com.example.client.application.service.AccountService;
import com.example.client.domain.model.account.*;
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
        EntryAccount entryAccount = service.entry(email);
        // TODO:redirectAttributes.addAttributeにすると何故かエラーになる
        redirectAttributes.addFlashAttribute("entryAccount", entryAccount);

        return "redirect:/account/confirm";
    }

    @GetMapping("confirm")
        // TODO: 他がmailAddressなのにここだけemail
    String confirm(@ModelAttribute EntryAccount entryAccount, Model model) {
        model.addAttribute("entryAccount", entryAccount);
        return "confirm";
    }

    @PostMapping("auth")
    String auth(RedirectAttributes redirectAttributes,
                @RequestParam(name = "confirmationCode") String confirmationCode,
                @RequestParam(name = "accountIdentifier") String accountIdentifier,
                @RequestParam(name = "mailAddress") String mailAddress) {
        AuthenticationStatus status = service.auth(new AccountIdentifier(accountIdentifier), new ConfirmationCode(confirmationCode));
        EntryAccount entryAccount = new EntryAccount(new AccountIdentifier(accountIdentifier), new AccountMailAddress(mailAddress));

        if (status.isFailed()) {
            // TODO: 認証できなかった旨のエラーメッセージを表示する
            redirectAttributes.addFlashAttribute("entryAccount", entryAccount);
            return "redirect:/account/confirm";
        }

        redirectAttributes.addFlashAttribute("entryAccount", entryAccount);
        return "redirect:/account/password";
    }

    @GetMapping("password")
    String password() {
        return "password";
    }

    @PostMapping("reset-password")
    String resetPassword(@RequestParam(name = "identifier") String identifier,
                         @RequestParam(name = "mailAddress") String mailAddress,
                         @RequestParam(name = "password") String password,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        
        AccountIdentifier accountIdentifier = new AccountIdentifier(identifier);
        AccountMailAddress accountMailAddress = new AccountMailAddress(mailAddress);
        ResetPasswordStatus status = service.resetPassword(
                accountIdentifier,
                accountMailAddress, 
                new AccountPassword(password)
        );

        if (status.isFailed()) {
            redirectAttributes.addFlashAttribute("entryAccount", new EntryAccount(accountIdentifier, accountMailAddress));
            return "redirect:/account/password";
        }

        redirectAttributes.addFlashAttribute("identifier", identifier);
        return "redirect:/member/register-profile";
    }
}
