package com.accounts.iskcon.patia.account.controller;

import com.accounts.iskcon.patia.account.pojo.AddTransactionReq;
import com.accounts.iskcon.patia.account.pojo.AddTransactionRes;
import com.accounts.iskcon.patia.account.pojo.FetchTransactionResponse;
import com.accounts.iskcon.patia.account.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("transaction")
public class TransactionController {
    final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @CrossOrigin(value = "*")
    @PostMapping(value = "add-transactions", consumes = {"multipart/form-data"})
    public ResponseEntity<AddTransactionRes> addExpenditure(
            @RequestPart("data") AddTransactionReq addTransactionReq,
            @RequestPart(value = "attachment", required = false) MultipartFile attachmentFile) {
        AddTransactionRes addTransactionRes = transactionService.addTransaction(addTransactionReq, attachmentFile);
        return ResponseEntity.status(addTransactionRes.getStatusCode().equals(-1) ? HttpStatus.NOT_FOUND : HttpStatus.OK).body(addTransactionRes);
    }

    @CrossOrigin(value = "*")
    @GetMapping("fetch-transactions")
    public ResponseEntity<FetchTransactionResponse> fetchTransaction(){
        FetchTransactionResponse fetchTransactionResponse = transactionService.fetchTransaction();
        return ResponseEntity.status(fetchTransactionResponse.getStatusCode().equals(-1) ? HttpStatus.NOT_FOUND : HttpStatus.OK).body(fetchTransactionResponse);
    }
}
