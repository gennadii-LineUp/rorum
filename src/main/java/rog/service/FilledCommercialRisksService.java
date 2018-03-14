package rog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rog.domain.FilledCommercialRisks;
import rog.repository.FilledCommercialRisksRepository;


/**
 * Service Implementation for managing FilledCommercialRisks.
 */
@Service
@Transactional
public class FilledCommercialRisksService {

    private final Logger log = LoggerFactory.getLogger(FilledCommercialRisksService.class);

    @Autowired
    private FilledCommercialRisksRepository filledCommercialRisksRepository;

    /**
     * Save a filledCommercialRisks.
     *
     * @param filledCommercialRisks the entity to save
     * @return the persisted entity
     */
    public FilledCommercialRisks save(FilledCommercialRisks filledCommercialRisks) {
        log.debug("Request to save FilledCommercialRisks : {}", filledCommercialRisks);
        return filledCommercialRisksRepository.save(filledCommercialRisks);
    }

    /**
     *  Get all the filledCommercialRisks.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FilledCommercialRisks> findAll(Pageable pageable) {
        log.debug("Request to get all FilledCommercialRisks");
        return filledCommercialRisksRepository.findAll(pageable);
    }

    /**
     *  Get one filledCommercialRisks by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public FilledCommercialRisks findOne(Long id) {
        log.debug("Request to get FilledCommercialRisks : {}", id);
        return filledCommercialRisksRepository.findOne(id);
    }

    /**
     *  Delete the  filledCommercialRisks by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete FilledCommercialRisks : {}", id);
        filledCommercialRisksRepository.delete(id);
    }
}
