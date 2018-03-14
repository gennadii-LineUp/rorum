package rog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rog.domain.RisksPurposes;


/**
 * Spring Data JPA repository for the RisksPurposes entity.
 */

@Repository
public interface RisksPurposesRepository extends JpaRepository<RisksPurposes, Long> {

}
