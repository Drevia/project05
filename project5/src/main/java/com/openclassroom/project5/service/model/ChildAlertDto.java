package com.openclassroom.project5.service.model;

import com.openclassroom.project5.model.PersonDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChildAlertDto {

    private List<PersonDto> children = new ArrayList<>();

    public void addChild(PersonDto personDto) {
        children.add(personDto);
    }
}
