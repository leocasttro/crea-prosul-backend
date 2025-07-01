package org.br.prosul.creaprosulv1.model;

import java.util.List;

public class UserModel {
  private Long id;
  private String username;
  private List<String> roles;

  // Construtor vazio
  public UserModel() {
  }

  // Construtor com campos
  public UserModel(Long id, String username, List<String> roles) {
    this.id = id;
    this.username = username;
    this.roles = roles;
  }

  // Getters e Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public List<String> getRoles() {
    return roles;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }
}