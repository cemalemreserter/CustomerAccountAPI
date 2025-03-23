import com.fasterxml.jackson.databind.ObjectMapper;
import org.customer.cache.RedisConfig;
import org.customer.dto.AccountDTO;
import org.customer.dto.CustomerDTO;
import org.customer.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @InjectMocks
    private RedisConfig redisTemplate ;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private HashOperations<String, Object, Object> hashOperations;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.redisTemplate = Mockito.mock(RedisConfig.class);
        accountService = new AccountService(redisTemplate, objectMapper);
        when(redisTemplate.customerRedisTemplate().opsForHash()).thenReturn(hashOperations);
    }

    @Test
    void testCreateAccount() {
        CustomerDTO customer = CustomerDTO.builder()
                .customerId("1")
                .name("Emre")
                .surname("Serter")
                .accounts(new ArrayList<>())
                .build();

        when(hashOperations.get("CUSTOMERS", "1")).thenReturn(customer);
        AccountDTO account = accountService.createAccount("1", 100.0);
        assertNotNull(account);
        assertEquals("1", account.getCustomerId());
        assertEquals(100.0, account.getBalance());
    }
}