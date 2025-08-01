package com.pm.patientservice.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Entity
public class Patient {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)// generate unique id automatically by db.
  private UUID id;
  
  @NotNull // can not be null when saved into db.
  private String name;
  
  @NotNull
  private LocalDate dateOfBirth;
  
  @NotNull
  private LocalDate registeredDate;
  
  @Email
  @NotNull
  @Column(unique = true)
  private String email;
  
  @NotNull
  private String address;
  
  public UUID getId() {
    return id;
  }
  
  public void setId(UUID id) {
    this.id = id;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getEmail() {
    return email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getAddress() {
    return address;
  }
  
  public void setAddress(String address) {
    this.address = address;
  }
  
  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }
  
  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }
  
  public LocalDate getRegisteredDate() {
    return registeredDate;
  }
  
  public void setRegisteredDate(LocalDate registeredDate) {
    this.registeredDate = registeredDate;
  }
  
  
  
  
}
