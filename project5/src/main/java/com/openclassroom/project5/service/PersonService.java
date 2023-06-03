package com.openclassroom.project5.service;

import com.openclassroom.project5.model.PersonDto;

import java.util.List;

public interface PersonService {

    List<PersonDto> returnAllPersons();

    void deletePerson(String firstName, String lastName);

    PersonDto createPerson(PersonDto personDto);

    PersonDto updatePerson(String firstName, String lastName, PersonDto updatePersonDto);

    PersonDto getPersonByFullName(String firstName, String lastName);

    List<String> getPersonsEmailByCity(String cityName);
}
