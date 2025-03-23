package org.customer.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import org.customer.cache.RedisConfig;
import org.customer.dto.AccountDTO;
import org.customer.dto.CustomerDTO;
import org.customer.dto.TransactionDTO;
import org.jsoup.HttpStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;


import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import static org.customer.constants.AppConstants.THRESHOLD;

@Service
public class AccountService extends  CustomerService{

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    public AccountService(RedisConfig redisTemplate, ObjectMapper objectMapper) {

        super(redisTemplate);
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void initializeCustomers() {
        CustomerDTO customer1 = CustomerDTO.builder().
                customerId("1").name("Emre").
                surname("Serter").
                accounts( new ArrayList<>()).
                build();
        CustomerDTO customer2 = CustomerDTO.builder().
                customerId("2").name("Efe").
                surname("Serter").
                accounts( new ArrayList<>()).
                build();

        redisTemplate.customerRedisTemplate().opsForHash().put("CUSTOMERS", "1", customer1);
        redisTemplate.customerRedisTemplate().opsForHash().put("CUSTOMERS", "2", customer2);
    }

    public AccountDTO createAccount(String customerId, double initialCredit) {
        CustomerDTO customer = (CustomerDTO) redisTemplate.customerRedisTemplate().opsForHash().get("CUSTOMERS", customerId);
        if (customer == null) {
            logger.error("Customer not found -> {}", customerId);
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Customer not found");
        }
        String accountId = UUID.randomUUID().toString();
        AccountDTO account = AccountDTO.builder()
                .id(accountId)
                .customerId(customerId)
                .balance(initialCredit)
                .transactions(new ArrayList<>())
                .build();

        customer.getAccounts().add(account);
        redisTemplate.customerRedisTemplate().opsForHash().put("CUSTOMERS", customerId, customer);
        if (initialCredit > THRESHOLD) {
            TransactionDTO transaction = TransactionDTO.builder().
                    id(UUID.randomUUID().toString()).
                    accountId(accountId).
                    amount(initialCredit).
                    timestamp(new Date()).build();

            account.getTransactions().add(transaction);

            try {
                String transactionJson = objectMapper.writeValueAsString(transaction);
                kafkaTemplate.send("transactions", transactionJson);
            } catch (Exception e) {
                logger.error("Transaction record not stored -> {} with error : {}", transaction.getId(), e.getMessage());
            }
        }
        return account;
    }
}