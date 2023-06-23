package com.openclassroom.project5.service.alert;

import com.openclassroom.project5.model.FireStationDTO;
import com.openclassroom.project5.model.PersonDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PhoneAlertServiceTest {

    @InjectMocks
    private PhoneAlertService phoneAlertService;

    @Test
    void getPhoneNumbersByFireStationWhenNoMatchingPeople() {
        List<PersonDto> people = new ArrayList<>();
        List<FireStationDTO> fireStations = new ArrayList<>();
        int fireStationNumber = 1;

        List<String> phoneNumbers = phoneAlertService.getPhoneNumbersByFireStation(fireStationNumber, people, fireStations);

        assertThat(phoneNumbers).isEmpty();
    }

    @Test
    void getPhoneNumbersByFireStationWhenNoMatchingFireStations() {
        List<PersonDto> people = new ArrayList<>();
        List<FireStationDTO> fireStations = new ArrayList<>();
        int fireStationNumber = 1;

        List<String> phoneNumbers = phoneAlertService.getPhoneNumbersByFireStation(fireStationNumber, people, fireStations);

        assertThat(phoneNumbers).isEmpty();
    }

    @Test
    void getPhoneNumbersByFireStationWhenMultiplePeopleAtFireStationAddress() {
        List<PersonDto> people = new ArrayList<>();
        PersonDto person1 = new PersonDto("John", "Doe", "123 Main St", "Springfield", 12345, "555-1234", "john.doe@example.com");
        PersonDto person2 = new PersonDto("Jane", "Doe", "123 Main St", "Springfield", 12345, "555-5678", "jane.doe@example.com");
        PersonDto person3 = new PersonDto("Bob", "Smith", "456 Oak St", "Springfield", 12345, "555-4321", "bob.smith@example.com");
        people.add(person1);
        people.add(person2);
        people.add(person3);

        List<FireStationDTO> fireStations = new ArrayList<>();
        FireStationDTO fireStation1 = new FireStationDTO("123 Main St", 1);
        FireStationDTO fireStation2 = new FireStationDTO("456 Oak St", 2);
        fireStations.add(fireStation1);
        fireStations.add(fireStation2);

        int fireStationNumber = 1;

        List<String> phoneNumbers = phoneAlertService.getPhoneNumbersByFireStation(fireStationNumber, people, fireStations);

        assertThat(phoneNumbers).containsExactlyInAnyOrder("555-1234", "555-5678");
    }

    @Test
    void getPhoneNumbersByFireStationWhenPeopleMatchFireStationAddress() {
        List<PersonDto> people = new ArrayList<>();
        PersonDto person1 = new PersonDto("John", "Doe", "123 Main St", "New York", 10001, "123-456-7890", "john.doe@example.com");
        PersonDto person2 = new PersonDto("Jane", "Doe", "123 Main St", "New York", 10001, "987-654-3210", "jane.doe@example.com");
        PersonDto person3 = new PersonDto("Bob", "Smith", "456 Park Ave", "New York", 10002, "555-555-5555", "bob.smith@example.com");
        people.add(person1);
        people.add(person2);
        people.add(person3);

        List<FireStationDTO> fireStations = new ArrayList<>();
        FireStationDTO fireStation1 = new FireStationDTO("123 Main St", 1);
        FireStationDTO fireStation2 = new FireStationDTO("456 Park Ave", 2);
        fireStations.add(fireStation1);
        fireStations.add(fireStation2);

        int fireStationNumber = 1;

        List<String> phoneNumbers = phoneAlertService.getPhoneNumbersByFireStation(fireStationNumber, people, fireStations);

        assertThat(phoneNumbers).contains("123-456-7890", "987-654-3210");
    }

}