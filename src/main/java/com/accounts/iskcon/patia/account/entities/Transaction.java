package com.accounts.iskcon.patia.account.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String purpose;
    private Boolean isOnline;
    private Boolean isCredit;
    private String category;
    private LocalDateTime date;
    private String attachment; // later to be changed to save img data
    private String remarks;
    private Double totalTransactionAmount;
    private Boolean transactionRefurbishmentStatus;
    @ManyToOne
    private Devotee devotee;
}
