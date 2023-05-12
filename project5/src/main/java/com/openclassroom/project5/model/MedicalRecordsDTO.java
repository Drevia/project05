package com.openclassroom.project5.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordsDTO {

    private String firstName;

    private String lastName;

    private String birthdate;

    private String[] medications;

    private String[] allergies;
}
