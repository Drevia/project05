package com.openclassroom.project5.service;

import com.openclassroom.project5.model.FireStationDTO;

import java.util.List;
import java.util.Map;

public interface FireStationService {

    List<FireStationDTO> returnAllFireStation();

    void deleteFireStation(Integer station, String address);

    FireStationDTO createFireStation(FireStationDTO fireStationDTO);

    FireStationDTO updateFireStation(Integer station, String address, FireStationDTO fireStationDtoUpdated);
}
