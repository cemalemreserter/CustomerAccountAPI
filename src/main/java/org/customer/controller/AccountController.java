package org.customer.controller;



import org.customer.dto.AccountDTO;
import org.customer.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/accounts")
class AccountController {
  @Autowired
  private AccountService accountService;

  @PostMapping("/open")
  public AccountDTO createAccount(@RequestParam String customerId, @RequestParam double initialCredit) {
    return accountService.createAccount(customerId, initialCredit);
  }
}
