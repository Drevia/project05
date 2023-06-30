package com.openclassroom.project5.controller;

import com.openclassroom.project5.model.FireStationDTO;
import com.openclassroom.project5.model.PersonDto;
import com.openclassroom.project5.service.FireStationService;
import com.openclassroom.project5.service.PersonService;
import com.openclassroom.project5.service.alert.PhoneAlertService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PhoneAlertController {

    private final Logger logger = LogManager.getLogger(PhoneAlertController.class);
    @Autowired
    PersonService personService;

    @Autowired
    FireStationService fireStationService;

    @Autowired
    PhoneAlertService phoneAlertService;

    @GetMapping("/phoneAlert")
    public ResponseEntity<?> getPhoneAlert(@RequestParam("firestation") int firestationNumber) {
        List<PersonDto> people = personService.returnAllPersons();
        List<FireStationDTO> fireStations = fireStationService.returnAllFireStation();

        List<String> phoneNumbers = phoneAlertService.getPhoneNumbersByFireStation(firestationNumber, people, fireStations);

        if (phoneNumbers.isEmpty()) {
            logger.warn(phoneNumbers);
            return ResponseEntity.notFound().build();
        } else {
            logger.info("No phone numbers found");
            return ResponseEntity.ok(phoneNumbers);
        }
    }
}
