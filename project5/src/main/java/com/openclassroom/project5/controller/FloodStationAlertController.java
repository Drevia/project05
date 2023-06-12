package com.openclassroom.project5.controller;

import com.openclassroom.project5.service.alert.FloodStationAlertService;
import com.openclassroom.project5.service.model.FloodStationDto;
import com.openclassroom.project5.service.model.Residents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FloodStationAlertController {

    @Autowired
    private FloodStationAlertService floodStationAlertService;

    @GetMapping("/flood/stations")
    public ResponseEntity<List<FloodStationDto>> getResidentsByStations(@RequestParam("stations") List<Integer> stationNumbers) {
        List<FloodStationDto> floodStationDtos = floodStationAlertService.getHouseholdsByStations(stationNumbers);

        if (floodStationDtos.isEmpty() || floodStationDtos == null) {
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.ok(floodStationDtos);
        }
    }
}
