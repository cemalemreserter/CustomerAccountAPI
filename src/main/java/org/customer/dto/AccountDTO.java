package org.customer.dto;

import lombok.Builder;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class AccountDTO {
    private String id;
    private String customerId;
    private double balance;
    private List<TransactionDTO> transactions = new ArrayList<>();
}
