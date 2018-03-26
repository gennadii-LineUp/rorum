package rog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rog.domain.GlossaryOfMeasureUnits;
import rog.repository.GlossaryOfMeasureUnitsRepository;

import java.util.ArrayList;
import java.util.List;


/**
 * Service Implementation for managing GlossaryOfMeasureUnits.
 */
@Service
@Transactional
public class GlossaryOfMeasureUnitsService {

    private final Logger log = LoggerFactory.getLogger(GlossaryOfMeasureUnitsService.class);

    @Autowired
    private GlossaryOfMeasureUnitsRepository glossaryOfMeasureUnitsRepository;

    @Autowired
    private GlossaryManagementService glossaryManagementService;
    /**
     * Save a glossaryOfMeasureUnits.
     *
     * @param glossaryOfMeasureUnits the entity to save
     * @return the persisted entity
     */
    public GlossaryOfMeasureUnits save(GlossaryOfMeasureUnits glossaryOfMeasureUnits) {
        log.debug("Request to save GlossaryOfMeasureUnits : {}", glossaryOfMeasureUnits);
        return glossaryOfMeasureUnitsRepository.save(glossaryOfMeasureUnits);
    }

    /**
     *  Get all the glossaryOfMeasureUnits.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<GlossaryOfMeasureUnits> findAll(Pageable pageable) {
        log.debug("Request to get all GlossaryOfMeasureUnits");
        return glossaryOfMeasureUnitsRepository.findAll(pageable);
    }

    /**
     *  Get one glossaryOfMeasureUnits by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public GlossaryOfMeasureUnits findOne(Long id) {
        log.debug("Request to get GlossaryOfMeasureUnits : {}", id);
        return glossaryOfMeasureUnitsRepository.findOne(id);
    }

    /**
     *  Delete the  glossaryOfMeasureUnits by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete GlossaryOfMeasureUnits : {}", id);
        glossaryOfMeasureUnitsRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public List<GlossaryOfMeasureUnits> getAllOfCurrentOrganisationByGlossaryOfPurposesId(Long purposeId){
        return glossaryOfMeasureUnitsRepository.getAllOfCurrentOrganisationByGlossaryOfPurposesId(purposeId);
    }
    @Transactional(readOnly = true)
    public List<GlossaryOfMeasureUnits> findAll() {
        log.debug("Request to get all GlossaryOfMeasureUnits without pagination");
        return glossaryOfMeasureUnitsRepository.findAll();
    }
    @Transactional(readOnly = true)
    public List<GlossaryOfMeasureUnits> findAllUserMeasureUnits() {
        List<GlossaryOfMeasureUnits> result = new ArrayList<>();
        glossaryManagementService.getAllAssignmentToCellOfCurrentOrganisation()
            .forEach(gop -> result.addAll(glossaryOfMeasureUnitsRepository.getAllOfCurrentOrganisationByGlossaryOfPurposesId(gop.getId())));
        return result;
    }
}
