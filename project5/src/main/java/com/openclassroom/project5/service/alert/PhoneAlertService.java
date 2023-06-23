package com.openclassroom.project5.service.alert;

import com.openclassroom.project5.model.FireStationDTO;
import com.openclassroom.project5.model.PersonDto;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PhoneAlertService {
    public List<String> getPhoneNumbersByFireStation(int fireStationNumber, List<PersonDto> people, List<FireStationDTO> fireStations) {
        Set<String> phoneNumbers = new HashSet<>();

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

        return phoneNumbers.stream().toList();
    }
}
