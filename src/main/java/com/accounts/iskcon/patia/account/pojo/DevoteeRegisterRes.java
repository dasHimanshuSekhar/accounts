package com.accounts.iskcon.patia.account.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DevoteeRegisterRes {
    private Integer statusCode;
    private String statusDesc;
}
