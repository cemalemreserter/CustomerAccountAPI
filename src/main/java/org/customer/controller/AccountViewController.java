package org.customer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/accounts")
public class AccountViewController {

    @GetMapping("/open")
    public String openAccountForm() {
        return "accounts"; // Refers to accounts.html in /templates/
    }
}