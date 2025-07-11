package org.br.prosul.creaprosulv1.controller;

import org.br.prosul.creaprosulv1.dto.CostumerCreateDTO;
import org.br.prosul.creaprosulv1.entity.CostumerEntity;
import org.br.prosul.creaprosulv1.service.CostumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/costumer")
public class CostumerController {

  private final CostumerService costumerService;

  @Autowired
  public CostumerController(CostumerService costumerService) {
    this.costumerService = costumerService;
  }

  @PostMapping("/createCostumer")
  public ResponseEntity<?> registerCostumer(@Validated @RequestBody CostumerCreateDTO costumerCreateDTO) {
    try {
      CostumerEntity costumerEntity = costumerService.registerCostumer(costumerCreateDTO);
      return ResponseEntity.status(HttpStatus.CREATED).body(costumerEntity);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/getAllCostumer")
  public ResponseEntity<List<CostumerEntity>> getAllCostumer() {
    try {
      List<CostumerEntity> costumers = costumerService.getAllCostumers();
      return ResponseEntity.status(HttpStatus.OK).body(costumers);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @GetMapping("/getCostumer/{id}")
  public ResponseEntity<CostumerEntity> getCostumer(@PathVariable Long id) {
    try {
      CostumerEntity costumer = costumerService.getCostumer(id);
      return ResponseEntity.status(HttpStatus.OK).body(costumer);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }
}
