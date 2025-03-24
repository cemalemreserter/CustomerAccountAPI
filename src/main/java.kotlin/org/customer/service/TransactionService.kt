package org.customer.service

import com.fasterxml.jackson.databind.ObjectMapper
import lombok.Getter
import org.customer.cache.RedisConfig
import org.customer.dto.TransactionDTO
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service


@Getter
@Service
class TransactionService(redisTemplate: RedisConfig?) : CustomerService(redisTemplate) {
    @KafkaListener(topics = ["transactions"], groupId = "transaction_group")
    fun consumeTransaction(transactionJson: String?) {
        var transaction: TransactionDTO? = null
        try { //handle transactions
            val objectMapper = ObjectMapper()
            transaction = objectMapper.readValue(transactionJson, TransactionDTO::class.java)

            redisTemplate.transactionRedisTemplate().opsForList()
                .rightPush("TRANSACTIONS:" + transaction.getAccountId(), transaction)
        } catch (e: Exception) {
            logger.error("Transaction record could not consumed -> {} with error : {}", transaction, e.message)
        }
    }

    fun getTransactionsByAccount(accountId: String?): MutableList<TransactionDTO?>? {
        return redisTemplate.transactionRedisTemplate().opsForList().range("TRANSACTIONS:$accountId", 0, -1)
    }
}