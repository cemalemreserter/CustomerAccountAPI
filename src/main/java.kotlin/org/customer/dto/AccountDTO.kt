package org.customer.dto

import lombok.Builder
import lombok.Data

@Builder
@Data
class AccountDTO {
    private val id: String? = null
    private val customerId: String? = null
    private val balance = 0.0
    private val transactions: MutableList<TransactionDTO?> = ArrayList()
}
