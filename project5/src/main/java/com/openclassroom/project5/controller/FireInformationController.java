package com.openclassroom.project5.controller;

import com.openclassroom.project5.service.alert.FireInformationService;
import com.openclassroom.project5.service.model.FireInformationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FireInformationController {

    @Autowired
    FireInformationService fireInformationService;

    @GetMapping("/fire")
    public ResponseEntity<?> getFireInformation(@RequestParam("address") String address) {
        FireInformationDto fireInfo = fireInformationService.getFireInformation(address);
        if (fireInfo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(fireInfo);
    }
}
