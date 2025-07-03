package org.br.prosul.creaprosulv1.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "servicos_principais")
public class ServicesPrincipalEntity {

  @Id
  @Column(name = "id_servico_principal")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idServicoPrincipal;

  @Column(name = "nome_servico_tecnico")
  private String nomeServicoTecnico;

  @Column(name = "codigo_servico_tecnico")
  private String codigoServicoTecnico;

  @OneToMany(mappedBy = "service")
  private List<FormationServiceActivitiesEntity> formacaoServicoAtividades;

  // Getters e Setters
  public Long getIdServicoPrincipal() { return idServicoPrincipal; }
  public void setIdServicoPrincipal(Long idServicoPrincipal) { this.idServicoPrincipal = idServicoPrincipal; }
  public String getNomeServicoTecnico() { return nomeServicoTecnico; }
  public void setNomeServicoTecnico(String nomeServicoTecnico) { this.nomeServicoTecnico = nomeServicoTecnico; }
  public String getCodigoServicoTecnico() { return codigoServicoTecnico; }
  public void setCodigoServicoTecnico(String codigoServicoTecnico) { this.codigoServicoTecnico = codigoServicoTecnico; }
  public List<FormationServiceActivitiesEntity> getFormacaoServicoAtividades() { return formacaoServicoAtividades; }
  public void setFormacaoServicoAtividades(List<FormationServiceActivitiesEntity> formacaoServicoAtividades) { this.formacaoServicoAtividades = formacaoServicoAtividades; }
}