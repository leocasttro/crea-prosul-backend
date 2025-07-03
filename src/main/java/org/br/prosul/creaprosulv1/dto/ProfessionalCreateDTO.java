package org.br.prosul.creaprosulv1.dto;

public class ProfessionalCreateDTO {

  private String registrationNumber;
  private String name;
  private Long formationId; // Substitui formationServiceActivities
  private String contactEmail;
  private String phoneNumber;

  public ProfessionalCreateDTO() {}

  public ProfessionalCreateDTO(String registrationNumber, String name, Long formationId, String contactEmail, String phoneNumber) {
    this.registrationNumber = registrationNumber;
    this.name = name;
    this.formationId = formationId;
    this.contactEmail = contactEmail;
    this.phoneNumber = phoneNumber;
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

  public Long getFormationId() {
    return formationId;
  }

  public void setFormationId(Long formationId) {
    this.formationId = formationId;
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