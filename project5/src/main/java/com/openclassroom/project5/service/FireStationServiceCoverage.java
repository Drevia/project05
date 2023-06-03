package com.openclassroom.project5.service;

import com.openclassroom.project5.controller.ChildAlertController;
import com.openclassroom.project5.model.FireStationDTO;
import com.openclassroom.project5.model.MedicalRecordsDTO;
import com.openclassroom.project5.model.PersonDto;
import com.openclassroom.project5.service.model.FireStationCoverageDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class FireStationServiceCoverage {

    private final Logger logger = LogManager.getLogger(FireStationServiceCoverage.class);

    private List<FireStationDTO> fireStationDTOList;

    private List<PersonDto> personDtoList;

    private List<MedicalRecordsDTO> medicalRecordsDTOList;

    @Autowired
    FireStationService fireStationService;

    @Autowired
    PersonService personService;

    @Autowired
    MedicalRecordsService medicalRecordsService;

    public FireStationCoverageDTO getFireStationCoverage(int stationNumber) {
        //possibilité de decaler l'appel du service dans le for a la place de DTOList ?
        fireStationDTOList = fireStationService.returnAllFireStation();
        personDtoList = personService.returnAllPersons();
        medicalRecordsDTOList = medicalRecordsService.returnAllMedicalRecords();

        List<PersonDto> coveredPeople = new ArrayList<>();
        int adultCount = 0;
        int childCount = 0;

        for (FireStationDTO fireStationDTO : fireStationDTOList) {
            if (fireStationDTO.getStation().equals(stationNumber)) {
                String fireStationAddress = fireStationDTO.getAddress();

                //parcours des personnes pour trouver celles couvertes par la caserne
                for (PersonDto person : personDtoList) {
                    if (person.getAddress().equalsIgnoreCase(fireStationAddress)) {
                        coveredPeople.add(person);

                        //recherche du dossier médical correspondant a la personne
                        MedicalRecordsDTO medicalRecord = findMedicalRecords(person);

                        // Vérifiez si la personne est un adulte ou un enfant
                        LocalDate birthdate = medicalRecord.getParsedBirthDate();
                        int age = Period.between(birthdate, LocalDate.now()).getYears();
                        if (age <= 18) {
                            childCount++;
                        } else {
                            adultCount++;
                        }
                    }
                }
            }
        }
        FireStationCoverageDTO coverageDTO = new FireStationCoverageDTO();
        coverageDTO.setPeople(coveredPeople);
        coverageDTO.setAdultCount(adultCount);
        coverageDTO.setChildCount(childCount);

        if (coverageDTO.getPeople() != null) {

            return coverageDTO;
        }
        else {
            logger.error("CoverageDto is null, make sure the stationNumber is correct and someone is covered by it");
            return null;
        }
    }

    private MedicalRecordsDTO findMedicalRecords(PersonDto personDto) {
        for (MedicalRecordsDTO medicalRecord : medicalRecordsDTOList) {
            if (medicalRecord.getFirstName().equalsIgnoreCase(personDto.getFirstName())
                    && medicalRecord.getLastName().equalsIgnoreCase(personDto.getLastName())) {
                return medicalRecord;
            }
        }
        return null;
    }


}
