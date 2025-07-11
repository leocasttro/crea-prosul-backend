package org.br.prosul.creaprosulv1.service;

import org.br.prosul.creaprosulv1.dto.FormationServiceActivitiesDTO;
import org.br.prosul.creaprosulv1.entity.ActivitiesEntity;
import org.br.prosul.creaprosulv1.repository.ActivitiesRepository;
import org.br.prosul.creaprosulv1.repository.FormationServiceActivitiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormationServiceActivitiesService {

  private final FormationServiceActivitiesRepository repository;
  private final ActivitiesRepository activitiesRepository;

  @Autowired
  public FormationServiceActivitiesService(FormationServiceActivitiesRepository repository, ActivitiesRepository activitiesRepository) {
    this.repository = repository;
    this.activitiesRepository = activitiesRepository;
  }

  public List<FormationServiceActivitiesDTO> findByFormationId(Long formationId) {
    if (formationId == null) {
      throw new IllegalArgumentException("Formation ID cannot be null");
    }
    return repository.findByFormationId(formationId);
  }

  public List<FormationServiceActivitiesDTO> findByServiceId(Long serviceId) {
    if (serviceId == null) {
      throw new IllegalArgumentException("Service ID cannot be null");
    }
    return repository.findByServiceId(serviceId);
  }

  public ActivitiesEntity findActivityById(Long activityId) {
    return activitiesRepository.findById(activityId).orElse(null);
  }
}