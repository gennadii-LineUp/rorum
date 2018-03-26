package rog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rog.domain.GlossaryOfPurposes;
import rog.domain.OrganisationStructure;
import rog.domain.enumeration.AssignmentToCell;
import rog.domain.enumeration.SpecifyingCells;
import rog.repository.GlossaryOfProcessesRepository;
import rog.repository.GlossaryOfPurposesRepository;
import rog.repository.OrganisationStructureRepository;
import rog.repository.SetOfSentPurposesRepository;

import javax.ws.rs.InternalServerErrorException;
import java.util.List;
import java.util.Optional;
import java.util.Set;


/**
 * Service Implementation for managing GlossaryOfPurposes.
 */
@Service
@Transactional
public class GlossaryOfPurposesService {

    private final Logger log = LoggerFactory.getLogger(GlossaryOfPurposesService.class);

    @Autowired
    private GlossaryOfPurposesRepository glossaryOfPurposesRepository;

    @Autowired
    private GlossaryOfProcessesRepository glossaryOfProcessesRepository;

    @Autowired
    private OrganisationStructureRepository organisationStructureRepository;

    @Autowired
    private SetOfSentPurposesRepository setOfSentPurposesRepository;

    /**
     * Save a glossaryOfPurposes.
     *
     * @param glossaryOfPurposes the entity to save
     * @return the persisted entity
     */
    public GlossaryOfPurposes save(GlossaryOfPurposes glossaryOfPurposes) {
        log.debug("Request to save GlossaryOfPurposes : {}", glossaryOfPurposes);
        return glossaryOfPurposesRepository.save(glossaryOfPurposes);
    }

    /**
     *  Get all the glossaryOfPurposes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<GlossaryOfPurposes> findAll(Pageable pageable) {
        log.debug("Request to get all GlossaryOfPurposes");
        return glossaryOfPurposesRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<GlossaryOfPurposes> findAll() {
        log.debug("Request to get all GlossaryOfPurposes without pagination");
        return glossaryOfPurposesRepository.findAll();
    }

    /**
     *  Get one glossaryOfPurposes by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public GlossaryOfPurposes findOne(Long id) {
        log.debug("Request to get GlossaryOfPurposes : {}", id);
        return glossaryOfPurposesRepository.findOne(id);
    }

    /**
     *  Delete the  glossaryOfPurposes by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete GlossaryOfPurposes : {}", id);
        glossaryOfPurposesRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public List<GlossaryOfPurposes> getAllByOrganisationId(Long organisationId){
        return glossaryOfPurposesRepository.getAllByOrganisationId(organisationId);
    }

    @Transactional(readOnly = true)
    public List<GlossaryOfPurposes> getAllAssignmentToCellOfCurrentOrganisation(){

        OrganisationStructure organisationOfUser = organisationStructureRepository.getOneByCurrentUser();

        String specifyingCells = organisationOfUser.getSpecifyingCells().name();
        Long parentId = organisationOfUser.getParentId();
        Long supervisorId = organisationOfUser.getSupervisoryUnitId();

        Optional<OrganisationStructure> organisationOfAdmin =
            organisationStructureRepository.getOneParentOrSupervisorOrganisation(specifyingCells, parentId, supervisorId);

        Long processId = organisationOfUser.getGlossaryOfProcesses().getId();

        SpecifyingCells specifyingCellsOfAdmin = organisationOfAdmin
            .orElseThrow(InternalServerErrorException::new).getSpecifyingCells();
        AssignmentToCell assignmentToCellOfAdmin = AssignmentToCell.valueOf(specifyingCellsOfAdmin.name());

        return glossaryOfPurposesRepository.getAllAssignmentToCellByOrganisationId(processId, assignmentToCellOfAdmin);
    }

    @Transactional(readOnly = true)
    public List<GlossaryOfPurposes> getAllBySetOfSentPurposes(Long setOfSentPurposesId){

        Long processId = glossaryOfProcessesRepository.getIdBySetOfSentPurposesId(setOfSentPurposesId);

        SpecifyingCells specifyingCells = organisationStructureRepository.getSpecifyingCellsOfCurrentOrganisation();
        AssignmentToCell assignmentToCell = AssignmentToCell.valueOf(specifyingCells.name());

        return glossaryOfPurposesRepository.getAllAssignmentToCellByOrganisationId(processId, assignmentToCell);
    }

    @Transactional(readOnly = true)
    public Set<GlossaryOfPurposes> findOneByOrderIdAndUserId(Long orderId, Long userId) {
        return setOfSentPurposesRepository.findOneByOrdersIdAndUserId(orderId, userId).getGlossaryOfPurposes();
    }

    @Transactional(readOnly = true)
    public List<GlossaryOfPurposes> findAllByNullParentId() {
        return glossaryOfPurposesRepository.findAllByParentId(null);
    }

}
