package com.openclassroom.project5.controller;

import com.openclassroom.project5.model.FireStationDTO;
import com.openclassroom.project5.model.MedicalRecordsDTO;
import com.openclassroom.project5.model.PersonDto;
import com.openclassroom.project5.model.PersonMedicalInfoDto;
import com.openclassroom.project5.service.alert.FireStationServiceCoverage;
import com.openclassroom.project5.service.model.FireStationCoverageDTO;
import com.openclassroom.project5.service.model.PersonInfoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FireStationCoverageControllerTest {

    private FireStationCoverageController fireStationCoverageController;

    @Mock
    private FireStationServiceCoverage fireStationServiceCoverage;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        fireStationCoverageController = new FireStationCoverageController();
        fireStationCoverageController.fireStationServiceCoverage = fireStationServiceCoverage;
    }

    @Test
    void testGetFireStationCoverage_FoundCoverage() {


        System.out.println(Arrays.toString(FireStationCoverageControllerTest.class.getAnnotations()));
        // Mock data
        int stationNumber = 1;
        FireStationCoverageDTO fireStationCoverageDTO = new FireStationCoverageDTO();
        fireStationCoverageDTO.setAdultCount(2);
        fireStationCoverageDTO.setChildCount(1);
        List<PersonDto> coveredPeople = new ArrayList<>();
        String[] medication = new String[1];
        medication[0] = "medication";
        String[] allergies = new String[1];
        medication[0] = "allergies";
        MedicalRecordsDTO medicalRecords1 = new MedicalRecordsDTO(
                "John", "Doe", "01/01/2000", medication, allergies);
        MedicalRecordsDTO medicalRecords2 = new MedicalRecordsDTO(
                "Jane", "Smith", "01/01/2020", medication, allergies);
        coveredPeople.add(new PersonDto("Firstname", "Lastname", "Address 1", "City 1", 123, "phone", "email1"));
        coveredPeople.add(new PersonDto("Firstname1", "Lastname2", "Address 2", "City 2", 123, "phone", "email2"));
        fireStationCoverageDTO.setPeople(coveredPeople);

        // Mock service method
        when(fireStationServiceCoverage.getFireStationCoverage(stationNumber))
                .thenReturn(fireStationCoverageDTO);

        // Test the controller method
        ResponseEntity<FireStationCoverageDTO> response = fireStationCoverageController.getFireStationCoverage(stationNumber);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(fireStationCoverageDTO, response.getBody());
    }

    @Test
    void testGetFireStationCoverage_NoCoverage() {
        // Mock data
        int stationNumber = 2;

        // Mock service method
        when(fireStationServiceCoverage.getFireStationCoverage(stationNumber))
                .thenReturn(null);

        // Test the controller method
        ResponseEntity<FireStationCoverageDTO> response = fireStationCoverageController.getFireStationCoverage(stationNumber);

        // Assertions
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}
