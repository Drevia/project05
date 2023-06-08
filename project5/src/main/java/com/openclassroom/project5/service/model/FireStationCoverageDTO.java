package com.openclassroom.project5.service.model;

import com.openclassroom.project5.model.PersonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FireStationCoverageDTO {
    private List<Residents> people;
    private int adultCount;
    private int childCount;
}
