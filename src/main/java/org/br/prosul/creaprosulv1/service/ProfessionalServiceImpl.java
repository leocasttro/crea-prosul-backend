package org.br.prosul.creaprosulv1.service;

import jakarta.persistence.EntityNotFoundException;
import org.br.prosul.creaprosulv1.dto.ProfessionalCreateDTO;
import org.br.prosul.creaprosulv1.dto.ProfessionalUpdateDTO;
import org.br.prosul.creaprosulv1.entity.FormationEntity;
import org.br.prosul.creaprosulv1.entity.ProfessionalEntity;
import org.br.prosul.creaprosulv1.entity.ServicesPrincipalEntity;
import org.br.prosul.creaprosulv1.repository.FormationRepository;
import org.br.prosul.creaprosulv1.repository.ProfessionalRepository;
import org.br.prosul.creaprosulv1.repository.ServicesPrincipalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfessionalServiceImpl implements ProfessionalService {

  private final ProfessionalRepository professionalRepository;
  private final FormationRepository formationRepository;
  private final ServicesPrincipalRepository servicesPrincipalRepository;

  @Autowired
  public ProfessionalServiceImpl(ProfessionalRepository professionalRepository, FormationRepository formationRepository, ServicesPrincipalRepository servicesPrincipalRepository) {
    this.professionalRepository = professionalRepository;
    this.formationRepository = formationRepository;
    this.servicesPrincipalRepository = servicesPrincipalRepository;
  }

  @Override
  public ProfessionalEntity registerProfessional(ProfessionalCreateDTO professionalDTO) {
    Optional<ProfessionalEntity> existingProfessional = professionalRepository.findByRegistrationNumber(professionalDTO.getRegistrationNumber());
    if (existingProfessional.isPresent()) {
      throw new IllegalArgumentException("Já existe um profissional cadastrado com esse CREA");
    }

    ProfessionalEntity professional = new ProfessionalEntity();
    professional.setRegistrationNumber(professionalDTO.getRegistrationNumber());
    professional.setName(professionalDTO.getName());
    professional.setContactEmail(professionalDTO.getContactEmail());
    professional.setPhoneNumber(professionalDTO.getPhoneNumber());

    if (professionalDTO.getFormationId() != null) {
      FormationEntity formation = formationRepository.findById(professionalDTO.getFormationId().longValue())
              .orElseThrow(() -> new IllegalArgumentException("Formação não encontrada com ID: " + professionalDTO.getFormationId()));
      professional.setFormation(formation);
    }

    return professionalRepository.save(professional);
  }

  @Override
  public List<ProfessionalEntity> getAllProfessionals() {
    return professionalRepository.findAll();
  }

  @Override
  public ProfessionalEntity getProfessionalById(Long id) {
    return professionalRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Professional not found with id: " + id));
  }

  @Override
  public List<ProfessionalEntity> searchProfessionalsByFormation(Long formationId) {
    // Implementação simplificada; use uma consulta JPA personalizada se necessário
    return professionalRepository.findAll().stream()
            .filter(p -> p.getFormation() != null && p.getFormation().getId().equals(formationId))
            .collect(Collectors.toList());
  }

  @Override
  public List<ProfessionalEntity> searchProfessionalsByService(Long serviceId) {
    // Remova ou ajuste, pois não há mais relação com ServicesPrincipalEntity diretamente
    return new ArrayList<>();
  }

  @Override
  public List<ProfessionalEntity> searchProfessionalsByActivity(Long activityId) {
    // Remova ou ajuste, pois não há mais relação com ActivitiesEntity diretamente
    return new ArrayList<>();
  }

  @Override
  public List<ProfessionalEntity> searchProfessionalsByCriteria(Long formationId, Long serviceId, Long activityId) {
    // Remova ou ajuste, pois não há mais relação com ServicesPrincipalEntity ou ActivitiesEntity diretamente
    return professionalRepository.findAll().stream()
            .filter(p -> p.getFormation() != null && (formationId == null || p.getFormation().getId().equals(formationId)))
            .collect(Collectors.toList());
  }

  @Override
  public List<ServicesPrincipalEntity> searchServicesByFormation(Long formationId) {
    return servicesPrincipalRepository.findByFormacaoServicoAtividades_Formation_Id(formationId);
  }

  @Override
  public ProfessionalEntity deleteProfessional(Long id) {
    Optional<ProfessionalEntity> existingProfessional = professionalRepository.findById(id);
    if (existingProfessional.isPresent()) {
      ProfessionalEntity professional = existingProfessional.get();
      professionalRepository.delete(professional);
      return professional;
    } else {
      throw new EntityNotFoundException("Profissional com ID " + id + " não encontrado.");
    }
  }

  @Override
  public ProfessionalEntity updateProfessional(Long id, ProfessionalUpdateDTO professionalDTO) {
    Optional<ProfessionalEntity> existingProfessional = professionalRepository.findById(id);
    if (existingProfessional.isEmpty()) {
      throw new EntityNotFoundException("Profissional com ID " + id + " não encontrado.");
    }

    ProfessionalEntity professional = existingProfessional.get();

    if (professionalDTO.getRegistrationNumber() != null && !professionalDTO.getRegistrationNumber().equals(professional.getRegistrationNumber())) {
      Optional<ProfessionalEntity> duplicateProfessional = professionalRepository.findByRegistrationNumber(professionalDTO.getRegistrationNumber());
      if (duplicateProfessional.isPresent()) {
        throw new IllegalArgumentException("Já existe um profissional cadastrado com esse número de registro: " + professionalDTO.getRegistrationNumber());
      }
      professional.setRegistrationNumber(professionalDTO.getRegistrationNumber());
    }

    if (professionalDTO.getCpf() != null && !professionalDTO.getCpf().equals(professional.getCpf())) {
      Optional<ProfessionalEntity> duplicateCpf = professionalRepository.findByCpf(professionalDTO.getCpf());
      if (duplicateCpf.isPresent() && !duplicateCpf.get().getId().equals(id)) {
        throw new IllegalArgumentException("Já existe um profissional cadastrado com esse CPF: " + professionalDTO.getCpf());
      }
      professional.setCpf(professionalDTO.getCpf());
    }

    if (professionalDTO.getName() != null) {
      professional.setName(professionalDTO.getName());
    }
    if (professionalDTO.getContactEmail() != null) {
      professional.setContactEmail(professionalDTO.getContactEmail());
    }
    if (professionalDTO.getPhoneNumber() != null) {
      professional.setPhoneNumber(professionalDTO.getPhoneNumber());
    }
    return professionalRepository.save(professional);
  }
}