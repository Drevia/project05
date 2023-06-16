package com.openclassroom.project5.service;

import com.openclassroom.project5.model.FireStationDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        FireStationDTO fireStation1 = new FireStationDTO("Address 1", 1);
        FireStationDTO fireStation2 = new FireStationDTO("Address 2", 2);
        when(fireStationDTOS.stream()).thenReturn(Stream.of(fireStation1, fireStation2));

        FireStationDTO result = fireStationService.getFireStation(1, "Address 1");

        assertNotNull(result);
        assertEquals(1, result.getStation());
        assertEquals("Address 1", result.getAddress());

        verify(fireStationDTOS, times(1)).stream();
    }

    @Test
    public void testGetFireStation_NotExists() {
        when(fireStationDTOS.stream()).thenReturn(Stream.empty());

        FireStationDTO result = fireStationService.getFireStation(1, "Address 1");

        assertNull(result);

        verify(fireStationDTOS, times(1)).stream();
    }

   @Test
    public void testDeleteFireStation_Exists() {
        FireStationDTO fireStation = new FireStationDTO("Address 1", 1);

        fireStationService.deleteFireStation(1, "Address 1");

        assertEquals(0, fireStationDTOS.size());
    }

    @Test
    public void testDeleteFireStation_NotExists() {
        lenient().when(fireStationDTOS.contains(any(FireStationDTO.class))).thenReturn(false);

        fireStationService.deleteFireStation(1, "Address 1");

        verify(fireStationDTOS, never()).remove(any(FireStationDTO.class));
    }

    @Test
    public void testCreateFireStation() {
        FireStationDTO fireStation = new FireStationDTO( "Address 1",1);
        when(fireStationDTOS.add(any(FireStationDTO.class))).thenReturn(true);

        FireStationDTO result = fireStationService.createFireStation(fireStation);

        assertNotNull(result);
        assertEquals(1, result.getStation());
        assertEquals("Address 1", result.getAddress());

        verify(fireStationDTOS, times(1)).add(fireStation);
    }

    @Test
    public void testUpdateFireStation_Exists() {
        FireStationDTO fireStation = new FireStationDTO( "Address 1",1);
        when(fireStationDTOS.stream()).thenReturn(Stream.of(fireStation));

        FireStationDTO updatedFireStation = new FireStationDTO( "Updated Address", 2);
        FireStationDTO result = fireStationService.updateFireStation(1, "Address 1", updatedFireStation);

        assertNotNull(result);
        assertEquals(2, result.getStation());
        assertEquals("Updated Address", result.getAddress());

        verify(fireStationDTOS, times(1)).stream();
    }

    @Test
    public void testUpdateFireStation_NotExists() {
        when(fireStationDTOS.stream()).thenReturn(Stream.empty());

        // Invoke the method being tested
        FireStationDTO updatedFireStation = new FireStationDTO( "Updated Address", 1);
        FireStationDTO result = fireStationService.updateFireStation(1, "Address 1", updatedFireStation);

        assertNull(result);

        verify(fireStationDTOS, times(1)).stream();
    }
}
