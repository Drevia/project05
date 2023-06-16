package com.openclassroom.project5.controller;

import com.openclassroom.project5.model.PersonDto;
import com.openclassroom.project5.service.MedicalRecordsService;
import com.openclassroom.project5.service.PersonService;
import com.openclassroom.project5.service.alert.ChildrenAlertService;
import com.openclassroom.project5.service.model.ChildAlertDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChildAlertControllerTest {

    @Mock
    private ChildrenAlertService childrenAlertService;

    @Mock
    private PersonService personService;

    @Mock
    private MedicalRecordsService medicalRecordsService;

    @InjectMocks
    private ChildAlertController childAlertController;

    @Test
    void getChildAlertWhenNoChildrenFound() {
        String address = "123 Main St";
        ChildAlertDto childAlertDto = new ChildAlertDto();
        when(childrenAlertService.getChildAlertByAddress(address)).thenReturn(childAlertDto);

        ResponseEntity<?> responseEntity = childAlertController.getChildAlert(address);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("", responseEntity.getBody());
    }

    @Test
    void getChildAlertWhenChildrenFound() {
        String address = "123 Main St";
        ChildAlertDto childAlertDto = new ChildAlertDto();
        PersonDto child1 = new PersonDto("John", "Doe", address, "City", 12345, "555-555-5555", "john.doe@example.com");
        PersonDto child2 = new PersonDto("Jane", "Doe", address, "City", 12345, "555-555-5555", "jane.doe@example.com");
        childAlertDto.addChild(child1);
        childAlertDto.addChild(child2);

        when(childrenAlertService.getChildAlertByAddress(address)).thenReturn(childAlertDto);

        ResponseEntity<?> responseEntity = childAlertController.getChildAlert(address);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(childAlertDto, responseEntity.getBody());
        verify(childrenAlertService, times(1)).getChildAlertByAddress(address);
    }

}