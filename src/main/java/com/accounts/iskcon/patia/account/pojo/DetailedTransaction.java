package com.accounts.iskcon.patia.account.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DetailedTransaction{
    private Long transactionId;
    private Long mobileNumber;
    private String name;
    private String purpose;
    private String transactionMode;
    private String transactionType;
    private String category;
    private LocalDateTime transactionDateTime;
    private String attachment; // later to be changed to save img data
    private String remarks;
    private Double totalTransactionAmount;
    private Boolean transactionRefurbishmentStatus;
}
