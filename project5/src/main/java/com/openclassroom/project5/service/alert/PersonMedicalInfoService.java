package com.openclassroom.project5.service.alert;

import com.openclassroom.project5.model.MedicalRecordsDTO;
import com.openclassroom.project5.model.PersonDto;
import com.openclassroom.project5.model.PersonMedicalInfoDto;
import com.openclassroom.project5.service.MedicalRecordsService;
import com.openclassroom.project5.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PersonMedicalInfoService {

    @Autowired
    PersonService personService;

    @Autowired
    MedicalRecordsService medicalRecordsService;

    public List<PersonMedicalInfoDto> getPersonInfoByFullName(String firstName, String lastName) {
        List<PersonDto> personDtoList = personService.returnAllPersons();
        List<PersonMedicalInfoDto> personInfoList = new ArrayList<>();
        List<MedicalRecordsDTO> medicalRecordsDTOList = medicalRecordsService.returnAllMedicalRecords();

        for (PersonDto person : personDtoList) {
            if (person.getLastName().equalsIgnoreCase(lastName)) {
                PersonMedicalInfoDto personInfo = new PersonMedicalInfoDto();
                personInfo.setFirstName(person.getFirstName());
                personInfo.setLastName(person.getLastName());
                personInfo.setAddress(person.getAddress());
                LocalDate birthdate = getMedicalRecords(person.getFirstName(), person.getLastName(), medicalRecordsDTOList).getParsedBirthDate();
                int age = Period.between(birthdate, LocalDate.now()).getYears();
                personInfo.setAge(age);
                personInfo.setEmail(person.getEmail());


                MedicalRecordsDTO medicalRecordsDTO = getMedicalRecords(person.getFirstName(), person.getLastName(), medicalRecordsDTOList);

                if (medicalRecordsDTO != null) {
                    personInfo.setMedications(medicalRecordsDTO.getMedications());
                    personInfo.setAllergies(medicalRecordsDTO.getAllergies());
                }

                personInfoList.add(personInfo);
            }
        }

        return personInfoList;
    }

    private MedicalRecordsDTO getMedicalRecords(String firstName, String lastName, List<MedicalRecordsDTO> medicalRecordsDTOList) {
        return medicalRecordsDTOList.stream()
                .filter(record -> record.getFirstName().equalsIgnoreCase(firstName) &&
                        record.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElse(null);
    }
}
