package com.openclassroom.project5.service.alert;

import com.openclassroom.project5.model.MedicalRecordsDTO;
import com.openclassroom.project5.model.PersonDto;
import com.openclassroom.project5.service.MedicalRecordsService;
import com.openclassroom.project5.service.PersonService;
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
public class ChildrenAlertService {

    @Autowired
    private PersonService personService;

    @Autowired
    private MedicalRecordsService medicalRecordsService;

    public ChildAlertDto getChildAlertByAddress(String address) {
        //TODO: Retourner l'age de l'enfant
        List<PersonDto> childs = personService.returnAllPersons();
        List<MedicalRecordsDTO> medicalRecordsDTOS = medicalRecordsService.returnAllMedicalRecords();
        ChildAlertDto childAlertDto = new ChildAlertDto();

        for (PersonDto person : childs) {
            if (person.getAddress().equalsIgnoreCase(address)) {
                MedicalRecordsDTO medicalRecordsDTO = findMedicalRecords(person, medicalRecordsDTOS);
                if (medicalRecordsDTO != null && medicalRecordsDTO.getAge() <= 18) {
                    childAlertDto.addChild(person);
                }
            }

        }
        return childAlertDto;
    }

    private MedicalRecordsDTO findMedicalRecords(PersonDto personDto, List<MedicalRecordsDTO> medicalRecordsDTOS) {
        for (MedicalRecordsDTO medicalRecord : medicalRecordsDTOS) {
            if (medicalRecord.getFirstName().equalsIgnoreCase(personDto.getFirstName())
                    && medicalRecord.getLastName().equalsIgnoreCase(personDto.getLastName())) {
                return medicalRecord;
            }
        }
        return null;
    }
}
