package org.customer.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import lombok.Data
import java.io.Serializable

@Data
@JsonIgnoreProperties
class TransactionRecord : Serializable {
    @JsonProperty("PID")
    private val transactionId: String? = null

    @JsonProperty("PAMOUNT")
    private val amount = 0.0

    @JsonProperty("PDATA")
    private val timestamp: String? = null
}
