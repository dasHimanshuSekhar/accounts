package com.accounts.iskcon.patia.account.pojo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DevoteeLoginReq {
    @NotNull(message = "Mobile number is mandatory !")
    private Long mobileNumber;
    @NotNull(message = "Password is mandatory !")
    private String password;
}
