package com.openclassroom.project5.controller;

import com.openclassroom.project5.service.ChildAlertService;
import com.openclassroom.project5.service.model.ChildAlertDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChildAlertController {

    private final Logger logger = LogManager.getLogger(ChildAlertController.class);
    @Autowired
    ChildAlertService childAlertService;

    @GetMapping("/childAlert")
    public ResponseEntity<?> getChildAlert(@RequestParam String address) {
        ChildAlertDto childAlertDto = childAlertService.getChildAlertByAddress(address);

        if (childAlertDto.getChild().isEmpty()) {
            logger.warn("No child found");
            return ResponseEntity.ok("");
        } else {
            logger.info(childAlertDto);
            return ResponseEntity.ok(childAlertDto);
        }
    }

}