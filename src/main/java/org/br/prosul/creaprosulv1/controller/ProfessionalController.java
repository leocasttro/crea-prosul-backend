package org.br.prosul.creaprosulv1.controller;

import org.br.prosul.creaprosulv1.dto.ProfessionalCreateDTO;
import org.br.prosul.creaprosulv1.entity.ProfessionalEntity;
import org.br.prosul.creaprosulv1.service.ProfessionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/professionals")
public class ProfessionalController {

  private final ProfessionalService professionalService;

  @Autowired
  public ProfessionalController(ProfessionalService professionalService) {
    this.professionalService = professionalService;
  }

  @PostMapping("/createProfessionals")
  public ResponseEntity<?> registerProfessional(@Validated @RequestBody ProfessionalCreateDTO professionalCreateDTO) {
    try {
      ProfessionalEntity professionalEntity = professionalService.registerProfessional(professionalCreateDTO);
      return ResponseEntity.status(HttpStatus.CREATED).body(professionalEntity);
    } catch (IllegalArgumentException e) {
      return  ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @GetMapping("/getAllProfessionals")
  public ResponseEntity<List<ProfessionalEntity>> getAllProfessionals() {
    try {
      List<ProfessionalEntity> professionals = professionalService.getAllProfessionals();
      return ResponseEntity.ok(professionals);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @GetMapping("/getProfessional/{id}")
  public ResponseEntity<ProfessionalEntity> getProfessional(@PathVariable Long id) {
    try {
      ProfessionalEntity professionalEntity = professionalService.getProfessionalById(id);
      return ResponseEntity.ok(professionalEntity);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }
}
