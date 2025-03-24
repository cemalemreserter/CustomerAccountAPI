package org.customer.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/customers")
class CustomerViewController {
    @GetMapping("/details")
    fun customerDetailsPage(): String {
        return "customers" // Refers to customers.html
    }
}

