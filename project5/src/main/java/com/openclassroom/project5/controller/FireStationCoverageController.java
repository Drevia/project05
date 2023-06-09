package com.openclassroom.project5.controller;

import com.openclassroom.project5.service.alert.FireStationServiceCoverage;
import com.openclassroom.project5.service.model.FireStationCoverageDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FireStationCoverageController {

    private final Logger logger = LogManager.getLogger(FireStationCoverageController.class);
    @Autowired
    FireStationServiceCoverage fireStationServiceCoverage;

    @GetMapping("/firestation")
    public ResponseEntity<FireStationCoverageDTO> getFireStationCoverage(@RequestParam("stationNumber") int stationNumber) {
        FireStationCoverageDTO fireStationCovered = fireStationServiceCoverage.getFireStationCoverage(stationNumber);
        if (fireStationCovered != null) {
            logger.info(fireStationCovered);
            return ResponseEntity.ok(fireStationCovered);
        } else {
            logger.info("No one is covered by fireStations number " + stationNumber);
            return ResponseEntity.notFound().build();
        }
    }
}
