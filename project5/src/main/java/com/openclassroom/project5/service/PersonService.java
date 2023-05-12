package com.openclassroom.project5.service;

import com.openclassroom.project5.model.PersonDto;

public interface PersonService {

    public void deletePerson(String firstName, String lastName);

    public PersonDto createPerson(PersonDto personDto);

    public PersonDto updatePerson(String firstName, String lastName, PersonDto updatePersonDto);

}
