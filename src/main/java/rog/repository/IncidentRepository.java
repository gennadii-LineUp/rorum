package rog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rog.domain.Incident;

import java.util.List;


/**
 * Spring Data JPA repository for the Incident entity.
 */

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {

    @Query("SELECT i FROM Incident i JOIN User u ON u.id = i.user.id " +
        "WHERE u.organisationStructure.id IN " +
        "(SELECT os.id FROM OrganisationStructure os WHERE os.parentId = :oId)")
    List<Incident> getAllOfParentedOrganisation(@Param("oId") Long id);

    @Query("SELECT i FROM Incident i JOIN User u ON u.id = i.user.id " +
        "WHERE u.organisationStructure.id IN " +
        "(SELECT os FROM OrganisationStructure os WHERE os.supervisoryUnitId IN " +
        "(SELECT os1.id FROM OrganisationStructure os1 WHERE os1.parentId = :oId))")
    List<Incident> getAllOfSupervisingOrganisation(@Param("oId") Long id);

    List<Incident> findAllByUserIdAndSetOfSentPurposesId(Long userId, Long setOfSentPurposesId);

    @Query(value = "SELECT i.set_of_sent_purposes_id FROM Incident i WHERE i.id = ?1", nativeQuery = true)
    Long getSetOfSentPurposesIdByIncidentId(Long id);

}
