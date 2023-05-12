package com.openclassroom.project5.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassroom.project5.model.MedicalRecordsDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecordsServiceImpl implements MedicalRecordsService{

    private List<MedicalRecordsDTO> medicalRecordsDTOS = new ArrayList<>();

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void loadDataFromFile() throws IOException {
        File file = new File(getClass().getClassLoader().getResource("data.json").getFile());

        JsonNode jsonNode = objectMapper.readTree(file);

        JsonNode personsNode = jsonNode.get("medicalrecords");
        if (personsNode != null) {
            medicalRecordsDTOS = objectMapper.readValue(personsNode.traverse(), new TypeReference<List<MedicalRecordsDTO>>() {});
        }
    }

    public MedicalRecordsDTO getMedicalRecords(String firstName, String lastName) {
        return medicalRecordsDTOS.stream()
                .filter(fs -> fs.getFirstName().equals(firstName) && fs.getLastName().equals(lastName))
                .findFirst()
                //.orElseThrow(PersonNotFoundException)
                .orElse(null);
    }


    public void deleteMedicalRecords(String firstName, String lastName){
        MedicalRecordsDTO fireStationDTO = getMedicalRecords(firstName, lastName);
        if (fireStationDTO != null) {
            medicalRecordsDTOS.remove(fireStationDTO);
        }
    }

    public MedicalRecordsDTO createMedicalRecords(MedicalRecordsDTO medicalRecordsDTO){
        medicalRecordsDTOS.add(medicalRecordsDTO);
        return medicalRecordsDTO;
    }

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
}
