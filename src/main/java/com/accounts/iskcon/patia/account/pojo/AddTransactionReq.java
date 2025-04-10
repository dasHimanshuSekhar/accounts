package com.accounts.iskcon.patia.account.pojo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddTransactionReq {
    @NotNull(message = "mobile number is mandatory !")
    private Long mobileNumber;
    @NotNull(message = "expenses purpose is mandatory !")
    private String purpose;
    @NotNull(message = "Transaction type can't be null")
    private Boolean isCredit;
    private Boolean isOnline;
    @NotNull(message = "category is mandatory to choose !")
    private String category;
    @NotNull(message = "total expenses is mandatory to provide !")
    private Double totalTransactionAmount;
    private Boolean transactionRefurbishmentStatus;
    private String remarks;
}
