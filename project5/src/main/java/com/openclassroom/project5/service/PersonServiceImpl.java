package com.openclassroom.project5.service;

import com.openclassroom.project5.data.ReadDataFile;
import com.openclassroom.project5.model.PersonDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class PersonServiceImpl implements PersonService {

    private List<PersonDto> personDtoList = new ArrayList<>();

    @PostConstruct
    public void loadData() throws IOException {
        personDtoList = ReadDataFile.loadDataFromFile("persons", PersonDto.class);
    }

    public PersonDto getPersonByFullName(String firstName, String lastName) {
        return personDtoList.stream()
                .filter(p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName))
                .findFirst()
                //.orElseThrow(PersonNotFoundException)
                .orElse(null);
    }

    @Override
    public List<PersonDto> returnAllPerson() {
        return personDtoList;
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
