package com.accounts.iskcon.patia.account.services;

import com.accounts.iskcon.patia.account.entities.Devotee;
import com.accounts.iskcon.patia.account.pojo.DevoteeRegisterReq;
import com.accounts.iskcon.patia.account.pojo.DevoteeRegisterRes;
import com.accounts.iskcon.patia.account.repos.DevoteeRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DevoteeServiceImpl implements DevoteeService{
    Logger logger = LoggerFactory.getLogger(DevoteeServiceImpl.class);

    final DevoteeRepo devoteeRepo;

    public DevoteeServiceImpl(DevoteeRepo devoteeRepo) {
        this.devoteeRepo = devoteeRepo;
    }

    @Override
    public DevoteeRegisterRes registerDevotee(DevoteeRegisterReq devoteeRegisterReq) {
        String mobileNumber = devoteeRegisterReq.getMobileNumber().toString();
        String password = devoteeRegisterReq.getName().substring(0,4) + mobileNumber.substring(mobileNumber.length()-4, mobileNumber.length());
        try{

            if(devoteeRepo.existsByMobileNumber(devoteeRegisterReq.getMobileNumber())){
                return new DevoteeRegisterRes(-1, "Devotee Already exists");
            }

            //save to db
            Devotee devotee = new Devotee();
            devotee.setName(devoteeRegisterReq.getName());
            devotee.setMobileNumber(devoteeRegisterReq.getMobileNumber());
            devotee.setPassword(password);

            devoteeRepo.save(devotee);
        } catch (Exception e) {
            logger.error("Exception :: MSG: {}", e.getMessage());
            return new DevoteeRegisterRes(-1, "DB Exception Occurred !");
        }
        return new DevoteeRegisterRes(0, "Devotee Onboarded Successfully !");
    }
}
