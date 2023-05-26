package com.openclassroom.project5.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassroom.project5.data.ReadDataFile;
import com.openclassroom.project5.model.FireStationDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
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
public class FireStationServiceImpl implements FireStationService {

    private List<FireStationDTO> fireStationDTOS = new ArrayList<>();

    private ObjectMapper objectMapper = new ObjectMapper();

/*    @PostConstruct
    public void loadDataFromFile() throws IOException {
        File file = new File(getClass().getClassLoader().getResource("data.json").getFile());

        JsonNode jsonNode = objectMapper.readTree(file);

        JsonNode personsNode = jsonNode.get("firestations");
        if (personsNode != null) {
            fireStationDTOS = objectMapper.readValue(personsNode.traverse(), new TypeReference<List<FireStationDTO>>() {});
        }
    }*/

    @PostConstruct
    public void loadData() throws IOException {
        fireStationDTOS = ReadDataFile.loadDataFromFile("firestations", FireStationDTO.class);
    }

    public FireStationDTO getFireStation(Integer station, String address) {
        return fireStationDTOS.stream()
                .filter(fs -> fs.getStation().equals(station) && fs.getAddress().equals(address))
                .findFirst()
                .orElse(null);
    }


    @Override
    public void deleteFireStation(Integer station, String address){
        FireStationDTO fireStationDTO = getFireStation(station, address);
        if (fireStationDTO != null) {
            fireStationDTOS.remove(fireStationDTO);
        }
    }

    @Override
    public FireStationDTO createFireStation(FireStationDTO fireStationDTO){
        fireStationDTOS.add(fireStationDTO);
        return fireStationDTO;
    }

    @Override
    public FireStationDTO updateFireStation(Integer station, String address, FireStationDTO fireStationDtoUpdated) {
        FireStationDTO fireStation = getFireStation(station, address);
        if (fireStation != null) {
            fireStation.setStation(fireStationDtoUpdated.getStation());
            fireStation.setAddress(fireStationDtoUpdated.getAddress());
        } else {
            //Return exception quand une entyté n'est pas trouvé
            //mettre l'exception dans le if et le reste en dehors
            return null;
        }
        return fireStation;
    }
}
