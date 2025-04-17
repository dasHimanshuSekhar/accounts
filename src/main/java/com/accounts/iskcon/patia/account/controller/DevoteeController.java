package com.accounts.iskcon.patia.account.controller;

import com.accounts.iskcon.patia.account.pojo.DevoteeRegisterReq;
import com.accounts.iskcon.patia.account.pojo.DevoteeRegisterRes;
import com.accounts.iskcon.patia.account.services.DevoteeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("devotee")
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
    public ResponseEntity<DevoteeRegisterRes> registerDevotee(@RequestBody DevoteeRegisterReq devoteeRegisterReq){
        DevoteeRegisterRes devoteeRegisterRes = devoteeService.registerDevotee(devoteeRegisterReq);
        return ResponseEntity.status(devoteeRegisterRes.getStatusCode().equals(-1) ? HttpStatus.NOT_FOUND : HttpStatus.OK).body(devoteeRegisterRes);
    }


}
