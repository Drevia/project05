package com.openclassroom.project5.service;

import com.openclassroom.project5.model.MedicalRecordsDTO;
import com.openclassroom.project5.model.PersonDto;
import com.openclassroom.project5.service.model.ChildAlertDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ChildAlertService {

    @Autowired
    PersonService personService;

    @Autowired
    MedicalRecordsService medicalRecordsService;

    List<MedicalRecordsDTO> medicalRecordsDTOS;

    public ChildAlertDto getChildAlertByAddress(String address) {
        List<PersonDto> childs = personService.returnAllPersons();
        medicalRecordsDTOS = medicalRecordsService.returnAllMedicalRecords();
        ChildAlertDto childAlertDto = new ChildAlertDto();

        for (PersonDto person : childs) {
            if (person.getAddress().equalsIgnoreCase(address)) {
                MedicalRecordsDTO medicalRecordsDTO = findMedicalRecords(person);
                if (medicalRecordsDTO != null) {
                    LocalDate birthdate = medicalRecordsDTO.getParsedBirthDate();
                    int age = Period.between(birthdate, LocalDate.now()).getYears();
                    if (age <= 18){
                        childAlertDto.addChild(person);
                    }
                }
            }

        }
        return childAlertDto;
    }

    private MedicalRecordsDTO findMedicalRecords(PersonDto personDto) {
        for (MedicalRecordsDTO medicalRecord : medicalRecordsDTOS) {
            if (medicalRecord.getFirstName().equalsIgnoreCase(personDto.getFirstName())
                    && medicalRecord.getLastName().equalsIgnoreCase(personDto.getLastName())) {
                return medicalRecord;
            }
        }
        return null;
    }
}
