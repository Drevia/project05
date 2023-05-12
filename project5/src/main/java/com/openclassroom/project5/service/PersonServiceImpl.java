package com.openclassroom.project5.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassroom.project5.model.PersonDto;
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
public class PersonServiceImpl implements PersonService {

    private List<PersonDto> personDtoList = new ArrayList<>();


    private ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void loadDataFromFile() throws IOException {
        File file = new File(getClass().getClassLoader().getResource("data.json").getFile());

        JsonNode jsonNode = objectMapper.readTree(file);

        JsonNode personsNode = jsonNode.get("persons");
        if (personsNode != null) {
            personDtoList = objectMapper.readValue(personsNode.traverse(), new TypeReference<List<PersonDto>>() {});
        }
    }

    public PersonDto getPersonByFullName(String firstName, String lastName) {
        return personDtoList.stream()
                .filter(p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName))
                .findFirst()
                //.orElseThrow(PersonNotFoundException)
                .orElse(null);
    }

    @Override
    public void deletePerson(String firstName, String lastName){
        PersonDto personDto = getPersonByFullName(firstName, lastName);
        if (personDto != null) {
            personDtoList.remove(personDto);
        }
    }

    @Override
    public PersonDto createPerson(PersonDto personDto){
        personDtoList.add(personDto);
        return personDto;
    }

    @Override
    public PersonDto updatePerson(String firstName, String lastName, PersonDto updatePersonDto) {
        PersonDto personDto = getPersonByFullName(firstName, lastName);
        if (personDto != null) {
            personDto.setCity(updatePersonDto.getCity());
            personDto.setPhone(updatePersonDto.getPhone());
            personDto.setAddress(updatePersonDto.getAddress());
            personDto.setZip(updatePersonDto.getZip());
        } else {
            //Return exception quand une entyté n'est pas trouvé
            //mettre l'exception dans le if et le reste en dehors
            return null;
        }
        return personDto;
    }



}
