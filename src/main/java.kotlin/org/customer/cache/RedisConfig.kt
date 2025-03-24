package org.customer.cache

import com.fasterxml.jackson.databind.ObjectMapper
import lombok.RequiredArgsConstructor
import org.customer.constants.AppConstants
import org.customer.dto.AccountDTO
import org.customer.dto.CustomerDTO
import org.customer.dto.TransactionDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.stereotype.Component
import java.time.Duration


@Component
@Configuration
@EnableCaching
@RequiredArgsConstructor
@EnableRedisRepositories
class RedisConfig {
    @Autowired
    var redisCacheConfiguration: RedisCacheConfiguration? = null

    private val objectMapper = ObjectMapper()

    @Bean
    fun getRedisConfiguration(): RedisCacheConfiguration {
        val serializer = Jackson2JsonRedisSerializer(
            CustomerDTO::class.java
        )
        serializer.setObjectMapper(objectMapper)
        return RedisCacheConfiguration.defaultCacheConfig()
            .disableCachingNullValues()
            .serializeKeysWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    StringRedisSerializer()
                )
            )
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    serializer
                )
            )
            .entryTtl(Duration.ofMinutes(AppConstants.CACHE_TTL))
    }


    @Primary
    @Bean("redisCacheManager")
    fun redisCacheManager(lettuceConnectionFactory: LettuceConnectionFactory): RedisCacheManager {
        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(lettuceConnectionFactory)
            .cacheDefaults(
                getRedisConfiguration()
            )
            .build()
    }

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        return LettuceConnectionFactory()
    }

    @Bean
    fun customerRedisTemplate(): RedisTemplate<String?, CustomerDTO?> {
        val template = RedisTemplate<String?, CustomerDTO?>()
        template.connectionFactory = redisConnectionFactory()
        return template
    }

    @Bean
    fun transactionRedisTemplate(): RedisTemplate<String?, TransactionDTO?> {
        val template = RedisTemplate<String?, TransactionDTO?>()
        template.connectionFactory = redisConnectionFactory()
        return template
    }

    fun accountRedisTemplate(): RedisTemplate<String?, AccountDTO?> {
        val template = RedisTemplate<String?, AccountDTO?>()
        template.connectionFactory = redisConnectionFactory()
        return template
    }
}