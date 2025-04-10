package com.accounts.iskcon.patia.account.services;

import com.accounts.iskcon.patia.account.pojo.AddTransactionReq;
import com.accounts.iskcon.patia.account.pojo.AddTransactionRes;
import com.accounts.iskcon.patia.account.pojo.FetchTransactionResponse;

public interface TransactionService {
    AddTransactionRes addTransaction(AddTransactionReq addTransactionReq);

    FetchTransactionResponse fetchTransaction();
}
