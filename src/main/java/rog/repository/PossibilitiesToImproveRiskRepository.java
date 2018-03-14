package rog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rog.domain.PossibilitiesToImproveRisk;


/**
 * Spring Data JPA repository for the PossibilitiesToImproveRisk entity.
 */
@Repository
public interface PossibilitiesToImproveRiskRepository extends JpaRepository<PossibilitiesToImproveRisk, Long> {

}
