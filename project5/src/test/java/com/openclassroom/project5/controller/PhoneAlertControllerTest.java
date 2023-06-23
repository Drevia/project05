package com.openclassroom.project5.controller;

import com.openclassroom.project5.model.FireStationDTO;
import com.openclassroom.project5.model.PersonDto;
import com.openclassroom.project5.service.FireStationService;
import com.openclassroom.project5.service.PersonService;
import com.openclassroom.project5.service.alert.PhoneAlertService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PhoneAlertControllerTest {
    private PhoneAlertController phoneAlertController;

    @Mock
    private PersonService personService;

    @Mock
    private FireStationService fireStationService;

    @Mock
    private PhoneAlertService phoneAlertService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        phoneAlertController = new PhoneAlertController();
        phoneAlertController.personService = personService;
        phoneAlertController.fireStationService = fireStationService;
        phoneAlertController.phoneAlertService = phoneAlertService;
    }

    @Test
    void testGetPhoneAlert_FoundPhoneNumbers() {
        // Mock data
        int firestationNumber = 1;
        List<PersonDto> people = Arrays.asList(
                new PersonDto("John", "Doe", "Address1", "city", 12345, "phoneNbr", "email"),
                new PersonDto("Jane", "Smith", "Address2", "city", 67890, "phoneNbr", "email")
        );
        List<FireStationDTO> fireStations = Collections.singletonList(
                new FireStationDTO("Address1", firestationNumber)
        );
        List<String> phoneNumbers = Arrays.asList("12345", "67890");

        // Mock service methods
        when(personService.returnAllPersons()).thenReturn(people);
        when(fireStationService.returnAllFireStation()).thenReturn(fireStations);
        when(phoneAlertService.getPhoneNumbersByFireStation(firestationNumber, people, fireStations))
                .thenReturn(phoneNumbers);

        // Test the controller method
        ResponseEntity<?> response = phoneAlertController.getPhoneAlert(firestationNumber);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(phoneNumbers, response.getBody());
    }

    @Test
    void testGetPhoneAlert_NoPhoneNumbers() {
        // Mock data
        int firestationNumber = 1;
        List<PersonDto> people = Arrays.asList(
                new PersonDto("John", "Doe", "Address1", "city", 12345, "phoneNbr", "email"),
                new PersonDto("Jane", "Smith", "Address2", "city", 67890, "phoneNbr", "email")
        );
        List<FireStationDTO> fireStations = Collections.singletonList(
                new FireStationDTO("Address1", 2)
        );
        List<String> emptyPhoneNumbers = new ArrayList<>();

        // Mock service methods
        when(personService.returnAllPersons()).thenReturn(people);
        when(fireStationService.returnAllFireStation()).thenReturn(fireStations);
        when(phoneAlertService.getPhoneNumbersByFireStation(firestationNumber, people, fireStations))
                .thenReturn(emptyPhoneNumbers);

        // Test the controller method
        ResponseEntity<?> response = phoneAlertController.getPhoneAlert(firestationNumber);

        // Assertions
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}
