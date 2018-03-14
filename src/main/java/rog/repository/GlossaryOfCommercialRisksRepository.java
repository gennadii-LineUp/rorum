package rog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rog.domain.GlossaryOfCommercialRisks;

import java.util.List;

/**
 * Spring Data JPA repository for the GlossaryOfCommercialRisks entity.
 */

@Repository
public interface GlossaryOfCommercialRisksRepository extends JpaRepository<GlossaryOfCommercialRisks, Long> {

    @Query("select glossary_of_commercial_risks from GlossaryOfCommercialRisks glossary_of_commercial_risks where glossary_of_commercial_risks.user.login = ?#{principal.username}")
    List<GlossaryOfCommercialRisks> findByUserIsCurrentUser();

    @Query("SELECT cr FROM GlossaryOfCommercialRisks cr " +
        "WHERE (cr.isChecked IS NULL OR cr.isChecked = TRUE) AND " +
        "(cr.organisationStructure.id IS NULL OR cr.organisationStructure.id = " +
        "(SELECT u.organisationStructure.id FROM User u WHERE u.login = ?#{principal.username}))")
    List<GlossaryOfCommercialRisks> getAllOfCurrentOrganisation();

    @Query("SELECT cr FROM GlossaryOfCommercialRisks cr WHERE cr.isChecked = TRUE")
    List<GlossaryOfCommercialRisks> getAllConfirmedByLocalAdmin();

    @Query("SELECT cr FROM GlossaryOfCommercialRisks cr WHERE cr.isChecked = FALSE")
    List<GlossaryOfCommercialRisks> getAllUnconfirmed();

}
