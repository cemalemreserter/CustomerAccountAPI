package org.customer.controller;


import org.customer.dto.CustomerDTO;
import org.customer.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;


@RestController
@RequestMapping("/customers")
public class CustomerController {
  @Autowired
  private AccountService accountService;

  @GetMapping("/{customerId}")
  public CustomerDTO getCustomerDetails(@PathVariable String customerId) {
    CustomerDTO customer = (CustomerDTO) accountService.redisTemplate.customerRedisTemplate().opsForHash().get("CUSTOMERS", customerId);
    if (customer == null) {
       throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Customer not found");
    }
    return customer;
  }
}