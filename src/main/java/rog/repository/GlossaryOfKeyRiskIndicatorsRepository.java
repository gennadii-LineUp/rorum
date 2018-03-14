package rog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rog.domain.GlossaryOfKeyRiskIndicators;

import java.util.List;

@Repository
public interface GlossaryOfKeyRiskIndicatorsRepository extends JpaRepository<GlossaryOfKeyRiskIndicators, Long> {

    @Query("SELECT gkri FROM GlossaryOfKeyRiskIndicators gkri WHERE gkri.glossaryOfPurposes.id = :pId")
    List<GlossaryOfKeyRiskIndicators> getAllByPurposeId(@Param("pId") Long purposeId);

}
