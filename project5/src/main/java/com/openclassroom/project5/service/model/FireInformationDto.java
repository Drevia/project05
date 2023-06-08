package com.openclassroom.project5.service.model;

import com.openclassroom.project5.model.MedicalRecordsDTO;
import com.openclassroom.project5.model.PersonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FireInformationDto {

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private int age;

    private MedicalRecordsDTO medicalRecordsDTO;
}
