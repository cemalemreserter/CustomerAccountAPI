package org.customer.controller

import org.customer.dto.TransactionDTO
import org.customer.service.TransactionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/transactions")
class TransactionController {
    @Autowired
    private val transactionService: TransactionService? = null

    @GetMapping("/{accountId}")
    fun getTransactions(@PathVariable accountId: String?): MutableList<TransactionDTO?>? {
        return transactionService.getTransactionsByAccount(accountId)
    }
}
