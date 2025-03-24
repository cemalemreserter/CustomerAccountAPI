package org.customer.consumer

import com.fasterxml.jackson.databind.ObjectMapper
import org.customer.service.TransactionService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap


@Service
class KafkaTransactionEventConsumer @Autowired constructor(
    private val kafkaTemplate: KafkaTemplate<String?, String?>?,
    private val objectMapper: ObjectMapper?,
    private val transactionService: TransactionService?
) {
    private val logger: Logger? = LoggerFactory.getLogger(KafkaTransactionEventConsumer::class.java)
    private val transactionStore: MutableMap<String?, String?> = ConcurrentHashMap()


    @KafkaListener(topics = ["account-rollback"], groupId = "transaction_group")
    fun handleRollback(rollbackMessage: String) {
        println("Rolling back transaction: $rollbackMessage")
        transactionStore.remove(rollbackMessage)
        transactionService.redisTemplate.transactionRedisTemplate().opsForHash<Any?, Any?>().delete(rollbackMessage)
        // Implement rollback logic if needed
        logger.info("Transaction Message receieved -> {}", rollbackMessage)
    }
}
