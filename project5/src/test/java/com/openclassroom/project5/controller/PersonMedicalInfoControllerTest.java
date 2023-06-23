package com.openclassroom.project5.controller;

import com.openclassroom.project5.model.PersonMedicalInfoDto;
import com.openclassroom.project5.service.alert.PersonMedicalInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonMedicalInfoControllerTest {
    private PersonMedicalInfoController personMedicalInfoController;

    @Mock
    private PersonMedicalInfoService medicalInfoService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        personMedicalInfoController = new PersonMedicalInfoController();
        personMedicalInfoController.medicalInfoService = medicalInfoService;
    }

    @Test
    void testGetPersonInfo_FoundPersonInfo() {
        // Mock data
        String firstName = "John";
        String lastName = "Doe";
        List<PersonMedicalInfoDto> personInfoList = Collections.singletonList(
                new PersonMedicalInfoDto(firstName, lastName, "Address1", 30, "email", new String[]{"Med1"}, new String[]{"Allergy1"})
        );

        // Mock service method
        when(medicalInfoService.getPersonInfoByFullName(firstName, lastName))
                .thenReturn(personInfoList);

        // Test the controller method
        ResponseEntity<List<PersonMedicalInfoDto>> response = personMedicalInfoController.getPersonInfo(firstName, lastName);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(personInfoList, response.getBody());
    }

    @Test
    void testGetPersonInfo_NoPersonInfo() {
        // Mock data
        String firstName = "Jane";
        String lastName = "Smith";
        List<PersonMedicalInfoDto> emptyPersonInfoList = new ArrayList<>();

        // Mock service method
        when(medicalInfoService.getPersonInfoByFullName(firstName, lastName))
                .thenReturn(emptyPersonInfoList);

        // Test the controller method
        ResponseEntity<List<PersonMedicalInfoDto>> response = personMedicalInfoController.getPersonInfo(firstName, lastName);

        // Assertions
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }
}

