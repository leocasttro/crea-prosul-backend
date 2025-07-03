package org.br.prosul.creaprosulv1.repository;

import org.br.prosul.creaprosulv1.entity.ServicesPrincipalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicesPrincipalRepository extends JpaRepository<ServicesPrincipalEntity, Long> {
  List<ServicesPrincipalEntity> findByFormacaoServicoAtividades_Formation_Id(Long formationId);
}
