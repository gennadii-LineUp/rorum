package rog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rog.domain.HighCommercialRisk;

import java.util.List;

/**
 * Spring Data JPA repository for the HighCommercialRisk entity.
 */

@Repository
public interface HighCommercialRiskRepository extends JpaRepository<HighCommercialRisk, Long> {
    @Query("SELECT DISTINCT hcr FROM HighCommercialRisk hcr LEFT JOIN FETCH hcr.possibilitiesToImproveRisks")
    List<HighCommercialRisk> findAllWithEagerRelationships();

    @Query("SELECT hcr FROM HighCommercialRisk hcr LEFT JOIN FETCH hcr.possibilitiesToImproveRisks WHERE hcr.id =:id")
    HighCommercialRisk findOneWithEagerRelationships(@Param("id") Long id);

}
