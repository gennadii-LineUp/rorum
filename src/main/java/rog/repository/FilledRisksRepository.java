package rog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rog.domain.FilledRisks;


/**
 * Spring Data JPA repository for the FilledRisks entity.
 */

@Repository
public interface FilledRisksRepository extends JpaRepository<FilledRisks, Long> {
	
	@Query(value="SELECT fr.*\r\n" + 
			"  FROM public.filled_risks fr WHERE is_saved = false AND "
			+ "risks_purposes_id = ?1 AND glossary_of_risks_id=?2  "
			+ "ORDER BY creation_date DESC LIMIT 1;", nativeQuery = true)
	FilledRisks findLastFilledRisk(Long risksPurposesId, Long glossaryOfRisksId);

	@Query(value="SELECT fr.*\r\n" + 
			"  FROM public.filled_risks fr WHERE is_saved = false AND "
			+ "risks_purposes_id = ?1 AND glossary_of_risks_id=?2  "
			+ "ORDER BY creation_date ASC LIMIT 1;", nativeQuery = true)
	FilledRisks findFirstUnSavedFilledRisk(Long risksPurposesId, Long glossaryOfRisksId);
	
}
