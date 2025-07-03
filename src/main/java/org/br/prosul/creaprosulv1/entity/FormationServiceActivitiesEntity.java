package org.br.prosul.creaprosulv1.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "formacao_servico_atividade")
public class FormationServiceActivitiesEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "formation_id", referencedColumnName = "id")
  @JsonBackReference
  private FormationEntity formation;

  @ManyToOne
  @JoinColumn(name = "servico_id", referencedColumnName = "id_servico_principal")
  private ServicesPrincipalEntity service;

  @ManyToOne
  @JoinColumn(name = "atividade_id", referencedColumnName = "id_tipo_atividade")
  private ActivitiesEntity activity;

  // Getters e Setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public FormationEntity getFormation() { return formation; }
  public void setFormation(FormationEntity formation) { this.formation = formation; }
  public ServicesPrincipalEntity getService() { return service; }
  public void setService(ServicesPrincipalEntity service) { this.service = service; }
  public ActivitiesEntity getActivity() { return activity; }
  public void setActivity(ActivitiesEntity activity) { this.activity = activity; }
}