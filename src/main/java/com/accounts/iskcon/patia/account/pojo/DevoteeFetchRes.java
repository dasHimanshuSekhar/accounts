package com.accounts.iskcon.patia.account.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DevoteeFetchRes {
    private Integer statusCode;
    private String statusDesc;
    private List<DevoteeDetails> devoteeDetails;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DevoteeDetails {
        private Long mobileNumber;
        private String name;
        private String password;
    }
}
