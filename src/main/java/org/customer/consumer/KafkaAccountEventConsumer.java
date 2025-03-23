package org.customer.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.customer.constants.AppConstants.*;

@Service
public class KafkaAccountEventConsumer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Logger logger = LoggerFactory.getLogger(KafkaAccountEventConsumer.class);
    private final ObjectMapper objectMapper;
    private final Map<String, Double> accountBalances = new ConcurrentHashMap<>();

    @Autowired
    public KafkaAccountEventConsumer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "transaction-events", groupId = "account_group")
    public void processTransaction(String transactionJson) {
        try {
            Map<String, Object> transaction = objectMapper.readValue(transactionJson, Map.class);
            String accountId = (String) transaction.get("accountId");
            Double amount = (Double) transaction.get("amount");

            accountBalances.putIfAbsent(accountId, THRESHOLD_D);
            if (accountBalances.get(accountId) + amount < THRESHOLD) {
                kafkaTemplate.send("account-rollback", transaction.get("id").toString());
                System.out.println("Insufficient balance, rolling back transaction: " + transaction.get("id"));
                return;
            }

            accountBalances.put(accountId, accountBalances.get(accountId) + amount);
            System.out.println("Transaction processed for account: " + accountId + ", new balance: " + accountBalances.get(accountId));
        } catch (Exception e) {
            logger.error("Error occured -> {} with data -> {}", e.getMessage(), transactionJson);
        }
    }
}