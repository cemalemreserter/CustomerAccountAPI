package org.customer.controller

import org.customer.dto.AccountDTO
import org.customer.service.AccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/accounts")
internal class AccountController {
    @Autowired
    private val accountService: AccountService? = null

    @PostMapping("/open")
    fun createAccount(@RequestParam customerId: String?, @RequestParam initialCredit: Double): AccountDTO? {
        return accountService.createAccount(customerId, initialCredit)
    }
}
