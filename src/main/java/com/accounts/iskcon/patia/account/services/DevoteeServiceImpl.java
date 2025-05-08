package com.accounts.iskcon.patia.account.services;

import com.accounts.iskcon.patia.account.entities.Devotee;
import com.accounts.iskcon.patia.account.pojo.*;
import com.accounts.iskcon.patia.account.repos.DevoteeRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DevoteeServiceImpl implements DevoteeService{
    Logger logger = LoggerFactory.getLogger(DevoteeServiceImpl.class);

    final DevoteeRepo devoteeRepo;

    public DevoteeServiceImpl(DevoteeRepo devoteeRepo) {
        this.devoteeRepo = devoteeRepo;
    }

    @Override
    public DevoteeRegisterRes registerDevotee(DevoteeRegisterReq devoteeRegisterReq) {
        logger.info("Request received: {} :: API: register", devoteeRegisterReq);
        String mobileNumber = devoteeRegisterReq.getMobileNumber().toString();
        String password = devoteeRegisterReq.getName().substring(0,4) + mobileNumber.substring(mobileNumber.length()-4);
        try{

            if(devoteeRepo.existsByMobileNumber(devoteeRegisterReq.getMobileNumber())){
                logger.info("Devotee Already exists :: API: register");
                return new DevoteeRegisterRes(-1, "Devotee Already exists");
            }

            //save to db
            Devotee devotee = new Devotee();
            devotee.setName(devoteeRegisterReq.getName());
            devotee.setMobileNumber(devoteeRegisterReq.getMobileNumber());
            devotee.setPassword(password);

            devoteeRepo.save(devotee);
        } catch (Exception e) {
            logger.error("Exception :: MSG: {} :: API: register", e.getMessage());
            return new DevoteeRegisterRes(-1, "DB Exception Occurred !");
        }
        logger.error("Devotee Onboarded Successfully :: API: register");
        return new DevoteeRegisterRes(0, "Devotee Onboarded Successfully, your password is: " + password + ", Kindly remember for future usages !");
    }

    @Override
    public DevoteeLoginRes loginDevotee(DevoteeLoginReq devoteeLoginReq) {
        logger.info("Request received: {} :: API: login", devoteeLoginReq);
        Devotee devotee = new Devotee();
        try {
             devotee = devoteeRepo.findByMobileNumberAndPassword(devoteeLoginReq.getMobileNumber(), devoteeLoginReq.getPassword());
        } catch (Exception e) {
            logger.error("Exception :: MSG: {} :: API: login", e.getMessage());
            return new DevoteeLoginRes(-1, "DB Exception Occurred !");
        }
        if (devotee != null) {
            logger.error("Login successful :: API: login");
            return new DevoteeLoginRes(0, "Login successful !");
        } else {
            logger.error("Invalid mobile number or password :: API: login");
            return new DevoteeLoginRes(-1, "Invalid mobile number or password.");
        }
    }

    @Override
    public DevoteeFetchRes fetchDevotee() {
        logger.info("Request received :: API: fetch-all");
        List<Devotee> allDevotees = new ArrayList<>();
        try{
            allDevotees = devoteeRepo.findAll();
        } catch (Exception e) {
            logger.error("Exception :: MSG: {} :: API: fetch-all", e.getMessage());
            return new DevoteeFetchRes(-1, "DB Exception Occurred !", null);
        }

        List<DevoteeFetchRes.DevoteeDetails> details = allDevotees.stream()
                .map(devotee -> new DevoteeFetchRes.DevoteeDetails(devotee.getMobileNumber(), devotee.getName(), devotee.getPassword()))
                .collect(Collectors.toList());

        logger.error("Fetched successfully :: API: fetch-all");
        return new DevoteeFetchRes(0, "Fetched successfully", details);
    }
}
