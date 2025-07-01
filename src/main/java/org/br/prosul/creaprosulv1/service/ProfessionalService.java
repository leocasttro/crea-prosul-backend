package org.br.prosul.creaprosulv1.service;

import org.br.prosul.creaprosulv1.dto.ProfessionalCreateDTO;
import org.br.prosul.creaprosulv1.entity.ProfessionalEntity;
import org.br.prosul.creaprosulv1.repository.ProfessionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessionalService {
  private final ProfessionalRepository professionalRepository;

  @Autowired
  public ProfessionalService(ProfessionalRepository professionalRepository) {
    this.professionalRepository = professionalRepository;
  }

  public ProfessionalEntity registerProfessional(ProfessionalCreateDTO professionalDTO) {
    Optional<ProfessionalEntity> existingProfessional = professionalRepository.findByRegistrationNumber(professionalDTO.getRegistrationNumber());
    if (existingProfessional.isPresent()) {
      throw new IllegalArgumentException("Já existe um profissional cadastrado com esse CREA");
    }

    ProfessionalEntity professional = new ProfessionalEntity();
    professional.setRegistrationNumber(professionalDTO.getRegistrationNumber());
    professional.setName(professionalDTO.getName());
    professional.setFormation(professionalDTO.getFormation());
    professional.setContactEmail(professionalDTO.getContactEmail());
    professional.setPhoneNumber(professionalDTO.getPhoneNumber());

    return professionalRepository.save(professional);
  }

  public List<ProfessionalEntity> getAllProfessionals() {
    return professionalRepository.findAll();
  }

  public ProfessionalEntity getProfessionalById(Long id) {
    return professionalRepository.findById(id).get();
  }
}
