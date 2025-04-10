package com.accounts.iskcon.patia.account.services;

import com.accounts.iskcon.patia.account.entities.Devotee;
import com.accounts.iskcon.patia.account.entities.Transaction;
import com.accounts.iskcon.patia.account.pojo.AddTransactionReq;
import com.accounts.iskcon.patia.account.pojo.AddTransactionRes;
import com.accounts.iskcon.patia.account.pojo.DetailedTransaction;
import com.accounts.iskcon.patia.account.pojo.FetchTransactionResponse;
import com.accounts.iskcon.patia.account.repos.DevoteeRepo;
import com.accounts.iskcon.patia.account.repos.TransactionRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    final DevoteeRepo devoteeRepo;
    final TransactionRepo transactionRepo;
    Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    public TransactionServiceImpl(DevoteeRepo devoteeRepo, TransactionRepo transactionRepo) {
        this.devoteeRepo = devoteeRepo;
        this.transactionRepo = transactionRepo;
    }

    @Override
    public AddTransactionRes addTransaction(AddTransactionReq addTransactionReq) {
        Optional<Devotee> devoteeOptional;
        try
        {
            //existence of devotee && fetch devotee entity
            devoteeOptional = devoteeRepo.findByMobileNumber(addTransactionReq.getMobileNumber());
            if(!devoteeOptional.isPresent()){
                logger.info("Devotee doesn't exist, who wanted to add expenditure");
                return new AddTransactionRes(-1, "Devotee doesn't exist, who wanted to add expenditure !");
            }

            //save the expenditure details linked to devotee.
            Transaction transaction = new Transaction();
            transaction.setPurpose(addTransactionReq.getPurpose());
            transaction.setIsOnline(addTransactionReq.getIsOnline());
            transaction.setIsCredit(addTransactionReq.getIsCredit());
            transaction.setCategory(addTransactionReq.getCategory());
            transaction.setDate(LocalDateTime.now());
            transaction.setAttachment("must be a img");
            transaction.setRemarks(addTransactionReq.getRemarks());
            transaction.setTotalTransactionAmount(addTransactionReq.getTotalTransactionAmount());
            transaction.setTransactionRefurbishmentStatus(addTransactionReq.getTransactionRefurbishmentStatus());
            transaction.setDevotee(devoteeOptional.get());
            transactionRepo.save(transaction);
        } catch (Exception e) {
            logger.error("Exception :: MSG: {}", e.getMessage());
            return new AddTransactionRes(-1, "Failed to add your expenditure details, Kindly try again !");
        }
        return new AddTransactionRes(0, "Expenditure details added successfully, Thank you !");
    }

    @Override
    public FetchTransactionResponse fetchTransaction() {
        List<Transaction> transactionList;
        List<DetailedTransaction> detailedTransactionList = new ArrayList<>();
        try{
            transactionList = transactionRepo.findAll();
            if(transactionList.isEmpty()){
                logger.info("No transaction found !");
                return new FetchTransactionResponse(-1, "No transaction found, need to have transactions first !", null);
            }

            for(Transaction transaction : transactionList){
                DetailedTransaction detailedTransaction = new DetailedTransaction();
                detailedTransaction.setTransactionId(transaction.getId());
                detailedTransaction.setMobileNumber(transaction.getDevotee().getMobileNumber());
                detailedTransaction.setName(transaction.getDevotee().getName());
                detailedTransaction.setPurpose(transaction.getPurpose());
                detailedTransaction.setTransactionMode(Boolean.TRUE.equals(transaction.getIsOnline()) ? "Online" : "Offline");
                detailedTransaction.setTransactionType(Boolean.TRUE.equals(transaction.getIsCredit()) ? "Credit" : "Debit");
                detailedTransaction.setCategory(transaction.getCategory());
                detailedTransaction.setTransactionDateTime(transaction.getDate());
                detailedTransaction.setAttachment(transaction.getAttachment());
                detailedTransaction.setRemarks(transaction.getRemarks());
                detailedTransaction.setTotalTransactionAmount(transaction.getTotalTransactionAmount());
                detailedTransaction.setTransactionRefurbishmentStatus(transaction.getTransactionRefurbishmentStatus());

                detailedTransactionList.add(detailedTransaction);
            }

        } catch (Exception e) {
            logger.error("Exception :: MSG: {}", e.getMessage());
            return new FetchTransactionResponse(-1, "Failed to fetch the required transaction details, Kindly try again !", null);
        }
        return new FetchTransactionResponse(0, "Transactions fetched successfully !", detailedTransactionList);
    }
}
