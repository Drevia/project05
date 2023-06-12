package com.openclassroom.project5.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FloodStationDto {

    private String stationAddress;
    private List<Residents> residents;
}
