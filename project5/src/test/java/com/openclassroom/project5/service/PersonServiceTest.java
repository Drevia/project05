package com.openclassroom.project5.service;

import com.openclassroom.project5.model.FireStationDTO;
import com.openclassroom.project5.model.PersonDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private List<PersonDto> personsDTOSbis;

    @InjectMocks
    private PersonServiceImpl personService;


    @Test
    public void testGetPerson_Exists() {
        // Mocking the behavior
        PersonDto personDto1 = new PersonDto("Firstname", "Lastname", "Address 1", "City 1", 123, "phone", "email1");
        PersonDto personDto2 = new PersonDto("Firstname1", "Lastname2", "Address 2", "City 2", 123, "phone", "email2");
        when(personsDTOSbis.stream()).thenReturn(Stream.of(personDto1, personDto2));

        // Invoke the method being tested
        PersonDto result = personService.getPersonByFullName("Firstname", "Lastname");

        // Verify the result
        assertNotNull(result);
        assertEquals("Lastname", result.getLastName());
        assertEquals("Firstname", result.getFirstName());
        assertEquals("Address 1", result.getAddress());
        assertEquals(123, result.getZip());

        // Verify that the mocked method was called
        verify(personsDTOSbis, times(1)).stream();
    }

    @Test
    public void testGetPerson_NotExists() {
        // Mocking the behavior
        when(personsDTOSbis.stream()).thenReturn(Stream.empty());

        // Invoke the method being tested
        PersonDto result = personService.getPersonByFullName("Firstname", "Lastname");

        // Verify the result
        assertNull(result);

        // Verify that the mocked method was called
        verify(personsDTOSbis, times(1)).stream();
    }

    @Test
    public void testDeletePerson_Exists() {
        // Mocking the behavior
        PersonDto personDto1 = new PersonDto("Firstname", "Lastname", "Address 1", "City 1", 123, "phone", "email1");
        // Invoke the method being tested
        personService.deletePerson("Firstname", "Lastname");

        // Verify that the mocked method was called
        assertEquals(0, personsDTOSbis.size());
    }

    @Test
    public void testDeletePerson_NotExists() {
        // Mocking the behavior
        lenient().when(personsDTOSbis.contains(any(PersonDto.class))).thenReturn(false);

        // Invoke the method being tested
        personService.deletePerson("Firstname", "Lastname");

        // Verify that the mocked method was not called
        verify(personsDTOSbis, never()).remove(any(PersonDto.class));
    }

    @Test
    public void testCreatePerson() {
        // Mocking the behavior
        PersonDto personDto1 = new PersonDto("Firstname", "Lastname", "Address 1", "City 1", 123, "phone", "email1");
        when(personsDTOSbis.add(any(PersonDto.class))).thenReturn(true);

        // Invoke the method being tested
        PersonDto result = personService.createPerson(personDto1);

        // Verify the result
        assertNotNull(result);
        assertEquals(123, result.getZip());
        assertEquals("Address 1", result.getAddress());

        // Verify that the mocked method was called
        verify(personsDTOSbis, times(1)).add(personDto1);
    }

   @Test
    public void testUpdatePerson_Exists() {
        // Mocking the behavior
        PersonDto personDto = new PersonDto("Firstname", "Lastname", "Address 1", "City 1", 123, "phone", "email1");
        when(personsDTOSbis.stream()).thenReturn(Stream.of(personDto));

        // Invoke the method being tested
        PersonDto updatedFireStation = new PersonDto(
                "Firstname", "Lastname", "Updated Address", "updated city", 1234, "phone", "email1");
        PersonDto result = personService.updatePerson("Firstname", "Lastname", updatedFireStation);

        // Verify the result
        assertNotNull(result);
        assertEquals(1234, result.getZip());
        assertEquals("Updated Address", result.getAddress());

        // Verify that the mocked method was called
        verify(personsDTOSbis, times(1)).stream();
    }

    @Test
    public void testUpdatePerson_NotExists() {
        // Mocking the behavior
        when(personsDTOSbis.stream()).thenReturn(Stream.empty());

        // Invoke the method being tested
        PersonDto updatedFireStation = new PersonDto(
                "Firstname", "Lastname", "Updated Address ", "updated city", 1234, "phone", "email1");
        PersonDto result = personService.updatePerson("Firstname", "Lastname", updatedFireStation);

        // Verify the result
        assertNull(result);

        // Verify that the mocked method was called
        verify(personsDTOSbis, times(1)).stream();
    }
}
