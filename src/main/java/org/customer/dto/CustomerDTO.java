package org.customer.dto;

import lombok.Data;
import lombok.Builder;

import java.util.List;

@Data
@Builder
public class CustomerDTO {
    private String customerId;
    private String name;
    private String surname;
    private List<AccountDTO> accounts;
}
