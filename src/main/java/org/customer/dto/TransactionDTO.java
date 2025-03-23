package org.customer.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.Date;


@Data
@Getter
@Builder
public class TransactionDTO {
    private String id;
    private String accountId;
    private String customerId;
    private double amount;
    private Date timestamp;
}
