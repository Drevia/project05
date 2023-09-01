package com.openclassroom.project5.controller;

import com.openclassroom.project5.model.PersonMedicalInfoDto;
import com.openclassroom.project5.service.alert.PersonMedicalInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonMedicalInfoController {

    private final Logger logger = LogManager.getLogger(PersonMedicalInfoController.class);
    @Autowired
    private PersonMedicalInfoService medicalInfoService;

    @GetMapping("/personInfo")
    public ResponseEntity<List<PersonMedicalInfoDto>> getPersonInfo(@RequestParam("firstName") String firstName,
                                                                    @RequestParam("lastName") String lastName) {
        List<PersonMedicalInfoDto> personInfoList = medicalInfoService.getPersonInfoByFullName(firstName, lastName);

        if (personInfoList.isEmpty()) {
            logger.info("Searching Medical info for " + firstName + " " + lastName);
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("Cannot find info for " + firstName + " " + lastName);
            return ResponseEntity.ok(personInfoList);
        }
    }

}
