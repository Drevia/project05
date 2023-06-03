package com.openclassroom.project5.controller;

import com.openclassroom.project5.data.ReadDataFile;
import com.openclassroom.project5.model.PersonDto;
import com.openclassroom.project5.service.PersonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SafetyNetPersonServiceTest {

    @InjectMocks
    PersonServiceImpl personService;

    @Mock
    ReadDataFile readDataFile;

    private List<PersonDto> personDtoList;

    @BeforeEach
    void setUp() {
        personService = new PersonServiceImpl();
        personDtoList = new ArrayList<>();

        personDtoList.add(new PersonDto("Name1", "lastName1", "address", "city", 1234, "xxx", "email1"));
        personDtoList.add(new PersonDto("Name2", "lastName2", "address", "city", 1234, "xxx", "email2"));
        personDtoList.add(new PersonDto("Name3", "lastName3", "address", "city", 1234, "xxx", "email3"));
    }
    @Test
    void testDeletePerson_ExistingPerson_Success() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";

        // Act
        personService.deletePerson(firstName, lastName);

        // Assert
        assertNull(personService.getPersonByFullName(firstName, lastName));
    }

    @Test
    void testCreatePerson_Success() {
        PersonDto newPerson = new PersonDto("newName", "newLastName", "newAddress", "city", 1234, "xxx", "newEmail");

        PersonDto createdPerson = personService.createPerson(newPerson);

        assertEquals(newPerson.getLastName(), createdPerson.getLastName());
    }

    @Test
    void testUpdatePerson_ExistingPerson_Success() {
        // Arrange
        String firstName = "OldFirstName";
        String lastName = "OldLastName";
        PersonDto updatePersonDto = new PersonDto("UpdatedCity", "UpdatedPhone", "UpdatedAddress", "city", 1234, "xxx", "mail");

        // Act
        PersonDto updatedPerson = personService.updatePerson(firstName, lastName, updatePersonDto);

        // Assert
        assertNotNull(updatedPerson);
        assertEquals(updatePersonDto.getCity(), updatedPerson.getCity());
        assertEquals(updatePersonDto.getPhone(), updatedPerson.getPhone());
        assertEquals(updatePersonDto.getAddress(), updatedPerson.getAddress());
        assertEquals(updatePersonDto.getZip(), updatedPerson.getZip());
    }

}
