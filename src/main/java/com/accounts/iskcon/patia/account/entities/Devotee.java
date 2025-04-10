package com.accounts.iskcon.patia.account.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "devotee")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Devotee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private Long mobileNumber;
    private String name;
    private String password; // firstLetterOfName + last-4-digit-of-mob-no
    @OneToMany(mappedBy = "devotee", cascade = CascadeType.ALL)
    private List<Transaction> transactionList;
}
