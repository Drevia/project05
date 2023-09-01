package com.openclassroom.project5.service;

import com.openclassroom.project5.data.ReadDataFile;
import com.openclassroom.project5.model.MedicalRecordsDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MedicalRecordsServiceImpl implements MedicalRecordsService{

    private List<MedicalRecordsDTO> medicalRecordsDTOS = new ArrayList<>();

    @PostConstruct
    public void loadData() throws IOException {
        medicalRecordsDTOS = ReadDataFile.loadDataFromFile("medicalrecords", MedicalRecordsDTO.class);
    }

    @Override
    public MedicalRecordsDTO getMedicalRecords(String firstName, String lastName) {
        return medicalRecordsDTOS.stream()
                .filter(fs -> fs.getFirstName().equals(firstName) && fs.getLastName().equals(lastName))
                .findFirst()
                //.orElseThrow(PersonNotFoundException)
                .orElse(null);
    }


    @Override
    public void deleteMedicalRecords(String firstName, String lastName){
        MedicalRecordsDTO fireStationDTO = getMedicalRecords(firstName, lastName);
        if (fireStationDTO != null) {
            medicalRecordsDTOS.remove(fireStationDTO);
        }
    }

    @Override
    public MedicalRecordsDTO createMedicalRecords(MedicalRecordsDTO medicalRecordsDTO){
        medicalRecordsDTOS.add(medicalRecordsDTO);
        return medicalRecordsDTO;
    }

    @Override
    public MedicalRecordsDTO updateMedicalRecords(String firstName, String lastName, MedicalRecordsDTO medicalRecordsDTOUpdated) {
        MedicalRecordsDTO medicalRecordsDTOS = getMedicalRecords(firstName, lastName);
        if (medicalRecordsDTOS != null) {
            medicalRecordsDTOS.setFirstName(medicalRecordsDTOUpdated.getFirstName());
            medicalRecordsDTOS.setLastName(medicalRecordsDTOUpdated.getLastName());
            medicalRecordsDTOS.setBirthdate(medicalRecordsDTOUpdated.getBirthdate());
            medicalRecordsDTOS.setMedications(medicalRecordsDTOUpdated.getMedications());
            medicalRecordsDTOS.setAllergies((medicalRecordsDTOUpdated.getAllergies()));
        } else {
            //Return exception quand une entyté n'est pas trouvé
            //mettre l'exception dans le if et le reste en dehors
            return null;
        }
        return medicalRecordsDTOS;
    }

    @Override
    public List<MedicalRecordsDTO> returnAllMedicalRecords() {
        return medicalRecordsDTOS;
    }
}
