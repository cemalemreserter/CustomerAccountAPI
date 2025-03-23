package org.customer.cache;



import com.fasterxml.jackson.databind.ObjectMapper;
import org.customer.dto.AccountDTO;
import org.customer.dto.CustomerDTO;
import org.customer.dto.TransactionDTO;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.stereotype.Component;

@Component
@Configuration
@EnableCaching
@EnableRedisRepositories
public class RedisConfig {
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

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}