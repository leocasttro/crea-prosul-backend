package org.br.prosul.creaprosulv1.service;

import jakarta.transaction.Transactional;
import org.br.prosul.creaprosulv1.dto.CostumerCreateDTO;
import org.br.prosul.creaprosulv1.entity.CostumerEntity;
import org.br.prosul.creaprosulv1.repository.CostumersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CostumerService {
  private CostumersRepository costumersRepository;

  @Autowired
  public void setCostumersRepository(CostumersRepository costumersRepository) {
    this.costumersRepository = costumersRepository;
  }

  @Transactional
  public CostumerEntity registerCostumer(CostumerCreateDTO costumerDTO) {
    // Verificar se o CNPJ já existe
    if (costumersRepository.existsByCnpj(costumerDTO.getCnpj())) {
      throw new IllegalArgumentException("CNPJ já cadastrado: " + costumerDTO.getCnpj());
    }
    CostumerEntity costumer = new CostumerEntity(
            costumerDTO.getCliente(),
            costumerDTO.getEndereco(),
            costumerDTO.getCep(),
            costumerDTO.getTelefone(),
            costumerDTO.getCnpj()
    );
    return costumersRepository.save(costumer);
  }

  public List<CostumerEntity> getAllCostumers() {
    return costumersRepository.findAll();
  }

  public CostumerEntity getCostumer(Long costumerId) {
    return costumersRepository.findById(costumerId).get();
  }
}
