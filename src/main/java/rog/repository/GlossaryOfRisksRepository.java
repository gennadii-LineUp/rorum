package rog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rog.domain.GlossaryOfRisks;

import java.util.List;


/**
 * Spring Data JPA repository for the GlossaryOfRisks entity.
 */

@Repository
public interface GlossaryOfRisksRepository extends JpaRepository<GlossaryOfRisks, Long> {

    @Query("SELECT r FROM GlossaryOfRisks r WHERE r.glossaryOfPurposes.id = :pId AND " +
        "(r.isChecked IS NULL OR r.isChecked = TRUE) AND " +
        "(r.organisationStructure.id IS NULL OR r.organisationStructure.id = " +
        "(SELECT u.organisationStructure.id FROM User u WHERE u.login = ?#{principal.username}))")
    List<GlossaryOfRisks> getAllOfCurrentOrganisationByGlossaryOfPurposesId(@Param("pId") Long id);

    @Query("SELECT r FROM GlossaryOfRisks r WHERE r.isChecked = TRUE AND r.organisationStructure.id IS NOT NULL")
    List<GlossaryOfRisks> getAllConfirmedByLocalAdmin();

    @Query("SELECT r FROM GlossaryOfRisks r WHERE r.isChecked = FALSE")
    List<GlossaryOfRisks> getAllUnconfirmed();

    @Query("SELECT r FROM GlossaryOfRisks r WHERE r.organisationStructure.id IS NULL")
    List<GlossaryOfRisks> getAllConfirmedByGlobalAdmin();
}
