package org.br.prosul.creaprosulv1.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "professionals")
public class ProfessionalEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String registrationNumber;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String formation_id;

  @Column
  private String contactEmail;

  @Column
  private String phoneNumber;

  public ProfessionalEntity() {}

  public ProfessionalEntity(String registrationNumber, String name, String formation_id, String contactEmail, String phoneNumber) {
    this.registrationNumber = registrationNumber;
    this.name = name;
    this.formation_id = formation_id;
    this.contactEmail = contactEmail;
    this.phoneNumber = phoneNumber;
  }

  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }

  public String getRegistrationNumber() {
    return registrationNumber;
  }
  public void setRegistrationNumber(String registrationNumber) {
    this.registrationNumber = registrationNumber;
  }

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public String getFormation() {
    return formation_id;
  }
  public void setFormation(String formation_id) {
    this.formation_id = formation_id;
  }

  public String getContactEmail() {
    return contactEmail;
  }
  public void setContactEmail(String contactEmail) {
    this.contactEmail = contactEmail;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
}

