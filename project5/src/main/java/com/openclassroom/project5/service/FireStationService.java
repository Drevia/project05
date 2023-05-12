package com.openclassroom.project5.service;

import com.openclassroom.project5.model.FireStationDTO;

public interface FireStationService {

    void deleteFireStation(Integer station, String address);

    FireStationDTO createFireStation(FireStationDTO fireStationDTO);

    FireStationDTO updateFireStation(Integer station, String address, FireStationDTO fireStationDtoUpdated);
}
