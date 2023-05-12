package com.openclassroom.project5.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {

    private String firstName;

    private String lastName;

    private String address;

    private String city;

    private Integer zip;

    private String phone;

    private String email;


}
