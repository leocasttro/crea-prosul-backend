package org.br.prosul.creaprosulv1.service;

import org.br.prosul.creaprosulv1.dto.ProfessionalCreateDTO;
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
}