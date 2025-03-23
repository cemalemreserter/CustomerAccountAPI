package org.customer.producer;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.customer.consumer.KafkaTransactionEventConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
class KafkaTransactionEventProducer {
  private final Logger logger = LoggerFactory.getLogger(KafkaTransactionEventConsumer.class);
  private final KafkaTemplate<String, String> kafkaTemplate;
  private final ObjectMapper objectMapper;
  private final Map<String, String> transactionStore = new ConcurrentHashMap<>();

  @Autowired
  public KafkaTransactionEventProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
    this.kafkaTemplate = kafkaTemplate;
    this.objectMapper = objectMapper;
  }

  public void processTransaction(String transactionJson) {
    try {
      Map<String, Object> transaction = objectMapper.readValue(transactionJson, Map.class);
      String transactionId = (String) transaction.get("id");
      transactionStore.put(transactionId, transactionJson);
      kafkaTemplate.send("transaction-events", transactionJson);
    } catch (Exception e) {
      logger.error("Error occured -> {} with data -> {}", e.getMessage(), transactionJson);

    }
  }

}
