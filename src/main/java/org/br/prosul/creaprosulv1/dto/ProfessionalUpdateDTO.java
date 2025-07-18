package org.br.prosul.creaprosulv1.dto;

public class ProfessionalUpdateDTO {
  private String name;
  private String registrationNumber;
  private String contactEmail;
  private String phoneNumber;
  private String cpf;

  public ProfessionalUpdateDTO() {}

  public ProfessionalUpdateDTO(String name, String registrationNumber, String contactEmail, String phoneNumber, String cpf) {
    this.name = name;
    this.registrationNumber = registrationNumber;
    this.contactEmail = contactEmail;
    this.phoneNumber = phoneNumber;
    this.cpf = cpf;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getContactEmail() {
    return contactEmail;
  }

  public void setContactEmail(String contactEmail) {
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

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }
}
