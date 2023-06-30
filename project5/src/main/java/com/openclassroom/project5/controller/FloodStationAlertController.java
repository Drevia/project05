package com.openclassroom.project5.controller;

import com.openclassroom.project5.service.alert.FloodStationAlertService;
import com.openclassroom.project5.service.model.FloodStationDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FloodStationAlertController {

    private final Logger logger = LogManager.getLogger(FloodStationAlertController.class);

    @Autowired
    private FloodStationAlertService floodStationAlertService;

    @GetMapping("/flood/stations")
    public ResponseEntity<List<FloodStationDto>> getResidentsByStations(@RequestParam("stations") List<Integer> stationNumbers) {
        logger.info("Searching resident living near stations number " + stationNumbers);
        List<FloodStationDto> floodStationDtos = floodStationAlertService.getHouseholdsByStations(stationNumbers);

        if (floodStationDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        else {
            logger.warn("No residents found");
            return ResponseEntity.ok(floodStationDtos);
        }
    }
}
