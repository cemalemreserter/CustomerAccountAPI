package org.customer.controller

import org.customer.dto.CustomerDTO
import org.customer.service.AccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.HttpClientErrorException


@RestController
@RequestMapping("/customers")
class CustomerController {
    @Autowired
    private val accountService: AccountService? = null

    @GetMapping("/{customerId}")
    fun getCustomerDetails(@PathVariable customerId: String): CustomerDTO {
        val customer =
            accountService.redisTemplate.customerRedisTemplate()
                .opsForHash<Any?, Any?>()["CUSTOMERS", customerId] as CustomerDTO
                ?: throw HttpClientErrorException(HttpStatus.NOT_FOUND, "Customer not found")
        return customer
    }
}