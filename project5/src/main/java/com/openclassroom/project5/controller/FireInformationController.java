package com.openclassroom.project5.controller;

import com.openclassroom.project5.service.alert.FireInformationService;
import com.openclassroom.project5.service.model.FireInformationDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FireInformationController {

    private final Logger logger = LogManager.getLogger(FireInformationController.class);
    @Autowired
    private FireInformationService fireInformationService;

    @GetMapping("/fire")
    public ResponseEntity<?> getFireInformation(@RequestParam("address") String address) {
        logger.info("Searching person leaving at " + address);
        FireInformationDto fireInfo = fireInformationService.getFireInformation(address);
        if (fireInfo == null) {
            logger.info("Cannot find anyone leaving at " + address);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(fireInfo);
    }
}
