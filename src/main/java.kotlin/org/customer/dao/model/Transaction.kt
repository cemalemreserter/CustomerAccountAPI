package org.customer.dao.model

import lombok.Data
import org.springframework.data.annotation.Id
import java.util.*


@Data
class Transaction {
    @Id
    private val id = 0
    private val transactionId: String? = null

    private val amount = 0.0

    private val timestamp: Date? = null
}
