package com.pm.patientservice.mapper;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.model.Patient;

import java.time.LocalDate;

public class PatientMapper {
  public static PatientResponseDTO toDTO(Patient patient) {
    PatientResponseDTO patientDTO = new PatientResponseDTO();
    
    patientDTO.setId(patient.getId().toString());
    patientDTO.setAddress(patient.getAddress());
    patientDTO.setEmail(patient.getEmail());
    patientDTO.setName(patient.getName());
    patientDTO.setDateOfBirth(patient.getDateOfBirth().toString());
    
    return patientDTO;
  }
  
  public static Patient toModel(PatientRequestDTO patientResponseDTO) {
    Patient patient = new Patient();
    
    patient.setName(patientResponseDTO.getName());
    patient.setAddress(patientResponseDTO.getAddress());
    patient.setEmail(patientResponseDTO.getEmail());
    
    patient.setDateOfBirth(LocalDate.parse(patientResponseDTO.getDateOfBirth()));
    patient.setRegisteredDate(LocalDate.parse(patientResponseDTO.getRegisteredDate()));
    
    return patient;
  }
}
