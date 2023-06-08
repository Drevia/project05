package com.openclassroom.project5.service.model;

import com.openclassroom.project5.model.MedicalRecordsDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonInfoDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private int age;
    private MedicalRecordsDTO medicalRecords;
}
