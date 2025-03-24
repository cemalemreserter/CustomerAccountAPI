package org.customer.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.customer.cache.RedisConfig;
import org.customer.dto.TransactionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.stereotype.Service;
import java.util.*;


@Getter
@Service
public class TransactionService extends CustomerService {

  public TransactionService(RedisConfig redisTemplate) {
    super(redisTemplate);
  }

  @KafkaListener(topics = "transactions", groupId = "transaction_group")
  public void consumeTransaction(String transactionJson) {
    TransactionDTO transaction = null ;
    try { //handle transactions
      ObjectMapper objectMapper = new ObjectMapper();
      transaction = objectMapper.readValue(transactionJson, TransactionDTO.class);

      this.redisTemplate.transactionRedisTemplate().opsForList().rightPush("TRANSACTIONS:" + transaction.getAccountId(), transaction);
    } catch (Exception e) {
      logger.error("Transaction record could not consumed -> {} with error : {}", transaction, e.getMessage());
    }
  }

  public List<TransactionDTO> getTransactionsByAccount(String accountId) {
    return redisTemplate.transactionRedisTemplate().opsForList().range("TRANSACTIONS:" + accountId, 0, -1);
  }
}