package com.openclassroom.project5.service.alert;

import com.openclassroom.project5.model.MedicalRecordsDTO;
import com.openclassroom.project5.model.PersonDto;
import com.openclassroom.project5.model.PersonMedicalInfoDto;
import com.openclassroom.project5.service.MedicalRecordsService;
import com.openclassroom.project5.service.PersonService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonMedicalInfoServiceTest {

    @Mock
    private PersonService personService;

    @Mock
    private MedicalRecordsService medicalRecordsService;

    @InjectMocks
    private PersonMedicalInfoService personMedicalInfoService;

    @Test
    void getPersonInfoByFullNameWhenNoPersonFound() {
        String firstName = "John";
        String lastName = "Doe";
        List<PersonMedicalInfoDto> expectedPersonInfoList = Collections.emptyList();
        when(personService.returnAllPersons()).thenReturn(Collections.emptyList());

        List<PersonMedicalInfoDto> actualPersonInfoList = personMedicalInfoService.getPersonInfoByFullName(firstName, lastName);

        assertEquals(expectedPersonInfoList, actualPersonInfoList);
        verify(personService, times(1)).returnAllPersons();
    }

    @Test
    void getPersonInfoByFullNameWhenPersonFound() {
        String firstName = "John";
        String lastName = "Doe";
        List<PersonMedicalInfoDto> personInfoList = new ArrayList<>();
        PersonDto personDto = new PersonDto(firstName, lastName, "123 Main St", "New York", 12345, "123-456-7890", "john.doe@example.com");
        MedicalRecordsDTO medicalRecordsDTO = new MedicalRecordsDTO(firstName, lastName, "01/01/1990", new String[]{"medication1", "medication2"}, new String[]{"allergy1", "allergy2"});
        List<PersonDto> personDtoList = Collections.singletonList(personDto);
        List<MedicalRecordsDTO> medicalRecordsDTOList = Collections.singletonList(medicalRecordsDTO);

        when(personService.returnAllPersons()).thenReturn(personDtoList);
        when(medicalRecordsService.returnAllMedicalRecords()).thenReturn(medicalRecordsDTOList);

        personInfoList = personMedicalInfoService.getPersonInfoByFullName(firstName, lastName);

        assertEquals(1, personInfoList.size());
        assertEquals(firstName, personInfoList.get(0).getFirstName());
        assertEquals(lastName, personInfoList.get(0).getLastName());
        assertEquals("123 Main St", personInfoList.get(0).getAddress());
        assertEquals(33, personInfoList.get(0).getAge());
        assertEquals("john.doe@example.com", personInfoList.get(0).getEmail());
        assertArrayEquals(new String[]{"medication1", "medication2"}, personInfoList.get(0).getMedications());
        assertArrayEquals(new String[]{"allergy1", "allergy2"}, personInfoList.get(0).getAllergies());

        verify(personService, times(1)).returnAllPersons();
        verify(medicalRecordsService, times(1)).returnAllMedicalRecords();
    }

    @Test
    void getPersonInfoByFullNameWhenMultiplePersonsWithSameLastNameFound() {// Create test data
        List<PersonDto> personDtoList = new ArrayList<>();
        personDtoList.add(new PersonDto("John", "Doe", "123 Main St", "New York", 10001, "555-555-5555", "john.doe@example.com"));
        personDtoList.add(new PersonDto("Jane", "Doe", "456 Elm St", "New York", 10001, "555-555-5555", "jane.doe@example.com"));
        personDtoList.add(new PersonDto("Bob", "Smith", "789 Oak St", "New York", 10001, "555-555-5555", "bob.smith@example.com"));

        List<MedicalRecordsDTO> medicalRecordsDTOList = new ArrayList<>();
        medicalRecordsDTOList.add(new MedicalRecordsDTO("John", "Doe", "01/01/1980", new String[]{"medication1", "medication2"}, new String[]{"allergy1", "allergy2"}));
        medicalRecordsDTOList.add(new MedicalRecordsDTO("Jane", "Doe", "02/02/1985", new String[]{"medication3", "medication4"}, new String[]{"allergy3", "allergy4"}));
        medicalRecordsDTOList.add(new MedicalRecordsDTO("Bob", "Smith", "03/03/1990", new String[]{"medication5", "medication6"}, new String[]{"allergy5", "allergy6"}));

        when(personService.returnAllPersons()).thenReturn(personDtoList);
        when(medicalRecordsService.returnAllMedicalRecords()).thenReturn(medicalRecordsDTOList);

        List<PersonMedicalInfoDto> personMedicalInfoDtoList = personMedicalInfoService.getPersonInfoByFullName("John", "Doe");

        assertEquals(2, personMedicalInfoDtoList.size());

        PersonMedicalInfoDto johnDoe = personMedicalInfoDtoList.get(0);
        assertEquals("John", johnDoe.getFirstName());
        assertEquals("Doe", johnDoe.getLastName());
        assertEquals("123 Main St", johnDoe.getAddress());
        assertEquals(43, johnDoe.getAge());
        assertEquals("john.doe@example.com", johnDoe.getEmail());
        assertArrayEquals(new String[]{"medication1", "medication2"}, johnDoe.getMedications());
        assertArrayEquals(new String[]{"allergy1", "allergy2"}, johnDoe.getAllergies());

        PersonMedicalInfoDto janeDoe = personMedicalInfoDtoList.get(1);
        assertEquals("Jane", janeDoe.getFirstName());
        assertEquals("Doe", janeDoe.getLastName());
        assertEquals("456 Elm St", janeDoe.getAddress());
        assertEquals(38, janeDoe.getAge());
        assertEquals("jane.doe@example.com", janeDoe.getEmail());
        assertArrayEquals(new String[]{"medication3", "medication4"}, janeDoe.getMedications());
        assertArrayEquals(new String[]{"allergy3", "allergy4"}, janeDoe.getAllergies());

        // Verify that the PersonService and MedicalRecordsService were called
        verify(personService, times(1)).returnAllPersons();
        verify(medicalRecordsService, times(1)).returnAllMedicalRecords();
    }

}