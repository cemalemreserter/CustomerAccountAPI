package org.customer.service

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.annotation.PostConstruct
import org.customer.cache.RedisConfig
import org.customer.dto.AccountDTO
import org.customer.dto.CustomerDTO
import org.customer.dto.TransactionDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import java.util.*

@Service
class AccountService(redisTemplate: RedisConfig?, private val objectMapper: ObjectMapper?) :
    CustomerService(redisTemplate) {
    @Autowired
    private val kafkaTemplate: KafkaTemplate<String?, String?>? = null

    @PostConstruct
    fun initializeCustomers() {
        val customer1: CustomerDTO? = CustomerDTO.builder().customerId
        ("1").name("Emre").surname
        ("Serter").accounts
        (ArrayList<AccountDTO?>()).build()
        val customer2: CustomerDTO? = CustomerDTO.builder().customerId
        ("2").name("Efe").surname
        ("Serter").accounts
        (ArrayList<AccountDTO?>()).build()

        redisTemplate.customerRedisTemplate().opsForHash<Any?, Any?>().put("CUSTOMERS", "1", customer1)
        redisTemplate.customerRedisTemplate().opsForHash<Any?, Any?>().put("CUSTOMERS", "2", customer2)
    }

    fun createAccount(customerId: String, initialCredit: Double): AccountDTO? {
        val customer = redisTemplate.customerRedisTemplate()
            .opsForHash<Any?, Any?>()["CUSTOMERS", customerId] as CustomerDTO
        if (customer == null) {
            logger.error("Customer not found -> {}", customerId)
            throw HttpClientErrorException(HttpStatus.NOT_FOUND, "Customer not found")
        }
        val accountId = UUID.randomUUID().toString()
        val account = AccountDTO.builder()
            .id(accountId)
            .customerId(customerId)
            .balance(initialCredit)
            .transactions(ArrayList())
            .build()

        customer.accounts.add(account)
        redisTemplate.customerRedisTemplate().opsForHash<Any?, Any?>().put("CUSTOMERS", customerId, customer)
        if (initialCredit > THRESHOLD) {
            val transaction: TransactionDTO? = TransactionDTO.builder().id
            (UUID.randomUUID().toString()).accountId
            (accountId).amount
            (initialCredit).timestamp
            (Date()).build()

            account.transactions.add(transaction)

            try {
                val transactionJson = objectMapper.writeValueAsString(transaction)
                kafkaTemplate.send("transactions", transactionJson)
            } catch (e: Exception) {
                logger.error("Transaction record not stored -> {} with error : {}", transaction.getId(), e.message)
            }
        }
        return account
    }
}