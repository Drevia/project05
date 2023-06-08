package com.openclassroom.project5.controller;

import com.openclassroom.project5.model.FireStationDTO;
import com.openclassroom.project5.model.PersonDto;
import com.openclassroom.project5.service.FireInformationService;
import com.openclassroom.project5.service.FireStationService;
import com.openclassroom.project5.service.PersonService;
import com.openclassroom.project5.service.model.FireInformationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class FireInformationController {

    @Autowired
    FireStationService fireStationService;

    @Autowired
    PersonService personService;

    @Autowired
    FireInformationService fireInformationService;

    @GetMapping("/fire")
    public ResponseEntity<?> getFireInformation(@RequestParam("address") String address) {
        try {
            FireInformationDto fireInformation = fireInformationService.getFireInformation(address);
            if (fireInformation == null) {
                return ResponseEntity.notFound().build(); // Adresse non trouvée
            }
            return ResponseEntity.ok().body(fireInformation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Erreur interne du serveur
        }
    }
}
