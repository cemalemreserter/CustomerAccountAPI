package org.customer.cache;



import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.customer.dto.AccountDTO;
import org.customer.dto.CustomerDTO;
import org.customer.dto.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.time.Duration;

import static org.customer.constants.AppConstants.CACHE_TTL;

@Component
@Configuration
@EnableCaching
@RequiredArgsConstructor
@EnableRedisRepositories
public class RedisConfig {

    @Autowired
    RedisCacheConfiguration redisCacheConfiguration;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    public RedisCacheConfiguration getRedisConfiguration() {
        Jackson2JsonRedisSerializer<CustomerDTO> serializer = new Jackson2JsonRedisSerializer<>(CustomerDTO.class);
        serializer.setObjectMapper(objectMapper);
        return RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(
                        new StringRedisSerializer()))
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                                        serializer
                                ))
                .entryTtl(Duration.ofMinutes(CACHE_TTL));
    }


    @Primary
    @Bean("redisCacheManager")
    public RedisCacheManager redisCacheManager(LettuceConnectionFactory lettuceConnectionFactory) {

        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(lettuceConnectionFactory)
                .cacheDefaults(
                        getRedisConfiguration())
                .build();
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, CustomerDTO> customerRedisTemplate() {
        RedisTemplate<String, CustomerDTO> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }

    @Bean
    public RedisTemplate<String, TransactionDTO> transactionRedisTemplate() {
        RedisTemplate<String, TransactionDTO> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }

    public RedisTemplate<String, AccountDTO> accountRedisTemplate() {
        RedisTemplate<String, AccountDTO> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }

}