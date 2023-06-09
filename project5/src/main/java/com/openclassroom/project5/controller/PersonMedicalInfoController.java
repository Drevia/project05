package com.openclassroom.project5.controller;

import com.openclassroom.project5.model.PersonMedicalInfoDto;
import com.openclassroom.project5.service.alert.PersonMedicalInfoService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class PersonMedicalInfoController {

    @Autowired
    PersonMedicalInfoService medicalInfoService;

    @GetMapping("/personInfo")
    public ResponseEntity<List<PersonMedicalInfoDto>> getPersonInfo(@RequestParam("firstName") String firstName,
                                                                    @RequestParam("lastName") String lastName) {
        List<PersonMedicalInfoDto> personInfoList = medicalInfoService.getPersonInfoByFullName(firstName, lastName);

        if (personInfoList.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(personInfoList);
        }
    }

}
