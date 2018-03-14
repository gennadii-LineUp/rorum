package rog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rog.domain.GlossaryOfProcesses;

import java.util.ArrayList;


/**
 * Spring Data JPA repository for the GlossaryOfProcesses entity.
 */

@Repository
public interface GlossaryOfProcessesRepository extends JpaRepository<GlossaryOfProcesses, Long> {

    @Query("SELECT gp.id FROM GlossaryOfProcesses gp JOIN gp.organisationStructures o " +
        "JOIN User u ON u.organisationStructure.id = o.id JOIN SetOfSentPurposes ssp ON ssp.user.id = u.id " +
        "WHERE ssp.id = :sspId")
    Long getIdBySetOfSentPurposesId(@Param("sspId") Long setOfSentPurposesId);

    GlossaryOfProcesses findOneByGlossaryOfPurposesId(Long id);
    
    public ArrayList<GlossaryOfProcesses> findAll();

}
