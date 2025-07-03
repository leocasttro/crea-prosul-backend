package org.br.prosul.creaprosulv1.dto;

public class FormationServiceActivitiesDTO {
  private Long id;
  private Long formationId;
  private Long servicoId;
  private Long atividadeId;
  private String nomeServicoTecnico;
  private String codigoServicoTecnico;

  public FormationServiceActivitiesDTO(Long id, Long formationId, Long servicoId, Long atividadeId, String nomeServicoTecnico, String codigoServicoTecnico) {
    this.id = id;
    this.formationId = formationId;
    this.servicoId = servicoId;
    this.atividadeId = atividadeId;
    this.nomeServicoTecnico = nomeServicoTecnico;
    this.codigoServicoTecnico = codigoServicoTecnico;
  }

  // Getters e Setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public Long getFormationId() { return formationId; }
  public void setFormationId(Long formationId) { this.formationId = formationId; }
  public Long getServicoId() { return servicoId; }
  public void setServicoId(Long servicoId) { this.servicoId = servicoId; }
  public Long getAtividadeId() { return atividadeId; }
  public void setAtividadeId(Long atividadeId) { this.atividadeId = atividadeId; }
  public String getNomeServicoTecnico() { return nomeServicoTecnico; }
  public void setNomeServicoTecnico(String nomeServicoTecnico) { this.nomeServicoTecnico = nomeServicoTecnico; }
  public String getCodigoServicoTecnico() { return codigoServicoTecnico; }
  public void setCodigoServicoTecnicostr(String codigoServicoTecnico) { this.codigoServicoTecnico = codigoServicoTecnico; }
}