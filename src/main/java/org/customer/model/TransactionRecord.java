package org.customer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties
public class TransactionRecord implements Serializable {

  @JsonProperty("PID")
  private String transactionId;

  @JsonProperty("PAMOUNT")
  private double amount;

  @JsonProperty("PDATA")
  private String timestamp;

}
