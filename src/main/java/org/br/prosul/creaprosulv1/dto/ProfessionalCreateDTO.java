package org.br.prosul.creaprosulv1.dto;

public class ProfessionalCreateDTO {

  private String registrationNumber;
  private String name;
  private String formation;
  private String contactEmail;
  private String phoneNumber;

  public ProfessionalCreateDTO() {}

  public ProfessionalCreateDTO(String registrationNumber, String name, String formation, String contactEmail) {
    this.registrationNumber = registrationNumber;
    this.name = name;
    this.formation = formation;
    this.contactEmail = contactEmail;
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
    return formation;
  }
  public void setFormation(String formation) {
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
