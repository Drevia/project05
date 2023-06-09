package com.openclassroom.project5.service;

import com.openclassroom.project5.model.MedicalRecordsDTO;
import com.openclassroom.project5.model.PersonDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordsServiceTest {

    @Mock
    private List<MedicalRecordsDTO> medicalRecordsDTOList = new ArrayList<>();

    @InjectMocks
    private MedicalRecordsServiceImpl medicalRecordsService;


    @Test
    public void testGetPerson_Exists() {
        // Mocking the behavior
        String[] medication = new String[1];
        medication[0] = "medication";
        String[] allergies = new String[1];
        medication[0] = "allergies";
        MedicalRecordsDTO medicalRecords1 = new MedicalRecordsDTO(
                "Firstname", "Lastname", "01/01/2020", medication, allergies);
        MedicalRecordsDTO medicalRecords2 = new MedicalRecordsDTO(
                "Firstname", "Lastname", "02/02/2020", medication, allergies);

        when(medicalRecordsDTOList.stream()).thenReturn(Stream.of(medicalRecords1, medicalRecords2));

        // Invoke the method being tested
        MedicalRecordsDTO result = medicalRecordsService.getMedicalRecords("Firstname", "Lastname");

        // Verify the result
        assertNotNull(result);
        assertEquals("Lastname", result.getLastName());
        assertEquals("Firstname", result.getFirstName());
        assertEquals("01/01/2020", result.getBirthdate());

        // Verify that the mocked method was called
        verify(medicalRecordsDTOList, times(1)).stream();
    }

    @Test
    public void testGetPerson_NotExists() {
        // Mocking the behavior
        when(medicalRecordsDTOList.stream()).thenReturn(Stream.empty());

        // Invoke the method being tested
        MedicalRecordsDTO result = medicalRecordsService.getMedicalRecords("Firstname", "Lastname");

        // Verify the result
        assertNull(result);

        // Verify that the mocked method was called
        verify(medicalRecordsDTOList, times(1)).stream();
    }

    @Test
    public void testDeletePerson_NotExists() {
        // Mocking the behavior
        lenient().when(medicalRecordsDTOList.contains(any(PersonDto.class))).thenReturn(false);

        // Invoke the method being tested
        medicalRecordsService.deleteMedicalRecords("Firstname", "Lastname");

        // Verify that the mocked method was not called
        verify(medicalRecordsDTOList, never()).remove(any(PersonDto.class));
    }

    @Test
    public void testCreatePerson() {
        // Mocking the behavior
        MedicalRecordsDTO medicalRecords = new MedicalRecordsDTO(
                "Firstname", "Lastname", "02/02/2020", null, null);
        when(medicalRecordsDTOList.add(any(MedicalRecordsDTO.class))).thenReturn(true);

        // Invoke the method being tested
        MedicalRecordsDTO result = medicalRecordsService.createMedicalRecords(medicalRecords);

        // Verify the result
        assertNotNull(result);
        assertEquals("02/02/2020", result.getBirthdate());
        assertEquals("Firstname", result.getFirstName());

        // Verify that the mocked method was called
        verify(medicalRecordsDTOList, times(1)).add(medicalRecords);
    }

    @Test
    public void testUpdatePerson_Exists() {
        // Mocking the behavior
        MedicalRecordsDTO medicalRecords = new MedicalRecordsDTO(
                "Firstname", "Lastname", "02/02/2020", null, null);
        when(medicalRecordsDTOList.stream()).thenReturn(Stream.of(medicalRecords));

        // Invoke the method being tested
        MedicalRecordsDTO updatedMedicalRecords = new MedicalRecordsDTO(
                "Firstname", "Lastname", "02/02/2020", null, null);
        MedicalRecordsDTO result = medicalRecordsService.updateMedicalRecords("Firstname", "Lastname", updatedMedicalRecords);

        // Verify the result
        assertNotNull(result);
        assertEquals("02/02/2020", result.getBirthdate());
        assertEquals("Firstname", result.getFirstName());

        // Verify that the mocked method was called
        verify(medicalRecordsDTOList, times(1)).stream();
    }

    @Test
    public void testUpdatePerson_NotExists() {
        // Mocking the behavior
        when(medicalRecordsDTOList.stream()).thenReturn(Stream.empty());

        // Invoke the method being tested
        MedicalRecordsDTO updatedMedical = new MedicalRecordsDTO(
                "Firstname", "Lastname", "01/01/2000", null, null);
        MedicalRecordsDTO result = medicalRecordsService.updateMedicalRecords("Firstname", "Lastname", updatedMedical);

        // Verify the result
        assertNull(result);

        // Verify that the mocked method was called
        verify(medicalRecordsDTOList, times(1)).stream();
    }
}
