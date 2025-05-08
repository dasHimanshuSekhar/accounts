package com.accounts.iskcon.patia.account.services;

import com.accounts.iskcon.patia.account.pojo.AddTransactionReq;
import com.accounts.iskcon.patia.account.pojo.AddTransactionRes;
import com.accounts.iskcon.patia.account.pojo.FetchTransactionResponse;
import org.springframework.web.multipart.MultipartFile;

public interface TransactionService {
    AddTransactionRes addTransaction(AddTransactionReq addTransactionReq, MultipartFile multipartFile);

    FetchTransactionResponse fetchTransaction();
}
