package com.openclassroom.project5.service;

import com.openclassroom.project5.model.MedicalRecordsDTO;
import com.openclassroom.project5.model.PersonDto;
import com.openclassroom.project5.model.PersonMedicalInfoDto;
import com.openclassroom.project5.service.alert.PersonMedicalInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonMedicalInfoServiceTest {

    @Mock
    private PersonService personService;

    @Mock
    private MedicalRecordsService medicalRecordsService;

    @InjectMocks
    private PersonMedicalInfoService personMedicalInfoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetPersonInfoByFullName() {
        // Mocking personService
        List<PersonDto> personDtoList = new ArrayList<>();
        PersonDto person1 = new PersonDto(
                "John", "Doe", "Address 1","city", 123, "phone", "john.doe@example.com");
        PersonDto person2 = new PersonDto(
                "John", "Smith", "Address 2","city2", 123, "phone", "john.smith@example.com");
        personDtoList.add(person1);
        personDtoList.add(person2);
        when(personService.returnAllPersons()).thenReturn(personDtoList);


        String[] medication = new String[1];
        medication[0] = "medication";
        String[] allergies = new String[1];
        medication[0] = "allergies";
        // Mocking medicalRecordsService
        List<MedicalRecordsDTO> medicalRecordsDTOList = new ArrayList<>();
        MedicalRecordsDTO medicalRecords1 = new MedicalRecordsDTO(
                "John", "Doe", "01/01/2000", medication, allergies);
        MedicalRecordsDTO medicalRecords2 = new MedicalRecordsDTO(
                "Jane", "Smith", "01/01/2020", medication, allergies);
        medicalRecordsDTOList.add(medicalRecords1);
        medicalRecordsDTOList.add(medicalRecords2);
        when(medicalRecordsService.returnAllMedicalRecords()).thenReturn(medicalRecordsDTOList);

        // Invoke the method being tested
        List<PersonMedicalInfoDto> personInfoList = personMedicalInfoService.getPersonInfoByFullName("John", "Doe");

        // Verify the results
        assertEquals(1, personInfoList.size());
        PersonMedicalInfoDto personInfo = personInfoList.get(0);
        assertEquals("John", personInfo.getFirstName());
        assertEquals("Doe", personInfo.getLastName());
        assertEquals("Address 1", personInfo.getAddress());
        assertEquals("john.doe@example.com", personInfo.getEmail());
        assertEquals(medication, personInfo.getMedications());
        assertEquals(allergies, personInfo.getAllergies());

        // Verify that the mocked methods were called
        verify(personService, times(1)).returnAllPersons();
        verify(medicalRecordsService, times(1)).returnAllMedicalRecords();
    }
}
