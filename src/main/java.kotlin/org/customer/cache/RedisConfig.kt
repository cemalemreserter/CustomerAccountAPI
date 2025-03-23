package org.customer.cache

import com.fasterxml.jackson.databind.ObjectMapper
import org.customer.dto.AccountDTO
import org.customer.dto.CustomerDTO
import org.customer.dto.TransactionDTO
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.stereotype.Component


@Component
@Configuration
@EnableCaching
@EnableRedisRepositories
class RedisConfig {
    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        return LettuceConnectionFactory()
    }

    @Bean
    fun customerRedisTemplate(): RedisTemplate<String, CustomerDTO> {
        val template = RedisTemplate<String, CustomerDTO>()
        template.connectionFactory = redisConnectionFactory()
        return template
    }

    @Bean
    fun transactionRedisTemplate(): RedisTemplate<String, TransactionDTO> {
        val template = RedisTemplate<String, TransactionDTO>()
        template.connectionFactory = redisConnectionFactory()
        return template
    }

    fun accountRedisTemplate(): RedisTemplate<String, AccountDTO> {
        val template = RedisTemplate<String, AccountDTO>()
        template.connectionFactory = redisConnectionFactory()
        return template
    }

    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper()
    }
}