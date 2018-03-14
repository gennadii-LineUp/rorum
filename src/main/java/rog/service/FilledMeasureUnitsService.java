package rog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rog.domain.FilledMeasureUnits;
import rog.repository.FilledMeasureUnitsRepository;


/**
 * Service Implementation for managing FilledMeasureUnits.
 */
@Service
@Transactional
public class FilledMeasureUnitsService {

    private final Logger log = LoggerFactory.getLogger(FilledMeasureUnitsService.class);

    @Autowired
    private FilledMeasureUnitsRepository filledMeasureUnitsRepository;

    /**
     * Save a filledMeasureUnits.
     *
     * @param filledMeasureUnits the entity to save
     * @return the persisted entity
     */
    public FilledMeasureUnits save(FilledMeasureUnits filledMeasureUnits) {
        log.debug("Request to save FilledMeasureUnits : {}", filledMeasureUnits);
        return filledMeasureUnitsRepository.save(filledMeasureUnits);
    }

    /**
     *  Get all the filledMeasureUnits.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FilledMeasureUnits> findAll(Pageable pageable) {
        log.debug("Request to get all FilledMeasureUnits");
        return filledMeasureUnitsRepository.findAll(pageable);
    }

    /**
     *  Get one filledMeasureUnits by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public FilledMeasureUnits findOne(Long id) {
        log.debug("Request to get FilledMeasureUnits : {}", id);
        return filledMeasureUnitsRepository.findOne(id);
    }

    /**
     *  Delete the  filledMeasureUnits by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete FilledMeasureUnits : {}", id);
        filledMeasureUnitsRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public FilledMeasureUnits findLastFilledMeasureUnit(Long measureUnitPurposesId, Long glossaryOfMeasureUnitId) {
    	return filledMeasureUnitsRepository.findLastFilledMeasureUnit(measureUnitPurposesId, glossaryOfMeasureUnitId);
    }

    @Transactional(readOnly = true)
    public FilledMeasureUnits findFirstUnSavedFilledMeasureUnit(Long measureUnitPurposesId, Long glossaryOfMeasureUnitId) {
    	return filledMeasureUnitsRepository.findFirstUnSavedFilledMeasureUnit(measureUnitPurposesId, glossaryOfMeasureUnitId);
    }

}
