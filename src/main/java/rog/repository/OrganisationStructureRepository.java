package rog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rog.domain.OrganisationStructure;
import rog.domain.enumeration.SpecifyingCells;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the OrganisationStructure entity.
 */
@Repository
public interface OrganisationStructureRepository extends JpaRepository<OrganisationStructure, Long>{

    @Query("SELECT os FROM OrganisationStructure os WHERE os.parentId = :oId OR " +
        "os.parentId IN (SELECT os1 FROM OrganisationStructure os1 WHERE os.parentId = os1.supervisoryUnitId) OR " +
        "os.supervisoryUnitId = :oId")
    List<OrganisationStructure> getListOfPossibleSendersOrganisationId(@Param("oId") Long id);

    @Query("SELECT os FROM OrganisationStructure os WHERE os.parentId = :oId")
    List<OrganisationStructure> getListOfParentedOrganisations(@Param("oId") Long id);

    @Query("SELECT os FROM OrganisationStructure os " +
        "WHERE os.supervisoryUnitId IN (SELECT os1.id FROM OrganisationStructure os1 WHERE os1.parentId = :oId)")
    List<OrganisationStructure> getListOfSupervisingOrganisations(@Param("oId") Long id);

    @Query("SELECT o FROM OrganisationStructure o " +
        "WHERE o.id IN( CASE WHEN (:specifyingCells = 'JEDNOSTKAORGANIZACYJNA' " +
        "OR :specifyingCells = 'JEDNOSTKAWYKONAWCZA') " +
        "THEN (CASE WHEN EXISTS (SELECT os.id FROM OrganisationStructure os WHERE " +
        "os.id = :supervisorId AND (os.specifyingCells = 'BIURO' OR os.specifyingCells = 'URZAD')) " +
        "THEN (SELECT os.id FROM OrganisationStructure os " +
        "WHERE  os.id = :supervisorId AND (os.specifyingCells = 'BIURO' OR os.specifyingCells = 'URZAD')) " +
        "ELSE (SELECT os.id FROM OrganisationStructure os " +
        "WHERE os.id IN (SELECT os.parentId FROM OrganisationStructure os WHERE os.id = :supervisorId " +
        "AND os.specifyingCells != 'BIURO' AND os.specifyingCells != 'URZAD')) END) " +
        "ELSE (SELECT os.id FROM OrganisationStructure os WHERE os.id = :parentId) END)")
    Optional<OrganisationStructure> getOneParentOrSupervisorOrganisation(@Param("specifyingCells") String specifyingCells,
                                                                         @Param("parentId") Long parentId,
                                                                         @Param("supervisorId") Long supervisorId);

    @Query("SELECT u.organisationStructure.specifyingCells FROM User u WHERE u.login = ?#{principal.username}")
    SpecifyingCells getSpecifyingCellsOfCurrentOrganisation();

    @Query("SELECT u.organisationStructure FROM User u WHERE u.login = ?#{principal.username}")
    OrganisationStructure getOneByCurrentUser();

    List<OrganisationStructure> findAllByGlossaryOfProcessesId(Long id);

    @Query(value = "SELECT so.* FROM public.\"getAllParentedAndSupervisoredOrganisationStructure\"(?1) so ORDER BY specifying_cells DESC", nativeQuery = true)
    List<OrganisationStructure> getAllParentedOrSupervisoredOrganisationStructures(int organisationStructureId);
}
