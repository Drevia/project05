package com.openclassroom.project5.controller;

import com.openclassroom.project5.model.FireStationDTO;
import com.openclassroom.project5.model.MedicalRecordsDTO;
import com.openclassroom.project5.model.PersonDto;
import com.openclassroom.project5.service.FireStationServiceImpl;
import com.openclassroom.project5.service.MedicalRecordsServiceImpl;
import com.openclassroom.project5.service.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SafetyNetController {

    @Autowired
    PersonServiceImpl personService;

    @Autowired
    MedicalRecordsServiceImpl medicalService;

    @Autowired
    FireStationServiceImpl fireStationServiceImpl;

    @DeleteMapping("/person/{firstName}/{lastName}")
    public ResponseEntity<Void> deletePerson(@PathVariable String firstName, @PathVariable String lastName) {
        if(personService.getPersonByFullName(firstName, lastName) != null)
        {
            personService.deletePerson(firstName, lastName);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/person")
    public ResponseEntity<PersonDto> createPerson(@RequestBody PersonDto personDto) {
        PersonDto personDtoCreated = personService.createPerson(personDto);
        return new ResponseEntity<>(personDtoCreated, HttpStatus.CREATED);
    }

    @PutMapping("/person/{firstName}/{lastName}")
    public ResponseEntity<PersonDto> updatePerson(@PathVariable String firstName, @PathVariable String lastName, @RequestBody PersonDto personDto) {
        PersonDto personDtoUpdated = personService.updatePerson(firstName, lastName, personDto);
        if (personDtoUpdated != null) {
            return new ResponseEntity<>(personDtoUpdated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/medicalRecords")
    public ResponseEntity<MedicalRecordsDTO> createMedicalRecords(@RequestBody MedicalRecordsDTO medicalRecordsDTO) {
        MedicalRecordsDTO medicalRecordsDTOCreated = medicalService.createMedicalRecords(medicalRecordsDTO);
        return new ResponseEntity<>(medicalRecordsDTOCreated, HttpStatus.CREATED);
    }

    @PutMapping("/medicalRecords/{firstName}/{lastName}")
    public ResponseEntity<MedicalRecordsDTO> updateMedicalRecords(@PathVariable String firstName, @PathVariable String lastName,
                                                                  @RequestBody MedicalRecordsDTO medicalRecordsDTO) {
        MedicalRecordsDTO updateMedicalRecords = medicalService.updateMedicalRecords(firstName, lastName, medicalRecordsDTO);
        if (updateMedicalRecords != null) {
            return new ResponseEntity<>(updateMedicalRecords, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/medicalRecords/{firstName}/{lastName}")
    public ResponseEntity<Void> deleteMedicalRecords(@PathVariable String firstName, @PathVariable String lastName) {
        if(medicalService.getMedicalRecords(firstName, lastName) != null)
        {
            medicalService.deleteMedicalRecords(firstName, lastName);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/fireStation")
    public ResponseEntity<FireStationDTO> createFireStation(@RequestBody FireStationDTO fireStationDTO) {
        FireStationDTO fireStationDTOCreated = fireStationServiceImpl.createFireStation(fireStationDTO);
        return new ResponseEntity<>(fireStationDTOCreated, HttpStatus.CREATED);
    }

    @PutMapping("/fireStation/{station}/{address}")
    public ResponseEntity<FireStationDTO> updateFireStation(@PathVariable Integer station, @PathVariable String address,
                                                               @RequestBody FireStationDTO fireStationDTO) {
        FireStationDTO updateFireStation = fireStationServiceImpl.updateFireStation(station, address, fireStationDTO);
        if (updateFireStation != null) {
            return new ResponseEntity<>(updateFireStation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/fireStation/{station}/{address}")
    public ResponseEntity<Void> deleteFireStation(@PathVariable Integer station, @PathVariable String address) {
        if(fireStationServiceImpl.getFireStation(station, address) != null)
        {
            fireStationServiceImpl.deleteFireStation(station, address);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
