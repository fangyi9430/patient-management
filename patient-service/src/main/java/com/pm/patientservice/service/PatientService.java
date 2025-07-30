package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.exception.EmailAlreadyExistsException;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.grpc.BillingServiceGrpcClient;
import com.pm.patientservice.kafka.KafkaProducer;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Service
public class PatientService {
  private final PatientRepository patientRepository;
  private final BillingServiceGrpcClient billingServiceGrpcClient;
  private final KafkaProducer kafkaProducer;
  
  public PatientService(PatientRepository patientRepository, BillingServiceGrpcClient billingServiceGrpcClient,
                        KafkaProducer kafkaProducer) {
    this.patientRepository = patientRepository;
    this.billingServiceGrpcClient = billingServiceGrpcClient;
    this.kafkaProducer = kafkaProducer;
  }
  
  public List<PatientResponseDTO> getPatient() {
    List<Patient> patients = patientRepository.findAll();
    
    return patients.stream().map(PatientMapper::toDTO).toList();
  }
  
  public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
    // save the new patient to db, and return the entity with uuid.
    if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
      throw new EmailAlreadyExistsException("A patient with this email already exists" + patientRequestDTO.getEmail());
    }
    Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));
    
    // make a grpc request to create the billing account
    billingServiceGrpcClient.createBillingAccount(newPatient.getId().toString(), newPatient.getName().toString(),
            newPatient.getEmail().toString());
    
    kafkaProducer.sendEvent(newPatient);
    
    return PatientMapper.toDTO(newPatient);
  }
  
  public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
    // find the patient in db using id.
    Patient patient = patientRepository.findById(id).orElseThrow(
            () -> new PatientNotFoundException("Patient not found with id: {} " + id)
    );
    
    // check if duplicated email
    if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id)) {
      throw new EmailAlreadyExistsException(
              "A patient with this email already exists" + patientRequestDTO.getEmail()
      );
    }
    // update the patient
    patient.setEmail(patientRequestDTO.getEmail());
    patient.setName(patientRequestDTO.getName());
    patient.setAddress(patientRequestDTO.getAddress());
    patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
    
    // save the updated patient into db
    Patient updatedPatient = patientRepository.save(patient);
    
    
    return PatientMapper.toDTO(updatedPatient);
  }
  
  public void deletePatient(UUID id) {
    patientRepository.deleteById(id);
  }
}
