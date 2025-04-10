package com.accounts.iskcon.patia.account.controller;

import com.accounts.iskcon.patia.account.pojo.AddTransactionReq;
import com.accounts.iskcon.patia.account.pojo.AddTransactionRes;
import com.accounts.iskcon.patia.account.pojo.FetchTransactionResponse;
import com.accounts.iskcon.patia.account.services.TransactionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("exp")
public class TransactionController {
    final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("add-transactions")
    public AddTransactionRes addExpenditure(@RequestBody AddTransactionReq addTransactionReq){
        return transactionService.addTransaction(addTransactionReq);
    }

    @CrossOrigin(value = "*")
    @GetMapping("fetch-transactions")
    public FetchTransactionResponse fetchTransaction(){
        return transactionService.fetchTransaction();
    }
}
