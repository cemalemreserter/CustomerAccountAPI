package org.customer.dto

import lombok.Builder
import lombok.Data
import lombok.Getter
import java.util.*

@Data
@Getter
@Builder
class TransactionDTO {
    private val id: String? = null
    private val accountId: String? = null
    private val customerId: String? = null
    private val amount = 0.0
    private val timestamp: Date? = null
}
