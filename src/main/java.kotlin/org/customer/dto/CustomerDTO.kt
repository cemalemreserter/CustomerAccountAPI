package org.customer.dto

import lombok.Builder
import lombok.Data

@Data
@Builder
class CustomerDTO {
    private val customerId: String? = null
    private val name: String? = null
    private val surname: String? = null
    private val accounts: MutableList<AccountDTO?>? = null
}
