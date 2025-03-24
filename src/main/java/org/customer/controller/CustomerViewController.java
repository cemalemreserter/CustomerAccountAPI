package org.customer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customers")
public class CustomerViewController {

    @GetMapping("/details")
    public String customerDetailsPage() {
        return "customers"; // Refers to customers.html
    }
}
