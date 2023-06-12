package com.openclassroom.project5.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Residents {

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private List<String> medications;

    private List<String> allergies;

    private String age;

}
