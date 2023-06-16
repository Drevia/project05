package com.openclassroom.project5.controller;

import com.openclassroom.project5.model.FireStationDTO;
import com.openclassroom.project5.model.MedicalRecordsDTO;
import com.openclassroom.project5.model.PersonDto;
import com.openclassroom.project5.service.FireStationService;
import com.openclassroom.project5.service.MedicalRecordsService;
import com.openclassroom.project5.service.PersonService;
import com.openclassroom.project5.service.alert.FireInformationService;
import com.openclassroom.project5.service.model.FireInformationDto;
import com.openclassroom.project5.service.model.PersonInfoDto;
import com.openclassroom.project5.service.model.Residents;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FireInformationControllerTest {

    @Mock
    private FireInformationService fireInformationService;

    @InjectMocks
    private FireInformationController fireInformationController;

    @Test
    void getFireInformationWhenAddressIsNotValidThenReturnNotFound() {
        String address = "invalid address";
        when(fireInformationService.getFireInformation(address)).thenReturn(null);

        ResponseEntity<?> response = fireInformationController.getFireInformation(address);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(fireInformationService, times(1)).getFireInformation(address);
    }

}