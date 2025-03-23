package org.customer.dao.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import java.util.Date;

@Data
public class Transaction {

    @Id
    private int id;
    private String transactionId;

    private double amount;

    private Date timestamp;


}
