package org.br.prosul.creaprosulv1.service;

import org.br.prosul.creaprosulv1.dto.FormationServiceActivitiesDTO;
import org.br.prosul.creaprosulv1.entity.ActivitiesEntity;
import org.br.prosul.creaprosulv1.repository.ActivitiesRepository;
import org.br.prosul.creaprosulv1.repository.FormationServiceActivitiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FormationServiceActivitiesService {

  private final FormationServiceActivitiesRepository repository;
  private final ActivitiesRepository activitiesRepository;

  @Autowired
  public FormationServiceActivitiesService(FormationServiceActivitiesRepository repository, ActivitiesRepository activitiesRepository) {
    this.repository = repository;
    this.activitiesRepository = activitiesRepository;
  }

  public Page<FormationServiceActivitiesDTO> findByFormationId(Long formationId, Pageable pageable) {
    if (formationId == null) {
      throw new IllegalArgumentException("Formation ID cannot be null");
    }
    return repository.findByFormationId(formationId, pageable);
  }

  public Page<FormationServiceActivitiesDTO> findByServiceId(Long serviceId, Pageable pageable) {
    if (serviceId == null) {
      throw new IllegalArgumentException("Service ID cannot be null");
    }
    return repository.findByServiceId(serviceId, pageable);
  }

  public ActivitiesEntity findActivityById(Long activityId) {
    return activitiesRepository.findById(activityId).orElse(null);
  }
}