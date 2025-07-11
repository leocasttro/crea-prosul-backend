package org.br.prosul.creaprosulv1.repository;

import org.br.prosul.creaprosulv1.dto.FormationServiceActivitiesDTO;
import org.br.prosul.creaprosulv1.entity.FormationServiceActivitiesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormationServiceActivitiesRepository extends JpaRepository<FormationServiceActivitiesEntity, Long> {
  @Query("SELECT DISTINCT new org.br.prosul.creaprosulv1.dto.FormationServiceActivitiesDTO(fsa.id, fsa.formation.id, sp.idServicoPrincipal, fsa.activity.id, sp.nomeServicoTecnico, sp.codigoServicoTecnico) " +
          "FROM ServicesPrincipalEntity sp " +
          "JOIN sp.formacaoServicoAtividades fsa " +
          "JOIN fsa.formation f " +
          "WHERE f.id = :formationId")
  List<FormationServiceActivitiesDTO> findByFormationId(Long formationId);

  @Query("SELECT DISTINCT new org.br.prosul.creaprosulv1.dto.FormationServiceActivitiesDTO(fsa.id, fsa.formation.id, sp.idServicoPrincipal, fsa.activity.id, sp.nomeServicoTecnico, sp.codigoServicoTecnico) " +
          "FROM ServicesPrincipalEntity sp " +
          "JOIN sp.formacaoServicoAtividades fsa " +
          "JOIN fsa.formation f " +
          "WHERE sp.idServicoPrincipal = :serviceId")
  List<FormationServiceActivitiesDTO> findByServiceId(Long serviceId);
}