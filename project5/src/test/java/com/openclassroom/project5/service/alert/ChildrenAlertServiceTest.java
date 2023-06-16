package com.openclassroom.project5.service.alert;

import com.openclassroom.project5.model.MedicalRecordsDTO;
import com.openclassroom.project5.model.PersonDto;
import com.openclassroom.project5.service.MedicalRecordsService;
import com.openclassroom.project5.service.PersonService;
import com.openclassroom.project5.service.model.ChildAlertDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChildrenAlertServiceTest {

    @Mock
    private PersonService personService;

    @Mock
    private MedicalRecordsService medicalRecordsService;

    @InjectMocks
    private ChildrenAlertService childrenAlertService;

    @Test
    void getChildAlertByAddressNotFound() {
        String address = "123 Main St";
        when(personService.returnAllPersons()).thenReturn(new ArrayList<>());
        when(medicalRecordsService.returnAllMedicalRecords()).thenReturn(new ArrayList<>());

        ChildAlertDto result = childrenAlertService.getChildAlertByAddress(address);

        assertNotNull(result);
        assertTrue(result.getChildren().isEmpty());
        verify(personService, times(1)).returnAllPersons();
        verify(medicalRecordsService, times(1)).returnAllMedicalRecords();
    }

    @Test
    void getChildAlertByAddressWithoutChildren() {
        String address = "123 Main St";
        List<PersonDto> emptyList = new ArrayList<>();
        List<MedicalRecordsDTO> medicalRecordsDTOS = new ArrayList<>();

        when(personService.returnAllPersons()).thenReturn(emptyList);
        when(medicalRecordsService.returnAllMedicalRecords()).thenReturn(medicalRecordsDTOS);

        ChildAlertDto result = childrenAlertService.getChildAlertByAddress(address);

        assertNotNull(result);
        assertTrue(result.getChildren().isEmpty());
    }

    @Test
    void getChildAlertByAddressWithChildren() {
        List<PersonDto> persons = new ArrayList<>();
        persons.add(new PersonDto("John", "Doe", "123 Main St", "New York", 10001, "555-1234", "john.doe@example.com"));
        persons.add(new PersonDto("Jane", "Doe", "123 Main St", "New York", 10001, "555-5678", "jane.doe@example.com"));
        persons.add(new PersonDto("Bob", "Smith", "456 Elm St", "New York", 10002, "555-4321", "bob.smith@example.com"));

        List<MedicalRecordsDTO> medicalRecords = new ArrayList<>();
        medicalRecords.add(new MedicalRecordsDTO("John", "Doe", "01/01/2000", new String[]{"med1", "med2"}, new String[]{"allergy1", "allergy2"}));
        medicalRecords.add(new MedicalRecordsDTO("Jane", "Doe", "01/01/2005", new String[]{"med3", "med4"}, new String[]{"allergy3", "allergy4"}));
        medicalRecords.add(new MedicalRecordsDTO("Bob", "Smith", "01/01/2010", new String[]{"med5", "med6"}, new String[]{"allergy5", "allergy6"}));

        when(personService.returnAllPersons()).thenReturn(persons);
        when(medicalRecordsService.returnAllMedicalRecords()).thenReturn(medicalRecords);

        ChildAlertDto childAlertDto = childrenAlertService.getChildAlertByAddress("123 Main St");

        assertEquals(1, childAlertDto.getChildren().size());
        assertTrue(childAlertDto.getChildren().stream().anyMatch(p -> p.getFirstName().equals("Jane")));

        verify(personService, times(1)).returnAllPersons();
        verify(medicalRecordsService, times(1)).returnAllMedicalRecords();
    }

}