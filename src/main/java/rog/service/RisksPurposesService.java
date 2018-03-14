package rog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rog.domain.RisksPurposes;
import rog.repository.RisksPurposesRepository;


/**
 * Service Implementation for managing RisksPurposes.
 */
@Service
@Transactional
public class RisksPurposesService {

    private final Logger log = LoggerFactory.getLogger(RisksPurposesService.class);

    @Autowired
    private RisksPurposesRepository risksPurposesRepository;

    /**
     * Save a risksPurposes.
     *
     * @param risksPurposes the entity to save
     * @return the persisted entity
     */
    public RisksPurposes save(RisksPurposes risksPurposes) {
        log.debug("Request to save RisksPurposes : {}", risksPurposes);
        return risksPurposesRepository.save(risksPurposes);
    }

    /**
     *  Get all the risksPurposes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RisksPurposes> findAll(Pageable pageable) {
        log.debug("Request to get all RisksPurposes");
        return risksPurposesRepository.findAll(pageable);
    }

    /**
     *  Get one risksPurposes by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public RisksPurposes findOne(Long id) {
        log.debug("Request to get RisksPurposes : {}", id);
        return risksPurposesRepository.findOne(id);
    }

    /**
     *  Delete the  risksPurposes by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RisksPurposes : {}", id);
        risksPurposesRepository.delete(id);
    }
}
