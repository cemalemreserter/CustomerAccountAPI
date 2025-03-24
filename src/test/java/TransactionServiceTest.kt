import com.fasterxml.jackson.databind.ObjectMapper
import org.customer.cache.RedisConfig
import org.customer.dto.TransactionDTO
import org.customer.service.TransactionService
import org.junit.Ignore
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
@Ignore
class TransactionServiceTest {
    @Mock
    private var transactionRedisTemplate: RedisConfig? = null

    @InjectMocks
    private val transactionService: TransactionService? = null

    @BeforeEach
    @Ignore
    fun setUp() {
        //MockitoAnnotations.initMocks(this);
        this.transactionRedisTemplate = RedisConfig()
    }

    @Ignore
    @Test
    @Throws(Exception::class)
    fun testConsumeTransaction() {
        val transaction: TransactionDTO = TransactionDTO.builder()
            .id("1")
            .accountId("account1")
            .amount(50.0)
            .timestamp(Date())
            .build()


        val objectMapper = ObjectMapper()
        val transactionJson = objectMapper.writeValueAsString(transaction)
        transactionService.consumeTransaction(transactionJson)
        transactionRedisTemplate.transactionRedisTemplate().opsForList().rightPush("TRANSACTIONS:account1", transaction)
    }
}
