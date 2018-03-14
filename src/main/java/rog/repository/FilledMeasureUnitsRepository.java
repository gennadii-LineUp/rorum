package rog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rog.domain.FilledMeasureUnits;


/**
 * Spring Data JPA repository for the FilledMeasureUnits entity.
 */

@Repository
public interface FilledMeasureUnitsRepository extends JpaRepository<FilledMeasureUnits, Long> {

	@Query(value="SELECT fmu.*\r\n" +
			"  FROM public.filled_measure_units fmu WHERE is_saved = false AND "
			+ "measure_units_purposes_id = ?1 AND glossary_of_measure_units_id=?2  "
			+ "ORDER BY creation_date DESC LIMIT 1;", nativeQuery = true)
	FilledMeasureUnits findLastFilledMeasureUnit(Long measureUnitPurposesId, Long glossaryOfMeasureUnitId);

	@Query(value="SELECT fmu.*\r\n" +
			"  FROM public.filled_measure_units fmu WHERE is_saved = false AND "
			+ "measure_units_purposes_id = ?1 AND glossary_of_measure_units_id=?2  "
			+ "ORDER BY creation_date ASC LIMIT 1;", nativeQuery = true)
	FilledMeasureUnits findFirstUnSavedFilledMeasureUnit(Long measureUnitPurposesId, Long glossaryOfMeasureUnitId);
	
}
