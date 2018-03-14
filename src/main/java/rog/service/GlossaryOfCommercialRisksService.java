package rog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rog.domain.GlossaryOfCommercialRisks;
import rog.repository.GlossaryOfCommercialRisksRepository;

import java.util.List;


/**
 * Service Implementation for managing GlossaryOfCommercialRisks.
 */
@Service
@Transactional
public class GlossaryOfCommercialRisksService {

    private final Logger log = LoggerFactory.getLogger(GlossaryOfCommercialRisksService.class);

    @Autowired
    private GlossaryOfCommercialRisksRepository glossaryOfCommercialRisksRepository;

    /**
     * Save a glossaryOfCommercialRisks.
     *
     * @param glossaryOfCommercialRisks the entity to save
     * @return the persisted entity
     */
    public GlossaryOfCommercialRisks save(GlossaryOfCommercialRisks glossaryOfCommercialRisks) {
        log.debug("Request to save GlossaryOfCommercialRisks : {}", glossaryOfCommercialRisks);
        return glossaryOfCommercialRisksRepository.save(glossaryOfCommercialRisks);
    }

    /**
     *  Get all the glossaryOfCommercialRisks.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<GlossaryOfCommercialRisks> findAll(Pageable pageable) {
        log.debug("Request to get all GlossaryOfCommercialRisks");
        return glossaryOfCommercialRisksRepository.findAll(pageable);
    }

    /**
     *  Get one glossaryOfCommercialRisks by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public GlossaryOfCommercialRisks findOne(Long id) {
        log.debug("Request to get GlossaryOfCommercialRisks : {}", id);
        return glossaryOfCommercialRisksRepository.findOne(id);
    }

    /**
     *  Delete the  glossaryOfCommercialRisks by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete GlossaryOfCommercialRisks : {}", id);
        glossaryOfCommercialRisksRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public List<GlossaryOfCommercialRisks> getAllOfCurrentOrganisation(){
        return glossaryOfCommercialRisksRepository.getAllOfCurrentOrganisation();
    }

    @Transactional(readOnly = true)
    public List<GlossaryOfCommercialRisks> findByUserIsCurrentUser(){
        return glossaryOfCommercialRisksRepository.findByUserIsCurrentUser();
    }
}
