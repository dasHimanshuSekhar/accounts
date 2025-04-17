package com.accounts.iskcon.patia.account.controller;

import com.accounts.iskcon.patia.account.pojo.AddTransactionReq;
import com.accounts.iskcon.patia.account.pojo.AddTransactionRes;
import com.accounts.iskcon.patia.account.pojo.FetchTransactionResponse;
import com.accounts.iskcon.patia.account.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("transaction")
public class TransactionController {
    final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @CrossOrigin(value = "*")
    @PostMapping("add-transactions")
    public ResponseEntity<AddTransactionRes> addExpenditure(@RequestBody AddTransactionReq addTransactionReq){
        AddTransactionRes addTransactionRes = transactionService.addTransaction(addTransactionReq);
        return ResponseEntity.status(addTransactionRes.getStatusCode().equals(-1) ? HttpStatus.NOT_FOUND : HttpStatus.OK).body(addTransactionRes);
    }

    @CrossOrigin(value = "*")
    @GetMapping("fetch-transactions")
    public ResponseEntity<FetchTransactionResponse> fetchTransaction(){
        FetchTransactionResponse fetchTransactionResponse = transactionService.fetchTransaction();
        return ResponseEntity.status(fetchTransactionResponse.getStatusCode().equals(-1) ? HttpStatus.NOT_FOUND : HttpStatus.OK).body(fetchTransactionResponse);
    }
}
