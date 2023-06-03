package com.openclassroom.project5.service;

import com.openclassroom.project5.model.MedicalRecordsDTO;

import java.util.List;

public interface MedicalRecordsService {
    List<MedicalRecordsDTO> returnAllMedicalRecords();
}
