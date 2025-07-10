package com.pm.patientservice.controller;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.dto.validators.CreatePatientValidationGroup;
import com.pm.patientservice.service.PatientService;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;


@RestController
@RequestMapping("/patients")
@Tag(name = "Patient", description = "API for managing Patients")
public class PatientController {
  private final PatientService patientService;
  
  public PatientController(PatientService patientService) {
    this.patientService = patientService;
  }
  
  @GetMapping
  @Operation(summary = "get patients")
  public ResponseEntity<List<PatientResponseDTO>> getPatients() {
    List<PatientResponseDTO> patients = patientService.getPatient();
    return ResponseEntity.ok().body(patients);
  }
  
  @PostMapping
  // @RequestBody, get body in json from http request and convert to java obj. here is prdto.
  @Operation(summary = "create a new patient")
  public ResponseEntity<PatientResponseDTO> createPatient(
          @Validated({Default.class, CreatePatientValidationGroup.class}) // 这里两个组别都检查
          @RequestBody PatientRequestDTO patientRequestDTO) {
    PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO);
    
    return ResponseEntity.ok().body(patientResponseDTO);
  }
  
  // if a request ask to update
  @PutMapping("/{id}")
  @Operation(summary = "update a patient")
  public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable UUID id,
      @Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO) {
    PatientResponseDTO patientResponseDTO = patientService.updatePatient(id, patientRequestDTO);
    return ResponseEntity.ok().body(patientResponseDTO);
  }
  
  @DeleteMapping("/{id}")
  @Operation(summary = "delete a patient")
  public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
    patientService.deletePatient(id);
    return ResponseEntity.noContent().build();
  }
}
