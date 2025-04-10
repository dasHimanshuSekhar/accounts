package com.accounts.iskcon.patia.account.services;

import com.accounts.iskcon.patia.account.pojo.DevoteeRegisterReq;
import com.accounts.iskcon.patia.account.pojo.DevoteeRegisterRes;

public interface DevoteeService {
    DevoteeRegisterRes registerDevotee(DevoteeRegisterReq devoteeRegisterReq);
}
