package rog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rog.domain.MeasureUnitsPurposes;
import rog.repository.MeasureUnitsPurposesRepository;


/**
 * Service Implementation for managing MeasureUnitsPurposes.
 */
@Service
@Transactional
public class MeasureUnitsPurposesService {

    private final Logger log = LoggerFactory.getLogger(MeasureUnitsPurposesService.class);

    @Autowired
    private MeasureUnitsPurposesRepository measureUnitsPurposesRepository;

    /**
     * Save a measureUnitsPurposes.
     *
     * @param measureUnitsPurposes the entity to save
     * @return the persisted entity
     */
    public MeasureUnitsPurposes save(MeasureUnitsPurposes measureUnitsPurposes) {
        log.debug("Request to save MeasureUnitsPurposes : {}", measureUnitsPurposes);
        return measureUnitsPurposesRepository.save(measureUnitsPurposes);
    }

    /**
     * Get all the measureUnitsPurposes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MeasureUnitsPurposes> findAll(Pageable pageable) {
        log.debug("Request to get all MeasureUnitsPurposes");
        return measureUnitsPurposesRepository.findAll(pageable);
    }

    /**
     * Get one measureUnitsPurposes by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MeasureUnitsPurposes findOne(Long id) {
        log.debug("Request to get MeasureUnitsPurposes : {}", id);
        return measureUnitsPurposesRepository.findOne(id);
    }

    /**
     * Delete the  measureUnitsPurposes by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MeasureUnitsPurposes : {}", id);
        measureUnitsPurposesRepository.delete(id);
    }
}
