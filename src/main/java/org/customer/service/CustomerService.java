package org.customer.service;

import lombok.Getter;
import org.customer.cache.RedisConfig;
import org.customer.consumer.KafkaTransactionEventConsumer;
import org.customer.dto.TransactionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import java.util.*;


@Getter
@Service
public abstract class CustomerService {
    public final RedisConfig redisTemplate;
    public final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    public CustomerService(RedisConfig redisTemplate) {
        this.redisTemplate = redisTemplate;
    }



}
