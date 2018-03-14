package rog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rog.domain.FilledCommercialRisks;


/**
 * Spring Data JPA repository for the FilledCommercialRisks entity.
 */

@Repository
public interface FilledCommercialRisksRepository extends JpaRepository<FilledCommercialRisks, Long> {

}
