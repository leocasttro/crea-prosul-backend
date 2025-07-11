package org.br.prosul.creaprosulv1.repository;

import org.br.prosul.creaprosulv1.entity.CostumerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostumersRepository extends JpaRepository<CostumerEntity, Long> {
  boolean existsByCnpj(String cnpj);
}
