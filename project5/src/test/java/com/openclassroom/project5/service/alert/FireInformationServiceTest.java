package com.openclassroom.project5.service.alert;

import com.openclassroom.project5.model.FireStationDTO;
import com.openclassroom.project5.model.MedicalRecordsDTO;
import com.openclassroom.project5.model.PersonDto;
import com.openclassroom.project5.service.FireStationService;
import com.openclassroom.project5.service.MedicalRecordsService;
import com.openclassroom.project5.service.PersonService;
import com.openclassroom.project5.service.model.FireInformationDto;
import com.openclassroom.project5.service.model.PersonInfoDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FireInformationServiceTest {

    @Mock
    private PersonService personService;

    @Mock
    private MedicalRecordsService medicalRecordsService;

    @Mock
    private FireStationService fireStationService;

    @InjectMocks
    private FireInformationService fireInformationService;

    @Test
    void getFireInformationWhenAddressIsValid() {
        String address = "123 Main St";
        List<PersonDto> personDtos = new ArrayList<>();
        personDtos.add(new PersonDto("John", "Doe", address, "City", 12345, "123-456-7890", "john.doe@example.com"));
        personDtos.add(new PersonDto("Jane", "Doe", address, "City", 12345, "123-456-7890", "jane.doe@example.com"));
        when(personService.returnAllPersons()).thenReturn(personDtos);

        List<MedicalRecordsDTO> medicalRecordsDTOList = new ArrayList<>();
        medicalRecordsDTOList.add(new MedicalRecordsDTO("John", "Doe", "01/01/1990", new String[]{"med1", "med2"}, new String[]{"allergy1", "allergy2"}));
        medicalRecordsDTOList.add(new MedicalRecordsDTO("Jane", "Doe", "01/01/1995", new String[]{"med3", "med4"}, new String[]{"allergy3", "allergy4"}));
        when(medicalRecordsService.returnAllMedicalRecords()).thenReturn(medicalRecordsDTOList);

        List<FireStationDTO> fireStationDTOList = new ArrayList<>();
        fireStationDTOList.add(new FireStationDTO(address, 1));
        when(fireStationService.returnAllFireStation()).thenReturn(fireStationDTOList);

        FireInformationDto fireInformationDto = fireInformationService.getFireInformation(address);

        assertNotNull(fireInformationDto);
        assertEquals(1, fireInformationDto.getFireStationNumber());
        assertEquals(2, fireInformationDto.getResidents().size());
        assertEquals("John", fireInformationDto.getResidents().get(0).getFirstName());
        assertEquals("Doe", fireInformationDto.getResidents().get(0).getLastName());
        assertEquals("123-456-7890", fireInformationDto.getResidents().get(0).getPhoneNumber());
        assertEquals(33, fireInformationDto.getResidents().get(0).getAge());
        assertEquals("Jane", fireInformationDto.getResidents().get(1).getFirstName());
        assertEquals("Doe", fireInformationDto.getResidents().get(1).getLastName());
        assertEquals("123-456-7890", fireInformationDto.getResidents().get(1).getPhoneNumber());
        assertEquals(28, fireInformationDto.getResidents().get(1).getAge());
    }

    @Test
    void getFireInformationWithCorrectResidentsAndFireStationNumber() {// Create test data
        String address = "123 Main St";
        List<PersonDto> residents = new ArrayList<>();
        residents.add(new PersonDto("John", "Doe", address, "City", 12345, "555-555-5555", "john.doe@example.com"));
        residents.add(new PersonDto("Jane", "Doe", address, "City", 12345, "555-555-5555", "jane.doe@example.com"));
        residents.add(new PersonDto("Bob", "Smith", address, "City", 12345, "555-555-5555", "bob.smith@example.com"));
        List<MedicalRecordsDTO> medicalRecords = new ArrayList<>();
        medicalRecords.add(new MedicalRecordsDTO("John", "Doe", "01/01/1980", new String[]{"medication1", "medication2"}, new String[]{"allergy1", "allergy2"}));
        medicalRecords.add(new MedicalRecordsDTO("Jane", "Doe", "01/01/1985", new String[]{"medication3", "medication4"}, new String[]{"allergy3", "allergy4"}));
        medicalRecords.add(new MedicalRecordsDTO("Bob", "Smith", "01/01/1990", new String[]{"medication5", "medication6"}, new String[]{"allergy5", "allergy6"}));
        List<FireStationDTO> fireStations = new ArrayList<>();
        fireStations.add(new FireStationDTO(address, 1));

        when(personService.returnAllPersons()).thenReturn(residents);
        when(medicalRecordsService.returnAllMedicalRecords()).thenReturn(medicalRecords);
        when(fireStationService.returnAllFireStation()).thenReturn(fireStations);

        FireInformationDto fireInformationDto = fireInformationService.getFireInformation(address);

        assertNotNull(fireInformationDto);
        assertEquals(1, fireInformationDto.getFireStationNumber());
        assertEquals(3, fireInformationDto.getResidents().size());
        assertEquals("John", fireInformationDto.getResidents().get(0).getFirstName());
        assertEquals("Doe", fireInformationDto.getResidents().get(0).getLastName());
        assertEquals("555-555-5555", fireInformationDto.getResidents().get(0).getPhoneNumber());
        assertEquals(43, fireInformationDto.getResidents().get(0).getAge());
        assertNotNull(fireInformationDto.getResidents().get(0).getMedicalRecords());
        assertEquals("Jane", fireInformationDto.getResidents().get(1).getFirstName());
        assertEquals("Doe", fireInformationDto.getResidents().get(1).getLastName());
        assertEquals("555-555-5555", fireInformationDto.getResidents().get(1).getPhoneNumber());
        assertEquals(38, fireInformationDto.getResidents().get(1).getAge());
        assertNotNull(fireInformationDto.getResidents().get(1).getMedicalRecords());
        assertEquals("Bob", fireInformationDto.getResidents().get(2).getFirstName());
        assertEquals("Smith", fireInformationDto.getResidents().get(2).getLastName());
        assertEquals("555-555-5555", fireInformationDto.getResidents().get(2).getPhoneNumber());
        assertEquals(33, fireInformationDto.getResidents().get(2).getAge());
        assertNotNull(fireInformationDto.getResidents().get(2).getMedicalRecords());

        verify(personService, times(1)).returnAllPersons();
        verify(medicalRecordsService, times(1)).returnAllMedicalRecords();
        verify(fireStationService, times(1)).returnAllFireStation();
    }

    @Test
    void getFireInformationWhenAddressNotFound() {
        String address = "123 Main St";
        when(fireStationService.returnAllFireStation()).thenReturn(Collections.emptyList());
        when(personService.returnAllPersons()).thenReturn(Collections.emptyList());
        when(medicalRecordsService.returnAllMedicalRecords()).thenReturn(Collections.emptyList());

        FireInformationDto fireInformationDto = fireInformationService.getFireInformation(address);

        assertNull(fireInformationDto);
    }

    @Test
    void getFireInformationWhenAddressNotAssociatedWithFireStation() {
        String address = "123 Main St";
        List<FireStationDTO> fireStationDTOList = Collections.emptyList();
        List<PersonDto> personDtos = Collections.emptyList();
        List<MedicalRecordsDTO> medicalRecordsDTOList = Collections.emptyList();

        when(fireStationService.returnAllFireStation()).thenReturn(fireStationDTOList);
        when(personService.returnAllPersons()).thenReturn(personDtos);
        when(medicalRecordsService.returnAllMedicalRecords()).thenReturn(medicalRecordsDTOList);

        FireInformationDto fireInformationDto = fireInformationService.getFireInformation(address);

        assertNull(fireInformationDto);
    }

    @Test
    void getFireInformationWhenNoResidentsAtAddress() {
        String address = "123 Main St";
        List<FireStationDTO> fireStationDTOList = Collections.singletonList(new FireStationDTO(address, 1));
        List<PersonDto> personDtos = new ArrayList<>();
        List<MedicalRecordsDTO> medicalRecordsDTOList = new ArrayList<>();

        when(fireStationService.returnAllFireStation()).thenReturn(fireStationDTOList);
        when(personService.returnAllPersons()).thenReturn(personDtos);
        when(medicalRecordsService.returnAllMedicalRecords()).thenReturn(medicalRecordsDTOList);

        FireInformationDto fireInformationDto = fireInformationService.getFireInformation(address);

        assertTrue(fireInformationDto.getResidents().size() == 0);
    }

    @Test
    void getFireInformationWithCorrectFireStationNumber() {
        String address = "123 Main St";
        FireStationDTO fireStationDTO = new FireStationDTO(address, 1);
        List<FireStationDTO> fireStationDTOList = Collections.singletonList(fireStationDTO);

        PersonDto personDto = new PersonDto("John", "Doe", address, "City", 12345, "123-456-7890", "john.doe@example.com");
        List<PersonDto> personDtoList = Collections.singletonList(personDto);

        MedicalRecordsDTO medicalRecordsDTO = new MedicalRecordsDTO("John", "Doe", "01/01/2000", new String[]{"medication1", "medication2"}, new String[]{"allergy1", "allergy2"});
        List<MedicalRecordsDTO> medicalRecordsDTOList = Collections.singletonList(medicalRecordsDTO);

        when(fireStationService.returnAllFireStation()).thenReturn(fireStationDTOList);
        when(personService.returnAllPersons()).thenReturn(personDtoList);
        when(medicalRecordsService.returnAllMedicalRecords()).thenReturn(medicalRecordsDTOList);

        FireInformationDto fireInformationDto = fireInformationService.getFireInformation(address);

        assertNotNull(fireInformationDto);
        assertEquals(fireStationDTO.getStation(), fireInformationDto.getFireStationNumber());
    }

    @Test
    void getFireInformationWithCorrectResidentInfo() {// Create mock data
        String address = "123 Main St";
        FireStationDTO fireStationDTO = new FireStationDTO(address, 1);
        List<FireStationDTO> fireStationDTOList = Collections.singletonList(fireStationDTO);

        PersonDto personDto = new PersonDto("John", "Doe", address, "City", 12345, "123-456-7890", "john.doe@example.com");
        List<PersonDto> personDtoList = Collections.singletonList(personDto);

        MedicalRecordsDTO medicalRecordsDTO = new MedicalRecordsDTO("John", "Doe", "01/01/1990", new String[]{"med1", "med2"}, new String[]{"allergy1", "allergy2"});
        List<MedicalRecordsDTO> medicalRecordsDTOList = Collections.singletonList(medicalRecordsDTO);

        when(fireStationService.returnAllFireStation()).thenReturn(fireStationDTOList);
        when(personService.returnAllPersons()).thenReturn(personDtoList);
        when(medicalRecordsService.returnAllMedicalRecords()).thenReturn(medicalRecordsDTOList);

        FireInformationDto fireInformationDto = fireInformationService.getFireInformation(address);

        assertNotNull(fireInformationDto);
        assertEquals(1, fireInformationDto.getFireStationNumber());
        assertEquals(1, fireInformationDto.getResidents().size());

        PersonInfoDto personInfoDto = fireInformationDto.getResidents().get(0);
        assertEquals("John", personInfoDto.getFirstName());
        assertEquals("Doe", personInfoDto.getLastName());
        assertEquals("123-456-7890", personInfoDto.getPhoneNumber());
        assertEquals(33, personInfoDto.getAge());
        assertEquals(medicalRecordsDTO, personInfoDto.getMedicalRecords());

        verify(fireStationService, times(1)).returnAllFireStation();
        verify(personService, times(1)).returnAllPersons();
        verify(medicalRecordsService, times(1)).returnAllMedicalRecords();
    }
}