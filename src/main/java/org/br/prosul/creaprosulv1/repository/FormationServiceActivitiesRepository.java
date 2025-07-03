package org.br.prosul.creaprosulv1.repository;

import org.br.prosul.creaprosulv1.dto.FormationServiceActivitiesDTO;
import org.br.prosul.creaprosulv1.entity.FormationServiceActivitiesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FormationServiceActivitiesRepository extends JpaRepository<FormationServiceActivitiesEntity, Long> {
  @Query("SELECT DISTINCT new org.br.prosul.creaprosulv1.dto.FormationServiceActivitiesDTO(fsa.id, fsa.formation.id, sp.idServicoPrincipal, fsa.activity.id, sp.nomeServicoTecnico, sp.codigoServicoTecnico) " +
          "FROM ServicesPrincipalEntity sp " +
          "JOIN sp.formacaoServicoAtividades fsa " +
          "JOIN fsa.formation f " +
          "WHERE f.id = :formationId")
  Page<FormationServiceActivitiesDTO> findByFormationId(Long formationId, Pageable pageable);

  @Query("SELECT DISTINCT new org.br.prosul.creaprosulv1.dto.FormationServiceActivitiesDTO(fsa.id, fsa.formation.id, sp.idServicoPrincipal, fsa.activity.id, sp.nomeServicoTecnico, sp.codigoServicoTecnico) " +
          "FROM ServicesPrincipalEntity sp " +
          "JOIN sp.formacaoServicoAtividades fsa " +
          "JOIN fsa.formation f " +
          "WHERE sp.idServicoPrincipal = :serviceId")
  Page<FormationServiceActivitiesDTO> findByServiceId(Long serviceId, Pageable pageable);
}