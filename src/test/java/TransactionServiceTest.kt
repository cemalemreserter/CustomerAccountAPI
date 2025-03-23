import com.fasterxml.jackson.databind.ObjectMapper
import org.customer.cache.RedisConfig
import org.customer.dto.TransactionDTO
import org.customer.service.TransactionService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.runners.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class TransactionServiceTest {
    @InjectMocks
    private val transactionRedisTemplate: RedisConfig? = null

    @InjectMocks
    private val transactionService: TransactionService? = null

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    @Throws(Exception::class)
    fun testConsumeTransaction() {
        val transaction = TransactionDTO.builder()
            .id("1")
            .accountId("account1")
            .amount(50.0)
            .timestamp(Date())
            .build()


        val objectMapper = ObjectMapper()
        val transactionJson = objectMapper.writeValueAsString(transaction)
        transactionService.consumeTransaction(transactionJson)
        Mockito.verify(transactionRedisTemplate.transactionRedisTemplate().opsForList(), Mockito.times(1))
            .rightPush("TRANSACTIONS:account1", transaction)
    }
}
