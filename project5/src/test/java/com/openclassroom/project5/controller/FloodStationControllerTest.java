package com.openclassroom.project5.controller;

import com.openclassroom.project5.service.alert.FloodStationAlertService;
import com.openclassroom.project5.service.model.FloodStationDto;
import com.openclassroom.project5.service.model.Residents;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FloodStationControllerTest {

    @InjectMocks
    FloodStationAlertController floodStationController;

    @Mock
    FloodStationAlertService floodStationAlertService;

    @Test
    public void getResidentsByStationFound() {
        List<FloodStationDto> floodStationDtos = new ArrayList<>();
        Residents residents = new Residents();
        List<Integer> ints = new ArrayList<>();
        ints.add(1);
        ints.add(2);

        FloodStationDto dto = new FloodStationDto("address", List.of(residents));
        floodStationDtos.add(dto);

        when(floodStationAlertService.getHouseholdsByStations(ints)).thenReturn(floodStationDtos);

        ResponseEntity<List<FloodStationDto>> response = floodStationController.getResidentsByStations(ints);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
