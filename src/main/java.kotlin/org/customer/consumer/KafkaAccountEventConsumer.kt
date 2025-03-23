package org.customer.consumer

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class KafkaAccountEventConsumer @Autowired constructor(
    private val kafkaTemplate: KafkaTemplate<String?, String?>?,
    private val objectMapper: ObjectMapper?
) {
    private val logger: Logger? = LoggerFactory.getLogger(KafkaAccountEventConsumer::class.java)
    private val accountBalances: MutableMap<String?, Double?> = ConcurrentHashMap()

    @KafkaListener(topics = ["transaction-events"], groupId = "account_group")
    fun processTransaction(transactionJson: String?) {
        try {
            val transaction: MutableMap<String?, Any?>? = objectMapper.readValue(
                transactionJson,
                MutableMap::class.java
            )
            val accountId = transaction.get("accountId") as String?
            val amount = transaction.get("amount") as Double?

            accountBalances.putIfAbsent(accountId, THRESHOLD_D)
            if (accountBalances[accountId] + amount < THRESHOLD) {
                kafkaTemplate.send("account-rollback", transaction.get("id").toString())
                println("Insufficient balance, rolling back transaction: " + transaction.get("id"))
                return
            }

            accountBalances[accountId] = accountBalances[accountId] + amount
            println("Transaction processed for account: " + accountId + ", new balance: " + accountBalances[accountId])
        } catch (e: Exception) {
            logger.error("Error occured -> {} with data -> {}", e.message, transactionJson)
        }
    }
}