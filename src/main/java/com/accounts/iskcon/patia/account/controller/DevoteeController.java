package com.accounts.iskcon.patia.account.controller;

import com.accounts.iskcon.patia.account.pojo.DevoteeRegisterReq;
import com.accounts.iskcon.patia.account.pojo.DevoteeRegisterRes;
import com.accounts.iskcon.patia.account.services.DevoteeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("dvt")
public class DevoteeController {


    final DevoteeService devoteeService;

    public DevoteeController(DevoteeService devoteeService) {
        this.devoteeService = devoteeService;
    }

    @CrossOrigin(value = "*")
    @GetMapping("test")
    public String test(){
        return "Account 1.0 !";
    }

    @CrossOrigin(value = "*")
    @PostMapping("register")
    public DevoteeRegisterRes registerDevotee(@RequestBody DevoteeRegisterReq devoteeRegisterReq){
        return devoteeService.registerDevotee(devoteeRegisterReq);
    }


}
