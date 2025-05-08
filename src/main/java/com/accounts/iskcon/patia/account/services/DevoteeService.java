package com.accounts.iskcon.patia.account.services;

import com.accounts.iskcon.patia.account.pojo.*;

public interface DevoteeService {
    DevoteeRegisterRes registerDevotee(DevoteeRegisterReq devoteeRegisterReq);

    DevoteeLoginRes loginDevotee(DevoteeLoginReq devoteeLoginReq);

    DevoteeFetchRes fetchDevotee();
}
