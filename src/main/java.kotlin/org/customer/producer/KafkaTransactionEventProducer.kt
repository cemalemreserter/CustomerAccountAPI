package org.customer.producer

import com.fasterxml.jackson.databind.ObjectMapper
import org.customer.consumer.KafkaTransactionEventConsumer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap


@Service
internal class KafkaTransactionEventProducer @Autowired constructor(
    private val kafkaTemplate: KafkaTemplate<String?, String?>?,
    private val objectMapper: ObjectMapper?
) {
    private val logger: Logger? = LoggerFactory.getLogger(KafkaTransactionEventConsumer::class.java)
    private val transactionStore: MutableMap<String?, String?> = ConcurrentHashMap()

    fun processTransaction(transactionJson: String?) {
        try {
            val transaction: MutableMap<String?, Any?>? = objectMapper.readValue(
                transactionJson,
                MutableMap::class.java
            )
            val transactionId = transaction.get("id") as String?
            transactionStore[transactionId] = transactionJson
            kafkaTemplate.send("transaction-events", transactionJson)
        } catch (e: Exception) {
            logger.error("Error occured -> {} with data -> {}", e.message, transactionJson)
        }
    }
}
