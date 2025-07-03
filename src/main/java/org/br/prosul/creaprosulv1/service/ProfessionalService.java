package org.br.prosul.creaprosulv1.service;

import org.br.prosul.creaprosulv1.dto.ProfessionalCreateDTO;
import org.br.prosul.creaprosulv1.entity.ProfessionalEntity;
import org.br.prosul.creaprosulv1.entity.ServicesPrincipalEntity;

import java.util.List;

public interface ProfessionalService {
  ProfessionalEntity registerProfessional(ProfessionalCreateDTO professionalDTO);
  List<ProfessionalEntity> getAllProfessionals();
  ProfessionalEntity getProfessionalById(Long id);
  List<ProfessionalEntity> searchProfessionalsByFormation(Long formationId);
  List<ProfessionalEntity> searchProfessionalsByService(Long serviceId);
  List<ProfessionalEntity> searchProfessionalsByActivity(Long activityId);
  List<ProfessionalEntity> searchProfessionalsByCriteria(Long formationId, Long serviceId, Long activityId);
  List<ServicesPrincipalEntity> searchServicesByFormation(Long formationId);
}