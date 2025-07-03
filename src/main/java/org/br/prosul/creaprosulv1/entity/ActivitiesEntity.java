package org.br.prosul.creaprosulv1.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tipos_atividade")
public class ActivitiesEntity {

  @Id
  @Column(name = "id_tipo_atividade")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String descricao_atividade;

  private String codigo_atividade;

  public Long getId_tipo_atividade() {
    return id;
  }
  public void setId_tipo_atividade(Long id) {
    this.id = id;
  }

  public String getDescricao_atividade() {
    return descricao_atividade;
  }
  public void setDescricao_atividade(String descricao_atividade) {
    this.descricao_atividade = descricao_atividade;
  }

  public String getCodigo_atividade() {
    return codigo_atividade;
  }
  public void setCodigo_atividade(String codigo_atividade) {
    this.codigo_atividade = codigo_atividade;
  }
}
