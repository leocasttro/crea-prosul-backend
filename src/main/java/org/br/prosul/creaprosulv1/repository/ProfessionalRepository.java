package org.br.prosul.creaprosulv1.repository;

import org.br.prosul.creaprosulv1.entity.ProfessionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfessionalRepository extends JpaRepository<ProfessionalEntity, Long> {

  Optional<ProfessionalEntity> findByRegistrationNumber(String registrationNumber);
  Optional<ProfessionalEntity> findByCpf(String cpf);
}
