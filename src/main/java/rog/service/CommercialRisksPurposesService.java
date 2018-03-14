package rog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rog.domain.CommercialRisksPurposes;
import rog.repository.CommercialRisksPurposesRepository;


/**
 * Service Implementation for managing CommercialRisksPurposes.
 */
@Service
@Transactional
public class CommercialRisksPurposesService {

    private final Logger log = LoggerFactory.getLogger(CommercialRisksPurposesService.class);

    @Autowired
    private CommercialRisksPurposesRepository commercialRisksPurposesRepository;

    /**
     * Save a commercialRisksPurposes.
     *
     * @param commercialRisksPurposes the entity to save
     * @return the persisted entity
     */
    public CommercialRisksPurposes save(CommercialRisksPurposes commercialRisksPurposes) {
        log.debug("Request to save CommercialRisksPurposes : {}", commercialRisksPurposes);
        return commercialRisksPurposesRepository.save(commercialRisksPurposes);
    }

    /**
     *  Get all the commercialRisksPurposes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommercialRisksPurposes> findAll(Pageable pageable) {
        log.debug("Request to get all CommercialRisksPurposes");
        return commercialRisksPurposesRepository.findAll(pageable);
    }

    /**
     *  Get one commercialRisksPurposes by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CommercialRisksPurposes findOne(Long id) {
        log.debug("Request to get CommercialRisksPurposes : {}", id);
        return commercialRisksPurposesRepository.findOne(id);
    }

    /**
     *  Delete the  commercialRisksPurposes by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CommercialRisksPurposes : {}", id);
        commercialRisksPurposesRepository.delete(id);
    }
}
