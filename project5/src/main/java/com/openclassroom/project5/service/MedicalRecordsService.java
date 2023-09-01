package com.openclassroom.project5.service;

import com.openclassroom.project5.model.MedicalRecordsDTO;

import java.util.List;

public interface MedicalRecordsService {
    MedicalRecordsDTO getMedicalRecords(String firstName, String lastName);

    void deleteMedicalRecords(String firstName, String lastName);

    MedicalRecordsDTO createMedicalRecords(MedicalRecordsDTO medicalRecordsDTO);

    MedicalRecordsDTO updateMedicalRecords(String firstName, String lastName, MedicalRecordsDTO medicalRecordsDTOUpdated);

    List<MedicalRecordsDTO> returnAllMedicalRecords();
}
