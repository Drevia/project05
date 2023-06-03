package com.openclassroom.project5.service.model;

import com.openclassroom.project5.model.PersonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChildAlertDto {

    private List<PersonDto> child = new ArrayList<>();

    public void addChild(PersonDto personDto) {
        child.add(personDto);
    }
}
