package com.accounts.iskcon.patia.account.repos;

import com.accounts.iskcon.patia.account.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {
}
