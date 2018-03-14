package rog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rog.domain.FilledRisks;
import rog.repository.FilledRisksRepository;


/**
 * Service Implementation for managing FilledRisks.
 */
@Service
@Transactional
public class FilledRisksService {

    private final Logger log = LoggerFactory.getLogger(FilledRisksService.class);

    @Autowired
    private FilledRisksRepository filledRisksRepository;

    /**
     * Save a filledRisks.
     *
     * @param filledRisks the entity to save
     * @return the persisted entity
     */
    public FilledRisks save(FilledRisks filledRisks) {
        log.debug("Request to save FilledRisks : {}", filledRisks);
        return filledRisksRepository.save(filledRisks);
    }

    /**
     *  Get all the filledRisks.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FilledRisks> findAll(Pageable pageable) {
        log.debug("Request to get all FilledRisks");
        return filledRisksRepository.findAll(pageable);
    }

    /**
     *  Get one filledRisks by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public FilledRisks findOne(Long id) {
        log.debug("Request to get FilledRisks : {}", id);
        return filledRisksRepository.findOne(id);
    }

    /**
     *  Delete the  filledRisks by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete FilledRisks : {}", id);
        filledRisksRepository.delete(id);
    }
    
    @Transactional(readOnly = true)
    public FilledRisks findLastFilledRisk(Long risksPurposesId, Long glossaryOfRisksId) {
    	return filledRisksRepository.findLastFilledRisk(risksPurposesId, glossaryOfRisksId);
    }
    
    @Transactional(readOnly = true)
    public FilledRisks findFirstUnSavedFilledRisk(Long risksPurposesId, Long glossaryOfRisksId) {
    	return filledRisksRepository.findFirstUnSavedFilledRisk(risksPurposesId, glossaryOfRisksId);
    }
}
