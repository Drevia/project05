package com.openclassroom.project5.service;

import com.openclassroom.project5.model.PersonDto;

import java.util.List;

public interface PersonService {

    List<PersonDto> returnAllPerson();

    public void deletePerson(String firstName, String lastName);

    public PersonDto createPerson(PersonDto personDto);

    public PersonDto updatePerson(String firstName, String lastName, PersonDto updatePersonDto);

    PersonDto getPersonByFullName(String firstName, String lastName);

}
