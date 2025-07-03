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

  @ManyToOne
  @JoinColumn(name = "formation_id", referencedColumnName = "id")
  private FormationEntity formation; // Nova relação com FormationEntity

  @Column
  private String contactEmail;

  @Column
  private String phoneNumber;

  public ProfessionalEntity() {}

  public ProfessionalEntity(String registrationNumber, String name, FormationEntity formation, String contactEmail, String phoneNumber) {
    this.registrationNumber = registrationNumber;
    this.name = name;
    this.formation = formation;
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

  public FormationEntity getFormation() {
    return formation;
  }

  public void setFormation(FormationEntity formation) {
    this.formation = formation;
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