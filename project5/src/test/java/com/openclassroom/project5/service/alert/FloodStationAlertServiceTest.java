package com.openclassroom.project5.service.alert;

import com.openclassroom.project5.model.FireStationDTO;
import com.openclassroom.project5.model.MedicalRecordsDTO;
import com.openclassroom.project5.model.PersonDto;
import com.openclassroom.project5.service.FireStationService;
import com.openclassroom.project5.service.MedicalRecordsService;
import com.openclassroom.project5.service.PersonService;
import com.openclassroom.project5.service.model.FloodStationDto;
import com.openclassroom.project5.service.model.Residents;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("FloodStationAlertService test")
class FloodStationAlertServiceTest {

    @InjectMocks
    private FloodStationAlertService floodStationAlertService;

    @Mock
    private FireStationService fireStationService;

    @Mock
    private PersonService personService;

    @Mock
    private MedicalRecordsService medicalRecordsService;


    @Test
    void testGetHouseholdsByStations() {
        // Mock data
        List<Integer> stationNumbers = Arrays.asList(1, 2);
        List<FireStationDTO> fireStationDTOList = Arrays.asList(
                new FireStationDTO("Adress1", 1),
                new FireStationDTO("Address2", 2),
                new FireStationDTO("Address3", 2)
        );
        List<PersonDto> personDtos = Arrays.asList(
                new PersonDto("John", "Doe", "Address1", "city", 12345, "phoneNbr", "email"),
                new PersonDto("Jane", "Smith", "Address2", "city", 67890, "phoneNbr", "email"),
                new PersonDto("Alice", "Johnson", "Address2", "city", 54321, "phoneNbr", "email"),
                new PersonDto("Bob", "Brown", "Address3", "city", 98765, "phoneNbr", "email")
        );
        List<MedicalRecordsDTO> medicalRecordsDTOS = Arrays.asList(
                new MedicalRecordsDTO("John", "Doe", "01/01/2030", new String[]{"Med1"}, new String[]{"Allergy1"}),
                new MedicalRecordsDTO("Jane", "Smith", "01/01/2025", new String[]{"Med2"}, new String[]{"Allergy2"}),
                new MedicalRecordsDTO("Alice", "Johnson", "01/01/2040", new String[]{"Med3"}, new String[]{"Allergy3"}),
                new MedicalRecordsDTO("Bob", "Brown", "01/01/2050", new String[]{"Med4"}, new String[]{"Allergy4"})
        );

        // Mock service methods
        when(fireStationService.returnAllFireStation()).thenReturn(fireStationDTOList);
        when(personService.returnAllPersons()).thenReturn(personDtos);
        when(medicalRecordsService.returnAllMedicalRecords()).thenReturn(medicalRecordsDTOS);

        // Expected result
        List<FloodStationDto> expectedHouseholds = Arrays.asList(
                createExpectedHouseholdInfo("Address1", "John", "Doe", "12345", 30, new String[]{"Med1"}, new String[]{"Allergy1"}),
                createExpectedHouseholdInfo("Address2", "Jane", "Smith", "67890", 25, new String[]{"Med2"}, new String[]{"Allergy2"}),
                createExpectedHouseholdInfo("Address2", "Alice", "Johnson", "54321", 40, new String[]{"Med3"}, new String[]{"Allergy3"}),
                createExpectedHouseholdInfo("Address3", "Bob", "Brown", "98765", 50, new String[]{"Med4"}, new String[]{"Allergy4"})
        );

        // Test the method
        List<FloodStationDto> result = floodStationAlertService.getHouseholdsByStations(stationNumbers);

        // Assertions
        assertEquals(2, result.size());
    }

    private FloodStationDto createExpectedHouseholdInfo(String address, String firstName, String lastName,
                                                        String phoneNumber, int age, String[] medications, String[] allergies) {
        FloodStationDto floodStationDto = new FloodStationDto();
        floodStationDto.setStationAddress(address);
        Residents resident = new Residents();
        resident.setFirstName(firstName);
        resident.setLastName(lastName);
        resident.setPhoneNumber(phoneNumber);
        resident.setAge(String.valueOf(age));
        resident.setMedications(Arrays.asList(medications));
        resident.setAllergies(Arrays.asList(allergies));
        floodStationDto.setResidents(Arrays.asList(resident));
        return floodStationDto;
    }

}