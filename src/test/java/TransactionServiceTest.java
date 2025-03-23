import com.fasterxml.jackson.databind.ObjectMapper;
import org.customer.cache.RedisConfig;
import org.customer.dto.TransactionDTO;
import org.customer.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    @InjectMocks
    private RedisConfig transactionRedisTemplate;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testConsumeTransaction() throws Exception {
        TransactionDTO transaction = TransactionDTO.builder()
                .id("1")
                .accountId("account1")
                .amount(50.0)
                .timestamp(new Date())
                .build();


        ObjectMapper objectMapper = new ObjectMapper();
        String transactionJson = objectMapper.writeValueAsString(transaction);
        transactionService.consumeTransaction(transactionJson);
        verify(transactionRedisTemplate.transactionRedisTemplate().opsForList(), times(1)).rightPush("TRANSACTIONS:account1", transaction);
    }
}
