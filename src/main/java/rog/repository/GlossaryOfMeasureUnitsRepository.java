package rog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rog.domain.GlossaryOfMeasureUnits;

import java.util.List;


/**
 * Spring Data JPA repository for the GlossaryOfMeasureUnits entity.
 */

@Repository
public interface GlossaryOfMeasureUnitsRepository extends JpaRepository<GlossaryOfMeasureUnits, Long> {

    @Query("SELECT mu FROM GlossaryOfMeasureUnits mu " +
        "WHERE mu.glossaryOfPurposes.id = :pId AND (mu.isChecked IS NULL OR mu.isChecked = TRUE) AND " +
        "(mu.organisationStructure.id IS NULL OR mu.organisationStructure.id = " +
        "(SELECT u.organisationStructure.id FROM User u WHERE u.login = ?#{principal.username}))")
    List<GlossaryOfMeasureUnits> getAllOfCurrentOrganisationByGlossaryOfPurposesId(@Param("pId") Long purposeId);

    @Query("SELECT mu FROM GlossaryOfMeasureUnits mu WHERE mu.isChecked = TRUE AND mu.organisationStructure.id IS NOT NULL")
    List<GlossaryOfMeasureUnits> getAllConfirmedByLocalAdmin();

    @Query("SELECT mu FROM GlossaryOfMeasureUnits mu WHERE mu.isChecked = FALSE")
    List<GlossaryOfMeasureUnits> getAllUnconfirmed();

    @Query("SELECT mu FROM GlossaryOfMeasureUnits mu WHERE mu.organisationStructure.id IS NULL")
    List<GlossaryOfMeasureUnits> getAllConfirmedByGlobalAdmin();

}
