package com.openclassroom.project5.controller;

import com.openclassroom.project5.model.FireStationDTO;
import com.openclassroom.project5.model.MedicalRecordsDTO;
import com.openclassroom.project5.model.PersonDto;
import com.openclassroom.project5.service.FireStationServiceImpl;
import com.openclassroom.project5.service.MedicalRecordsServiceImpl;
import com.openclassroom.project5.service.PersonServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SafetyNetControllerTest {

    @Mock
    private PersonServiceImpl personService;

    @Mock
    FireStationServiceImpl fireStationServiceImpl;

    @Mock
    MedicalRecordsServiceImpl medicalRecordsServiceImpl;

    @InjectMocks
    private SafetyNetController controller;

    @Test
    public void testDeletePerson() {
        String firstName = "John";
        String lastName = "Doe";
        when(personService.getPersonByFullName(firstName, lastName)).thenReturn(new PersonDto());
        ResponseEntity<Void> response = controller.deletePerson(firstName, lastName);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(personService).deletePerson(firstName, lastName);
    }

    @Test
    public void deletePersonReturnNotFound() {
        String firstName = "John";
        String lastName = "Doe";
        when(personService.getPersonByFullName(anyString(), anyString())).thenReturn(null);
        ResponseEntity<Void> response = controller.deletePerson(firstName, lastName);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdatePerson() {
        String firstName = "John";
        String lastName = "Doe";
        PersonDto personDto = new PersonDto();
        when(personService.updatePerson(firstName, lastName, personDto)).thenReturn(personDto);
        ResponseEntity<PersonDto> response = controller.updatePerson(firstName, lastName, personDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(personDto, response.getBody());
    }

    @Test
    public void testUpdatePersonNotFound() {
        String firstName = "John";
        String lastName = "Doe";
        PersonDto personDto = new PersonDto();
        when(personService.updatePerson(firstName, lastName, personDto)).thenReturn(null);
        ResponseEntity<PersonDto> response = controller.updatePerson(firstName, lastName, personDto);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreatePerson() {
        PersonDto personDto = new PersonDto();
        when(personService.createPerson(personDto)).thenReturn(personDto);
        ResponseEntity<?> response = controller.createPerson(personDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(personDto, response.getBody());
    }

    @Test
    public void testDeleteFireStation() {
        String address = "some address";
        Integer station = 10;
        when(fireStationServiceImpl.getFireStation(station, address)).thenReturn(new FireStationDTO());
        ResponseEntity<Void> response = controller.deleteFireStation(station, address);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(fireStationServiceImpl).deleteFireStation(station, address);
    }

    @Test
    public void deleteFireStationReturnNotFound() {
        String address = "some address";
        Integer station = 10;
        when(fireStationServiceImpl.getFireStation(anyInt(), anyString())).thenReturn(null);
        ResponseEntity<Void> response = controller.deleteFireStation(station, address);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateFireStation() {
        String address = "some address";
        Integer station = 10;
        FireStationDTO fireStationDTO = new FireStationDTO();
        when(fireStationServiceImpl.updateFireStation(station, address, fireStationDTO)).thenReturn(fireStationDTO);
        ResponseEntity<FireStationDTO> response = controller.updateFireStation(station, address, fireStationDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(fireStationDTO, response.getBody());
    }

    @Test
    public void testUpdateFireStationNotFound() {
        String address = "some address";
        Integer station = 10;
        FireStationDTO fireStationDTO = new FireStationDTO();
        when(fireStationServiceImpl.updateFireStation(station, address, fireStationDTO)).thenReturn(null);
        ResponseEntity<FireStationDTO> response = controller.updateFireStation(station, address , fireStationDTO);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreateFireStation() {
        FireStationDTO fireStationDTO = new FireStationDTO();
        when(fireStationServiceImpl.createFireStation(fireStationDTO)).thenReturn(fireStationDTO);
        ResponseEntity<FireStationDTO> response = controller.createFireStation(fireStationDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(fireStationDTO, response.getBody());
    }

    @Test
    public void testDeleteMedicalRecord() {
        String firstName = "John";
        String lastName = "Doe";
        when(medicalRecordsServiceImpl.getMedicalRecords(firstName, lastName)).thenReturn(new MedicalRecordsDTO());
        ResponseEntity<Void> response = controller.deleteMedicalRecords(firstName, lastName);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(medicalRecordsServiceImpl).deleteMedicalRecords(firstName, lastName);
    }

    @Test
    public void deleteMedicalRecordReturnNotFound() {
        String firstName = "John";
        String lastName = "Doe";
        when(medicalRecordsServiceImpl.getMedicalRecords(any(), any())).thenReturn(null);
        ResponseEntity<Void> response = controller.deleteMedicalRecords(firstName, lastName);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateMedicalRecord() {
        String firstName = "John";
        String lastName = "Doe";
        MedicalRecordsDTO medicalRecordsDTO = new MedicalRecordsDTO();
        when(medicalRecordsServiceImpl.updateMedicalRecords(firstName, lastName, medicalRecordsDTO)).thenReturn(medicalRecordsDTO);
        ResponseEntity<MedicalRecordsDTO> response = controller.updateMedicalRecords(firstName, lastName, medicalRecordsDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(medicalRecordsDTO, response.getBody());
    }

    @Test
    public void testUpdateMedicalRecordNotFound() {
        String firstName = "John";
        String lastName = "Doe";
        MedicalRecordsDTO medicalRecordsDTO = new MedicalRecordsDTO();
        when(medicalRecordsServiceImpl.updateMedicalRecords(firstName, lastName, medicalRecordsDTO)).thenReturn(null);
        ResponseEntity<MedicalRecordsDTO> response = controller.updateMedicalRecords(firstName, lastName, medicalRecordsDTO);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreateMedicalRecord() {
        MedicalRecordsDTO medicalRecordsDTO = new MedicalRecordsDTO();
        when(medicalRecordsServiceImpl.createMedicalRecords(medicalRecordsDTO)).thenReturn(medicalRecordsDTO);
        ResponseEntity<MedicalRecordsDTO> response = controller.createMedicalRecords(medicalRecordsDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

}
