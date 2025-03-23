package org.customer.consumer;



import com.fasterxml.jackson.databind.ObjectMapper;
import org.customer.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class KafkaTransactionEventConsumer {


    private final TransactionService transactionService ;
    private final Logger logger = LoggerFactory.getLogger(KafkaTransactionEventConsumer.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final Map<String, String> transactionStore = new ConcurrentHashMap<>();

    @Autowired
    public KafkaTransactionEventConsumer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper, TransactionService transactionService) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.transactionService = transactionService;
    }




    @KafkaListener(topics = "account-rollback", groupId = "transaction_group")
    public void handleRollback(String rollbackMessage) {
        System.out.println("Rolling back transaction: " + rollbackMessage);
        transactionStore.remove(rollbackMessage);
        transactionService.getRedisConfig().transactionRedisTemplate().opsForHash().delete(rollbackMessage);
        // Implement rollback logic if needed
        logger.info("Transaction Message receieved -> {}", rollbackMessage);
    }
}
