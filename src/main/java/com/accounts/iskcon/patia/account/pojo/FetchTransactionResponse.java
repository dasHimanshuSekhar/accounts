package com.accounts.iskcon.patia.account.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FetchTransactionResponse {
    private Integer statusCode;
    private String statusDesc;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<DetailedTransaction> detailedTransactions;
}

