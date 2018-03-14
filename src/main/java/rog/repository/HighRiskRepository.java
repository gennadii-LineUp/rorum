package rog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rog.domain.HighRisk;

import java.util.List;

/**
 * Spring Data JPA repository for the HighRisk entity.
 */

@Repository
public interface HighRiskRepository extends JpaRepository<HighRisk, Long> {

    @Query("SELECT DISTINCT hr FROM HighRisk hr LEFT JOIN FETCH hr.possibilitiesToImproveRisks")
    List<HighRisk> findAllWithEagerRelationships();

    @Query("SELECT hr FROM HighRisk hr LEFT JOIN FETCH hr.possibilitiesToImproveRisks WHERE hr.id =:id")
    HighRisk findOneWithEagerRelationships(@Param("id") Long id);

}
