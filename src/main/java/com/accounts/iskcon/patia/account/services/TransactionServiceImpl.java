package com.accounts.iskcon.patia.account.services;

import com.accounts.iskcon.patia.account.dto.FetchTransactionProjection;
import com.accounts.iskcon.patia.account.entities.Devotee;
import com.accounts.iskcon.patia.account.entities.Transaction;
import com.accounts.iskcon.patia.account.pojo.AddTransactionReq;
import com.accounts.iskcon.patia.account.pojo.AddTransactionRes;
import com.accounts.iskcon.patia.account.pojo.DetailedTransaction;
import com.accounts.iskcon.patia.account.pojo.FetchTransactionResponse;
import com.accounts.iskcon.patia.account.repos.DevoteeRepo;
import com.accounts.iskcon.patia.account.repos.TransactionRepo;
import com.accounts.iskcon.patia.account.utils.HelperMethods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    final DevoteeRepo devoteeRepo;
    final TransactionRepo transactionRepo;
    final HelperMethods helperMethods;
    Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    public TransactionServiceImpl(DevoteeRepo devoteeRepo, TransactionRepo transactionRepo, HelperMethods helperMethods) {
        this.devoteeRepo = devoteeRepo;
        this.transactionRepo = transactionRepo;
        this.helperMethods = helperMethods;
    }

    @Override
    public AddTransactionRes addTransaction(AddTransactionReq addTransactionReq, MultipartFile attachmentFile) {
        logger.info("Request received: {} :: API: add-transactions", addTransactionReq);
        Optional<Devotee> devoteeOptional;
        LocalDateTime localDateTime;
        try {
            devoteeOptional = devoteeRepo.findByMobileNumber(addTransactionReq.getMobileNumber());
            if (!devoteeOptional.isPresent()) {
                logger.info("Devotee doesn't exist, who wanted to add expenditure :: API: add-transactions");
                return new AddTransactionRes(-1, "Devotee doesn't exist, who wanted to add expenditure !");
            }

            Transaction transaction = new Transaction();
            transaction.setPurpose(addTransactionReq.getPurpose());
            transaction.setIsOnline(addTransactionReq.getIsOnline());
            transaction.setIsCredit(addTransactionReq.getIsCredit());
            transaction.setCategory(addTransactionReq.getCategory());

            try{
                localDateTime = helperMethods.parseDateTime(addTransactionReq.getTransactionDateTime());
            } catch (Exception e) {
                logger.error("Exception :: Date Parsing Error :: MSG: {} :: API: add-transactions", e.getMessage());
                return new AddTransactionRes(-1, "Failed to parse date, Kindly try again !");
            }
            transaction.setDate(localDateTime);

            if (attachmentFile != null && !attachmentFile.isEmpty()) {
                transaction.setAttachment(attachmentFile.getBytes());
            }

            transaction.setRemarks(addTransactionReq.getRemarks());
            transaction.setTotalTransactionAmount(addTransactionReq.getTotalTransactionAmount());
            transaction.setTransactionRefurbishmentStatus(addTransactionReq.getTransactionRefurbishmentStatus());
            transaction.setDevotee(devoteeOptional.get());

            transactionRepo.save(transaction);
        } catch (Exception e) {
            logger.error("Exception :: DB Error :: MSG: {} :: API: add-transactions", e.getMessage());
            return new AddTransactionRes(-1, "Failed to add your expenditure details, Kindly try again !");
        }
        return new AddTransactionRes(0, "Expenditure details added successfully, Thank you !");
    }


    @Override
    public FetchTransactionResponse fetchTransaction(Long mobileNumber) {
        logger.info("Request received {} :: API: fetch-transactions",
                mobileNumber != null ? "for mobileNumber: " + mobileNumber : "for all transactions");

        List<FetchTransactionProjection> transactionList;
        List<DetailedTransaction> detailedTransactionList = new ArrayList<>();

        try {
            // Fetch either all or by mobile number
            if (mobileNumber != null) {
                transactionList = transactionRepo.findByDevoteeMobileNumber(mobileNumber);
            } else {
                transactionList = transactionRepo.findByAllDevotee();
            }
            if (transactionList.isEmpty()) {
                logger.info("No transactions found :: API: fetch-transactions");
                return new FetchTransactionResponse(-1, "No transaction found!", null);
            }


            for (FetchTransactionProjection fetchTransactionProjection : transactionList) {
                DetailedTransaction detailedTransaction = new DetailedTransaction();
                detailedTransaction.setTransactionId(fetchTransactionProjection.getId());
                detailedTransaction.setMobileNumber(fetchTransactionProjection.getMobileNumber());
                detailedTransaction.setName(fetchTransactionProjection.getName());
                detailedTransaction.setPurpose(fetchTransactionProjection.getPurpose());
                detailedTransaction.setTransactionMode(Boolean.TRUE.equals(fetchTransactionProjection.getIsOnline()) ? "Online" : "Offline");
                detailedTransaction.setTransactionType(Boolean.TRUE.equals(fetchTransactionProjection.getIsCredit()) ? "Credit" : "Debit");
                detailedTransaction.setCategory(fetchTransactionProjection.getCategory());
                detailedTransaction.setTransactionDateTime(fetchTransactionProjection.getDate());

                if (fetchTransactionProjection.getAttachment() != null) {
                    String base64 = Base64.getEncoder().encodeToString(fetchTransactionProjection.getAttachment());
                    detailedTransaction.setBase64Attachment(base64);
                }

                detailedTransaction.setRemarks(fetchTransactionProjection.getRemarks());
                detailedTransaction.setTotalTransactionAmount(fetchTransactionProjection.getTotalTransactionAmount());
                detailedTransaction.setTransactionRefurbishmentStatus(fetchTransactionProjection.getTransactionRefurbishmentStatus());

                detailedTransactionList.add(detailedTransaction);
            }

        } catch (Exception e) {
            logger.error("Exception :: MSG: {} :: API: fetch-transactions", e.getMessage());
            return new FetchTransactionResponse(-1, "Failed to fetch transactions, please try again!", null);
        }

        return new FetchTransactionResponse(0, "Transactions fetched successfully!", detailedTransactionList);
    }
}
