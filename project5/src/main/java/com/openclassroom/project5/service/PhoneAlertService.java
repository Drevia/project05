package com.openclassroom.project5.service;

import com.openclassroom.project5.model.FireStationDTO;
import com.openclassroom.project5.model.PersonDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PhoneAlertService {
    public List<String> getPhoneNumbersByFireStation(int fireStationNumber, List<PersonDto> people, List<FireStationDTO> fireStations) {

        List<String> phoneNumbers = new ArrayList<>();

        for (FireStationDTO fireStation : fireStations) {
            if (fireStation.getStation() == fireStationNumber) {
                String fireStationAddress = fireStation.getAddress();

                for (PersonDto person : people) {
                    if (person.getAddress().equalsIgnoreCase(fireStationAddress)) {
                        phoneNumbers.add(person.getPhone());
                    }
                }
            }
        }

        return phoneNumbers;
    }
}
