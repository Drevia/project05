package com.openclassroom.project5.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonMedicalInfoDto {
    private String firstName;
    private String lastName;
    private String address;
    private int age;
    private String email;
    private String[] medications;
    private String[] allergies;
}
