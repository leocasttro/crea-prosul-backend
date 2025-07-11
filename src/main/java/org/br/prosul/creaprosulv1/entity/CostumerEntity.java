package org.br.prosul.creaprosulv1.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "clientes")
public class CostumerEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String cliente;

  private String endereco;

  private String cep;

  private String telefone;

  @Column(nullable = false)
  private String cnpj;

  public CostumerEntity() {}

  public CostumerEntity(String  cliente, String endereco, String cep, String telefone, String cnpj) {
    this.cliente = cliente;
    this.endereco = endereco;
    this.cep = cep;
    this.telefone = telefone;
    this.cnpj = cnpj;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCliente() {
    return cliente;
  }

  public void setCliente(String cliente) {
    this.cliente = cliente;
  }

  public String getEndereco() {
    return endereco;
  }

  public void setEndereco(String endereco) {
    this.endereco = endereco;
  }

  public String getCep() {
    return cep;
  }

  public void setCep(String cep) {
    this.cep = cep;
  }

  public String getTelefone() {
    return telefone;
  }

  public void setTelefone(String telefone) {
    this.telefone = telefone;
  }

  public String getCnpj() {
    return cnpj;
  }

  public void setCnpj(String cnpj) {
    this.cnpj = cnpj;
  }
}
