package org.br.prosul.creaprosulv1.controller;

import org.br.prosul.creaprosulv1.dto.FormationServiceActivitiesDTO;
import org.br.prosul.creaprosulv1.entity.ActivitiesEntity;
import org.br.prosul.creaprosulv1.service.FormationServiceActivitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/formation-service-activities")
public class FormationServiceActivitiesController {

  private final FormationServiceActivitiesService service;

  @Autowired
  public FormationServiceActivitiesController(FormationServiceActivitiesService service) {
    this.service = service;
  }

  @GetMapping("/byFormation/{formationId}")
  public ResponseEntity<List<FormationServiceActivitiesDTO>> getByFormationId(
          @PathVariable Long formationId) {
    try {
      List<FormationServiceActivitiesDTO> result = service.findByFormationId(formationId);
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @GetMapping("/byService/{serviceId}/activities")
  public ResponseEntity<List<ActivityProjection>> getActivitiesByServiceId(
          @PathVariable Long serviceId) {
    try {
      List<FormationServiceActivitiesDTO> result = service.findByServiceId(serviceId);
      List<ActivityProjection> projectedList = result.stream().map(dto -> {
        ActivitiesEntity activity = service.findActivityById(dto.getAtividadeId());
        return new ActivityProjection(
                activity != null ? activity.getCodigo_atividade() : null,
                activity != null ? activity.getDescricao_atividade() : null
        );
      }).collect(Collectors.toList());
      return ResponseEntity.ok(projectedList);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  // Classe interna para projeção
  public static class ActivityProjection {
    private String codigoAtividade;
    private String descricaoAtividade;

    public ActivityProjection(String codigoAtividade, String descricaoAtividade) {
      this.codigoAtividade = codigoAtividade;
      this.descricaoAtividade = descricaoAtividade;
    }

    public String getCodigoAtividade() { return codigoAtividade; }
    public String getDescricaoAtividade() { return descricaoAtividade; }
  }
}