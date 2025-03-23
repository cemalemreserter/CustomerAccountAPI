package org.customer.controller;

import org.customer.dto.TransactionDTO;
import org.customer.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/{accountId}")
    public List<TransactionDTO> getTransactions(@PathVariable String accountId) {
        return transactionService.getTransactionsByAccount(accountId);
    }
}
