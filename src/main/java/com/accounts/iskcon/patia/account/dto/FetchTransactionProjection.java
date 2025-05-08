package com.accounts.iskcon.patia.account.dto;

import java.time.LocalDateTime;

public interface FetchTransactionProjection {
    Long getMobileNumber();
    Long getId();
    String getName();
    byte[] getAttachment();
    String getCategory();
    LocalDateTime getDate();
    Boolean getIsOnline();
    Boolean getIsCredit();
    String getPurpose();
    String getRemarks();
    Double getTotalTransactionAmount();
    Boolean getTransactionRefurbishmentStatus();

}
