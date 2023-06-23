package com.openclassroom.project5.model;

import lombok.*;

@Getter
@Setter
@ToString
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
