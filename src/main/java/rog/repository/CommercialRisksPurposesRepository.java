package rog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rog.domain.CommercialRisksPurposes;


/**
 * Spring Data JPA repository for the CommercialRisksPurposes entity.
 */

@Repository
public interface CommercialRisksPurposesRepository extends JpaRepository<CommercialRisksPurposes, Long> {

}
