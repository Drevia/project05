package com.openclassroom.project5.service;

import com.openclassroom.project5.model.FireStationDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FireStationServiceTest {
    @Mock
    private List<FireStationDTO> fireStationDTOS;

    @InjectMocks
    private FireStationServiceImpl fireStationService;


    @Test
    public void testGetFireStation_Exists() {
        // Mocking the behavior
        FireStationDTO fireStation1 = new FireStationDTO("Address 1", 1);
        FireStationDTO fireStation2 = new FireStationDTO("Address 2", 2);
        when(fireStationDTOS.stream()).thenReturn(Stream.of(fireStation1, fireStation2));

        // Invoke the method being tested
        FireStationDTO result = fireStationService.getFireStation(1, "Address 1");

        // Verify the result
        assertNotNull(result);
        assertEquals(1, result.getStation());
        assertEquals("Address 1", result.getAddress());

        // Verify that the mocked method was called
        verify(fireStationDTOS, times(1)).stream();
    }

    @Test
    public void testGetFireStation_NotExists() {
        // Mocking the behavior
        when(fireStationDTOS.stream()).thenReturn(Stream.empty());

        // Invoke the method being tested
        FireStationDTO result = fireStationService.getFireStation(1, "Address 1");

        // Verify the result
        assertNull(result);

        // Verify that the mocked method was called
        verify(fireStationDTOS, times(1)).stream();
    }

   @Test
    public void testDeleteFireStation_Exists() {
        // Mocking the behavior
        FireStationDTO fireStation = new FireStationDTO("Address 1", 1);

        // Invoke the method being tested
        fireStationService.deleteFireStation(1, "Address 1");

        // Verify that the mocked method was called
        assertEquals(0, fireStationDTOS.size());
    }

    @Test
    public void testDeleteFireStation_NotExists() {
        // Mocking the behavior
        lenient().when(fireStationDTOS.contains(any(FireStationDTO.class))).thenReturn(false);

        // Invoke the method being tested
        fireStationService.deleteFireStation(1, "Address 1");

        // Verify that the mocked method was not called
        verify(fireStationDTOS, never()).remove(any(FireStationDTO.class));
    }

    @Test
    public void testCreateFireStation() {
        // Mocking the behavior
        FireStationDTO fireStation = new FireStationDTO( "Address 1",1);
        when(fireStationDTOS.add(any(FireStationDTO.class))).thenReturn(true);

        // Invoke the method being tested
        FireStationDTO result = fireStationService.createFireStation(fireStation);

        // Verify the result
        assertNotNull(result);
        assertEquals(1, result.getStation());
        assertEquals("Address 1", result.getAddress());

        // Verify that the mocked method was called
        verify(fireStationDTOS, times(1)).add(fireStation);
    }

    @Test
    public void testUpdateFireStation_Exists() {
        // Mocking the behavior
        FireStationDTO fireStation = new FireStationDTO( "Address 1",1);
        when(fireStationDTOS.stream()).thenReturn(Stream.of(fireStation));

        // Invoke the method being tested
        FireStationDTO updatedFireStation = new FireStationDTO( "Updated Address", 2);
        FireStationDTO result = fireStationService.updateFireStation(1, "Address 1", updatedFireStation);

        // Verify the result
        assertNotNull(result);
        assertEquals(2, result.getStation());
        assertEquals("Updated Address", result.getAddress());

        // Verify that the mocked method was called
        verify(fireStationDTOS, times(1)).stream();
    }

    @Test
    public void testUpdateFireStation_NotExists() {
        // Mocking the behavior
        when(fireStationDTOS.stream()).thenReturn(Stream.empty());

        // Invoke the method being tested
        FireStationDTO updatedFireStation = new FireStationDTO( "Updated Address", 1);
        FireStationDTO result = fireStationService.updateFireStation(1, "Address 1", updatedFireStation);

        // Verify the result
        assertNull(result);

        // Verify that the mocked method was called
        verify(fireStationDTOS, times(1)).stream();
    }
}
