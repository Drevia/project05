package com.openclassroom.project5.service.alert;

import com.openclassroom.project5.model.FireStationDTO;
import com.openclassroom.project5.model.MedicalRecordsDTO;
import com.openclassroom.project5.model.PersonDto;
import com.openclassroom.project5.service.FireStationService;
import com.openclassroom.project5.service.MedicalRecordsService;
import com.openclassroom.project5.service.PersonService;
import com.openclassroom.project5.service.model.FireStationCoverageDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FireStationServiceCoverageTest {

    @Mock
    private FireStationService fireStationService;

    @Mock
    private PersonService personService;

    @Mock
    private MedicalRecordsService medicalRecordsService;

    @InjectMocks
    private FireStationServiceCoverage fireStationServiceCoverage;

    @Test
    void getFireStationCoverageForInvalidStationNumber() {
        int invalidStationNumber = 10;
        when(fireStationService.returnAllFireStation()).thenReturn(List.of(
                new FireStationDTO("address1", 1),
                new FireStationDTO("address2", 2)
        ));
        when(personService.returnAllPersons()).thenReturn(List.of(
                new PersonDto("John", "Doe", "address1", "city1", 12345, "123-456-7890", "john.doe@example.com"),
                new PersonDto("Jane", "Doe", "address2", "city2", 67890, "987-654-3210", "jane.doe@example.com")
        ));
        when(medicalRecordsService.returnAllMedicalRecords()).thenReturn(List.of(
                new MedicalRecordsDTO("John", "Doe", "01/01/2000", new String[]{"med1", "med2"}, new String[]{"allergy1", "allergy2"}),
                new MedicalRecordsDTO("Jane", "Doe", "01/01/2010", new String[]{"med3", "med4"}, new String[]{"allergy3", "allergy4"})
        ));

        FireStationCoverageDTO coverageDTO = fireStationServiceCoverage.getFireStationCoverage(invalidStationNumber);

        assertTrue(coverageDTO.getPeople().isEmpty());
        verify(fireStationService, times(1)).returnAllFireStation();
        verify(personService, times(1)).returnAllPersons();
        verify(medicalRecordsService, times(1)).returnAllMedicalRecords();
    }

    @Test
    void getFireStationCoverageForValidStationNumber() {
        int stationNumber = 1;

        FireStationDTO fireStationDTO = new FireStationDTO();
        fireStationDTO.setAddress("123 Main St");
        fireStationDTO.setStation(1);

        PersonDto personDto = new PersonDto();
        personDto.setFirstName("John");
        personDto.setLastName("Doe");
        personDto.setAddress("123 Main St");
        personDto.setCity("New York");
        personDto.setZip(12345);
        personDto.setPhone("123-456-7890");
        personDto.setEmail("john.doe@example.com");

        MedicalRecordsDTO medicalRecordsDTO = new MedicalRecordsDTO();
        medicalRecordsDTO.setFirstName("John");
        medicalRecordsDTO.setLastName("Doe");
        medicalRecordsDTO.setBirthdate("01/01/2000");
        medicalRecordsDTO.setMedications(new String[]{"medication1", "medication2"});
        medicalRecordsDTO.setAllergies(new String[]{"allergy1", "allergy2"});

        when(fireStationService.returnAllFireStation()).thenReturn(Collections.singletonList(fireStationDTO));
        when(personService.returnAllPersons()).thenReturn(Collections.singletonList(personDto));
        when(medicalRecordsService.returnAllMedicalRecords()).thenReturn(Collections.singletonList(medicalRecordsDTO));

        FireStationCoverageDTO coverageDTO = fireStationServiceCoverage.getFireStationCoverage(stationNumber);

        assertNotNull(coverageDTO);
        assertEquals(1, coverageDTO.getPeople().size());
        assertEquals(1, coverageDTO.getAdultCount());
        assertEquals(0, coverageDTO.getChildCount());
        assertEquals(personDto, coverageDTO.getPeople().get(0));
        verify(fireStationService, times(1)).returnAllFireStation();
        verify(personService, times(1)).returnAllPersons();
        verify(medicalRecordsService, times(1)).returnAllMedicalRecords();
    }

    @Test
    void getFireStationCoverageWhenNoMedicalRecordsFound() {
        int stationNumber = 1;

        FireStationDTO fireStationDTO = new FireStationDTO();
        fireStationDTO.setAddress("123 Main St");
        fireStationDTO.setStation(1);

        PersonDto personDto = new PersonDto();
        personDto.setFirstName("John");
        personDto.setLastName("Doe");
        personDto.setAddress("123 Main St");
        personDto.setCity("New York");
        personDto.setZip(12345);
        personDto.setPhone("123-456-7890");
        personDto.setEmail("john.doe@example.com");

        MedicalRecordsDTO medicalRecordsDTO = new MedicalRecordsDTO();
        medicalRecordsDTO.setFirstName("John");
        medicalRecordsDTO.setLastName("Doe");
        medicalRecordsDTO.setBirthdate("01/01/2000");
        medicalRecordsDTO.setMedications(new String[]{"medication1", "medication2"});
        medicalRecordsDTO.setAllergies(new String[]{"allergy1", "allergy2"});

        when(fireStationService.returnAllFireStation()).thenReturn(Collections.singletonList(fireStationDTO));
        when(personService.returnAllPersons()).thenReturn(Collections.singletonList(personDto));
        when(medicalRecordsService.returnAllMedicalRecords()).thenReturn(Collections.singletonList(medicalRecordsDTO));

        FireStationCoverageDTO fireStationCoverageDTO = fireStationServiceCoverage.getFireStationCoverage(stationNumber);

        assertNotNull(fireStationCoverageDTO);
        assertEquals(1, fireStationCoverageDTO.getPeople().size());
        assertEquals(0, fireStationCoverageDTO.getChildCount());
        assertEquals(1, fireStationCoverageDTO.getAdultCount());
        verify(fireStationService, times(1)).returnAllFireStation();
        verify(personService, times(1)).returnAllPersons();
        verify(medicalRecordsService, times(1)).returnAllMedicalRecords();
        verifyNoMoreInteractions(fireStationService, personService, medicalRecordsService);
    }

}