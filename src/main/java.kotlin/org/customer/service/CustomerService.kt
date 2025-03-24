package org.customer.service

import lombok.Getter
import org.customer.cache.RedisConfig
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Getter
@Service
abstract class CustomerService(val redisTemplate: RedisConfig?) {
    val logger: Logger? = LoggerFactory.getLogger(CustomerService::class.java)
}
