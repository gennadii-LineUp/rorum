package rog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rog.domain.GlossaryOfPurposes;
import rog.domain.enumeration.AssignmentToCell;

import java.util.List;


/**
 * Spring Data JPA repository for the GlossaryOfPurposes entity.
 */

@Repository
public interface GlossaryOfPurposesRepository extends JpaRepository<GlossaryOfPurposes, Long> {

    @Query("SELECT p FROM GlossaryOfPurposes p JOIN p.glossaryOfProcesses pr " +
        "JOIN OrganisationStructure os ON os.glossaryOfProcesses.id = pr.id " +
        "WHERE os.id = :orgId AND (p.isChecked IS NULL OR p.isChecked = TRUE)")
    List<GlossaryOfPurposes> getAllByOrganisationId(@Param("orgId") Long organisationId);

    @Query("SELECT p FROM GlossaryOfPurposes p " +
        "WHERE p.glossaryOfProcesses.id = :processId AND p.assignmentToCell = :specifyingCells")
    List<GlossaryOfPurposes> getAllAssignmentToCellByOrganisationId(@Param("processId") Long processId,
                                                                    @Param("specifyingCells") AssignmentToCell specifyingCells);

    @Query("SELECT p FROM GlossaryOfPurposes p WHERE p.isChecked = TRUE AND p.organisationStructure.id IS NOT NULL")
    Page<GlossaryOfPurposes> getAllConfirmedByLocalAdmin(Pageable pageable);

    @Query("SELECT p FROM GlossaryOfPurposes p WHERE p.isChecked = FALSE")
    Page<GlossaryOfPurposes> getAllUnconfirmed(Pageable pageable);

    @Query("SELECT p FROM GlossaryOfPurposes p WHERE p.organisationStructure.id IS NULL")
    Page<GlossaryOfPurposes> getAllConfirmedByGlobalAdmin(Pageable pageable);

    List<GlossaryOfPurposes> findAllByParentId(Long parentId);
}
