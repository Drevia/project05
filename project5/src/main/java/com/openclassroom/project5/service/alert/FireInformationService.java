package com.openclassroom.project5.service.alert;

import com.openclassroom.project5.model.FireStationDTO;
import com.openclassroom.project5.model.MedicalRecordsDTO;
import com.openclassroom.project5.model.PersonDto;
import com.openclassroom.project5.service.FireStationService;
import com.openclassroom.project5.service.MedicalRecordsService;
import com.openclassroom.project5.service.PersonService;
import com.openclassroom.project5.service.model.FireInformationDto;
import com.openclassroom.project5.service.model.PersonInfoDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FireInformationService {

    @Autowired
    private final PersonService personService;

    @Autowired
    private final MedicalRecordsService medicalRecordsService;

    @Autowired
    private final FireStationService fireStationService;

    public FireInformationDto getFireInformation(String address) {
        List<FireStationDTO> fireStationDTOList = fireStationService.returnAllFireStation();
        List<PersonDto> personDtos = personService.returnAllPersons();
        List<MedicalRecordsDTO> medicalRecordsDTOList = medicalRecordsService.returnAllMedicalRecords();

        FireStationDTO fireStationDTO = getFireStationByAddress(address, fireStationDTOList);
        List<PersonDto> residents = getResidentsByAddress(address, personDtos);
        if (fireStationDTO == null || residents == null) {
            return null;
        }

        List<PersonInfoDto> residentInfoList = new ArrayList<>();
        for (PersonDto resident : residents) {
            PersonInfoDto personInfo = new PersonInfoDto();
            personInfo.setFirstName(resident.getFirstName());
            personInfo.setLastName(resident.getLastName());
            personInfo.setPhoneNumber(resident.getPhone());
            personInfo.setMedicalRecords(getMedicalRecords(resident.getFirstName(), resident.getLastName(), medicalRecordsDTOList));
            MedicalRecordsDTO medicalRecordsDTO = getMedicalRecords(resident.getFirstName(), resident.getLastName(), medicalRecordsDTOList);
            personInfo.setAge(medicalRecordsDTO.getAge());
            residentInfoList.add(personInfo);
        }
        FireInformationDto fireInfo = new FireInformationDto();
        fireInfo.setResidents(residentInfoList);
        fireInfo.setFireStationNumber(fireStationDTO.getStation());

        return fireInfo;
    }

    private FireStationDTO getFireStationByAddress(String address, List<FireStationDTO> fireStationDTOList) {
        return fireStationDTOList.stream()
                .filter(fireStationDTO -> fireStationDTO.getAddress().equalsIgnoreCase(address))
                .findFirst()
                .orElse(null);
    }

    private List<PersonDto> getResidentsByAddress(String address, List<PersonDto> residents) {
        return residents.stream()
                .filter(person -> person.getAddress().equalsIgnoreCase(address))
                .collect(Collectors.toList());
    }

    private MedicalRecordsDTO getMedicalRecords(String firstName, String lastName, List<MedicalRecordsDTO> medicalRecordsDTOList) {
        return medicalRecordsDTOList.stream()
                .filter(record -> record.getFirstName().equalsIgnoreCase(firstName) &&
                        record.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElse(null);
    }
}
