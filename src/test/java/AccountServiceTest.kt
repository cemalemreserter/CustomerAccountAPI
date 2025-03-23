import com.fasterxml.jackson.databind.ObjectMapper
import org.customer.cache.RedisConfig
import org.customer.dto.CustomerDTO
import org.customer.service.AccountService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.runners.MockitoJUnitRunner
import org.springframework.data.redis.core.HashOperations
import org.springframework.kafka.core.KafkaTemplate

@RunWith(MockitoJUnitRunner::class)
class AccountServiceTest {
    @InjectMocks
    private var redisTemplate: RedisConfig? = null

    @Mock
    private val kafkaTemplate: KafkaTemplate<String?, String?>? = null

    @Mock
    private val hashOperations: HashOperations<String?, Any?, Any?>? = null

    @InjectMocks
    private var accountService: AccountService? = null

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        this.redisTemplate = Mockito.mock(RedisConfig::class.java)
        accountService = AccountService(redisTemplate, objectMapper)
        Mockito.`when`(redisTemplate.customerRedisTemplate().opsForHash<Any?, Any?>()).thenReturn(hashOperations)
    }

    @Test
    fun testCreateAccount() {
        val customer = CustomerDTO.builder()
            .customerId("1")
            .name("Emre")
            .surname("Serter")
            .accounts(ArrayList())
            .build()

        Mockito.`when`(hashOperations.get("CUSTOMERS", "1")).thenReturn(customer)
        val account = accountService.createAccount("1", 100.0)
        Assertions.assertNotNull(account)
        Assertions.assertEquals("1", account.customerId)
        Assertions.assertEquals(100.0, account.balance)
    }

    companion object {
        private val objectMapper = ObjectMapper()
    }
}