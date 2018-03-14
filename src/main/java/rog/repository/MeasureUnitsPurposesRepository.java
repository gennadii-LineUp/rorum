package rog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rog.domain.MeasureUnitsPurposes;


/**
 * Spring Data JPA repository for the MeasureUnitsPurposes entity.
 */

@Repository
public interface MeasureUnitsPurposesRepository extends JpaRepository<MeasureUnitsPurposes, Long> {

}
