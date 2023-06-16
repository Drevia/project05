package com.openclassroom.project5.service.alert;

import com.openclassroom.project5.service.FireStationService;
import com.openclassroom.project5.service.MedicalRecordsService;
import com.openclassroom.project5.service.PersonService;
import com.openclassroom.project5.service.model.FloodStationDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("FloodStationAlertService test")
class FloodStationAlertServiceTest {

    @Mock
    private FireStationService fireStationService;

    @Mock
    private PersonService personService;

    @Mock
    private MedicalRecordsService medicalRecordsService;

    @InjectMocks
    private FloodStationAlertService floodStationAlertService;

    @Test
    void getHouseholdsByStationsWhenNoStationNumbersProvided() {
        List<Integer> stationNumbers = new ArrayList<>();
        List<FloodStationDto> households = floodStationAlertService.getHouseholdsByStations(stationNumbers);

        assertTrue(households.isEmpty());
    }

    @Test
    void getHouseholdsByStationsWhenStationNumbersDoNotMatchFireStations() {
        List<Integer> stationNumbers = List.of(1, 2, 3);
        List<FloodStationDto> expectedHouseholds = new ArrayList<>();

        when(fireStationService.returnAllFireStation()).thenReturn(new ArrayList<>());
        when(personService.returnAllPersons()).thenReturn(new ArrayList<>());

        List<FloodStationDto> actualHouseholds = floodStationAlertService.getHouseholdsByStations(stationNumbers);

        assertEquals(expectedHouseholds, actualHouseholds);
        verify(fireStationService, times(1)).returnAllFireStation();
        verify(personService, times(1)).returnAllPersons();
        verifyNoMoreInteractions(fireStationService, personService, medicalRecordsService);
    }

}