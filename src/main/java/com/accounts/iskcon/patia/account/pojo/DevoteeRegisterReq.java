package com.accounts.iskcon.patia.account.pojo;

import lombok.Data;
import javax.validation.constraints.NotNull;


@Data
public class DevoteeRegisterReq {
    @NotNull(message = "Mobile number is mandatory !")
    private Long mobileNumber;
    @NotNull(message = "Name is mandatory !")
    private String name;
}
