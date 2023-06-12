package com.openclassroom.project5.service.alert;

import com.openclassroom.project5.model.FireStationDTO;
import com.openclassroom.project5.model.MedicalRecordsDTO;
import com.openclassroom.project5.model.PersonDto;
import com.openclassroom.project5.service.FireStationService;
import com.openclassroom.project5.service.MedicalRecordsService;
import com.openclassroom.project5.service.PersonService;
import com.openclassroom.project5.service.model.FloodStationDto;
import com.openclassroom.project5.service.model.Residents;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class FloodStationAlertService {

    private final Logger logger = LogManager.getLogger(FireStationServiceCoverage.class);


    @Autowired
    private FireStationService fireStationService;

    @Autowired
    private PersonService personService;

    @Autowired
    private MedicalRecordsService medicalRecordsService;


    public List<FloodStationDto> getHouseholdsByStations(List<Integer> stationNumbers) {
        List<FireStationDTO> fireStationDTOList = fireStationService.returnAllFireStation();
        List<PersonDto> personDtos = personService.returnAllPersons();
        List<FloodStationDto> households = new ArrayList<>();
        List<String> coveredAddresses = new ArrayList<>();

        for (Integer stationNumber : stationNumbers) {
            coveredAddresses = getCoveredAddresses(stationNumber, fireStationDTOList);
        }

        if (!coveredAddresses.isEmpty()) {
            for (String address : coveredAddresses) {
                List<PersonDto> persons = getPersonsByAddress(address, personDtos);
                FloodStationDto householdInfo = createHouseholdInfo(address, persons);
                households.add(householdInfo);
            }
        }

        return households;
    }

    private List<String> getCoveredAddresses(Integer stationNumber, List<FireStationDTO> fireStationDtoList) {
        List<String> coveredAddresses = new ArrayList<>();

        for (FireStationDTO fireStationDto : fireStationDtoList) {
            if (fireStationDto.getStation().equals(stationNumber)) {
                coveredAddresses.add(fireStationDto.getAddress());
            }
        }

        return coveredAddresses;
    }

    private List<PersonDto> getPersonsByAddress(String address, List<PersonDto> personDtoList) {
        List<PersonDto> persons = new ArrayList<>();

        for (PersonDto personDto : personDtoList) {
            if (personDto.getAddress().equalsIgnoreCase(address)) {
                persons.add(personDto);
            }
        }

        return persons;
    }

    private FloodStationDto createHouseholdInfo(String address, List<PersonDto> persons) {
        FloodStationDto floodStationDto = new FloodStationDto();
        List<Residents> residentsList = new ArrayList<>();
        floodStationDto.setResidents(residentsList);
        List<MedicalRecordsDTO> medicalRecordsDTOS = medicalRecordsService.returnAllMedicalRecords();

        for (PersonDto personDto : persons) {
            Residents personInfo = new Residents();
            personInfo.setFirstName(personDto.getFirstName());
            personInfo.setLastName(personDto.getLastName());
            personInfo.setPhoneNumber(personDto.getPhone());

            MedicalRecordsDTO medicalRecordDto = getMedicalRecord(personDto, medicalRecordsDTOS);
            personInfo.setMedications(List.of(medicalRecordDto.getMedications()));
            personInfo.setAllergies(List.of(medicalRecordDto.getAllergies()));
            personInfo.setAge(medicalRecordDto.getAge().toString());

            residentsList.add(personInfo);
        }
        floodStationDto.setStationAddress(address);
        return floodStationDto;
    }

    private MedicalRecordsDTO getMedicalRecord(PersonDto personDto, List<MedicalRecordsDTO> medicalRecordDtoList) {
        for (MedicalRecordsDTO medicalRecordDto : medicalRecordDtoList) {
            if (medicalRecordDto.getFirstName().equalsIgnoreCase(personDto.getFirstName())
                    && medicalRecordDto.getLastName().equalsIgnoreCase(personDto.getLastName())) {
                return medicalRecordDto;
            }
        }
        return null;
    }

}
