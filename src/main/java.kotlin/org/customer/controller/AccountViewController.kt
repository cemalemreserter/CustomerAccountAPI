package org.customer.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/accounts")
class AccountViewController {
    @GetMapping("/open")
    fun openAccountForm(): String {
        return "accounts" // Refers to accounts.html in /templates/
    }
}
