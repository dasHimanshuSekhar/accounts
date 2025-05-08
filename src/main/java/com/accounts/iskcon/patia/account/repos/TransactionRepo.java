package com.accounts.iskcon.patia.account.repos;

import com.accounts.iskcon.patia.account.dto.FetchTransactionProjection;
import com.accounts.iskcon.patia.account.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {

    @Query(
            value = "SELECT d.mobile_number AS mobileNumber, " +
                    "d.name AS name," +
                    "t.attachment AS attachment, " +
                    "t.id AS id," +
                    "t.category AS category, " +
                    "t.date AS date, " +
                    "t.is_credit AS isCredit, " +
                    "t.is_online AS isOnline, " +
                    "t.purpose AS purpose, " +
                    "t.remarks AS remarks, " +
                    "t.total_transaction_amount AS totalTransactionAmount, " +
                    "t.transaction_refurbishment_status AS transactionRefurbishmentStatus " +
                    "FROM devotee AS d INNER JOIN transaction " +
                    "AS t ON d.id = t.devotee_id " +
                    "WHERE d.mobile_number = :mobileNumber",
            nativeQuery = true
    )
    List<FetchTransactionProjection> findByDevoteeMobileNumber(@Param("mobileNumber") Long mobileNumber);

    @Query(
            value = "SELECT d.mobile_number AS mobileNumber, " +
                    "d.name AS name," +
                    "t.attachment AS attachment, " +
                    "t.id AS id," +
                    "t.category AS category, " +
                    "t.date AS date, " +
                    "t.is_credit AS isCredit, " +
                    "t.is_online AS isOnline, " +
                    "t.purpose AS purpose, " +
                    "t.remarks AS remarks, " +
                    "t.total_transaction_amount AS totalTransactionAmount, " +
                    "t.transaction_refurbishment_status AS transactionRefurbishmentStatus " +
                    "FROM devotee AS d INNER JOIN transaction " +
                    "AS t ON d.id = t.devotee_id ",
            nativeQuery = true
    )
    List<FetchTransactionProjection> findByAllDevotee();



}
