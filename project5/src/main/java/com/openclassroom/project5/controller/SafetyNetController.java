package com.openclassroom.project5.controller;

import com.openclassroom.project5.model.FireStationDTO;
import com.openclassroom.project5.model.MedicalRecordsDTO;
import com.openclassroom.project5.model.PersonDto;
import com.openclassroom.project5.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SafetyNetController {
    private final Logger logger = LogManager.getLogger(SafetyNetController.class);

    @Autowired
    private PersonService personService;

    @Autowired
    private MedicalRecordsService medicalService;

    @Autowired
    private FireStationService fireStationServiceImpl;

    @DeleteMapping("/person/{firstName}/{lastName}")
    public ResponseEntity<Void> deletePerson(@PathVariable String firstName, @PathVariable String lastName) {
        logger.info("Request to delete: "+ firstName + " " + lastName);

        if(personService.getPersonByFullName(firstName, lastName) != null)
        {
            logger.info("delete: "+ firstName + " " + lastName);
            personService.deletePerson(firstName, lastName);
            return ResponseEntity.ok().build();
        } else {
            logger.warn("Person not found");
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/person")
    public ResponseEntity<?> createPerson(@RequestBody PersonDto personDto) {
        if (personService.getPersonByFullName(personDto.getFirstName(), personDto.getLastName()) != null){
            logger.warn("This person already exist");
            return new ResponseEntity<>("Person already exist", HttpStatus.FORBIDDEN);
        } else {
            PersonDto personDtoCreated = personService.createPerson(personDto);
            logger.info("Created: " + personDto);
            return new ResponseEntity<>(personDtoCreated, HttpStatus.CREATED);
        }
    }

    @PutMapping("/person/{firstName}/{lastName}")
    public ResponseEntity<PersonDto> updatePerson(@PathVariable String firstName, @PathVariable String lastName, @RequestBody PersonDto personDto) {
        PersonDto personDtoUpdated = personService.updatePerson(firstName, lastName, personDto);
        if (personDtoUpdated != null) {
            logger.info("Updated: " + personDtoUpdated);
            return new ResponseEntity<>(personDtoUpdated, HttpStatus.OK);
        } else {
            logger.warn("Person to update not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/medicalRecords")
    public ResponseEntity<MedicalRecordsDTO> createMedicalRecords(@RequestBody MedicalRecordsDTO medicalRecordsDTO) {
        MedicalRecordsDTO medicalRecordsDTOCreated = medicalService.createMedicalRecords(medicalRecordsDTO);
        logger.info("Created: " + medicalRecordsDTOCreated);
        return new ResponseEntity<>(medicalRecordsDTOCreated, HttpStatus.CREATED);
    }

    @PutMapping("/medicalRecords/{firstName}/{lastName}")
    public ResponseEntity<MedicalRecordsDTO> updateMedicalRecords(@PathVariable String firstName, @PathVariable String lastName,
                                                                  @RequestBody MedicalRecordsDTO medicalRecordsDTO) {
        MedicalRecordsDTO updateMedicalRecords = medicalService.updateMedicalRecords(firstName, lastName, medicalRecordsDTO);
        if (updateMedicalRecords != null) {
            logger.info("Updated: " + updateMedicalRecords);
            return new ResponseEntity<>(updateMedicalRecords, HttpStatus.OK);
        } else {
            logger.warn("MedicalRecords not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/medicalRecords/{firstName}/{lastName}")
    public ResponseEntity<Void> deleteMedicalRecords(@PathVariable String firstName, @PathVariable String lastName) {
        if(medicalService.getMedicalRecords(firstName, lastName) != null)
        {
            logger.info("Deleting: " + firstName + " " + lastName + "'s MedicalRecords");
            medicalService.deleteMedicalRecords(firstName, lastName);
            return ResponseEntity.ok().build();
        } else {
            logger.warn("MedicalRecords not found");
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/fireStation")
    public ResponseEntity<FireStationDTO> createFireStation(@RequestBody FireStationDTO fireStationDTO) {
        logger.info("Creating: " + fireStationDTO);
        FireStationDTO fireStationDTOCreated = fireStationServiceImpl.createFireStation(fireStationDTO);
        return new ResponseEntity<>(fireStationDTOCreated, HttpStatus.CREATED);
    }

    @PutMapping("/fireStation/{station}/{address}")
    public ResponseEntity<FireStationDTO> updateFireStation(@PathVariable Integer station, @PathVariable String address,
                                                               @RequestBody FireStationDTO fireStationDTO) {
        FireStationDTO updateFireStation = fireStationServiceImpl.updateFireStation(station, address, fireStationDTO);
        if (updateFireStation != null) {
            logger.info("Updating: " + fireStationDTO);
            return new ResponseEntity<>(updateFireStation, HttpStatus.OK);
        } else {
            logger.warn("Cannot find FireStation: " + fireStationDTO);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/fireStation/{station}/{address}")
    public ResponseEntity<Void> deleteFireStation(@PathVariable Integer station, @PathVariable String address) {
        if(fireStationServiceImpl.getFireStation(station, address) != null)
        {
            logger.info("Deleting station number " + station);
            fireStationServiceImpl.deleteFireStation(station, address);
            return ResponseEntity.ok().build();
        } else {
            logger.warn("Station not found");
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/communityEmail")
    public ResponseEntity<List<String>> getEmailFromCity(@RequestParam(name = "city") String cityName){


        logger.info("Searching email from people living in " + cityName);
        List<String> emails = personService.getPersonsEmailByCity(cityName);
        return new ResponseEntity<>(emails, HttpStatus.OK);
    }

}
