package rog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rog.domain.DecisionForRisk;


/**
 * Spring Data JPA repository for the DecisionForRisk entity.
 */
@Repository
public interface DecisionForRiskRepository extends JpaRepository<DecisionForRisk, Long> {

}
