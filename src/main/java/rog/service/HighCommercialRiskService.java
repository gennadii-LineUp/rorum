package rog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rog.domain.HighCommercialRisk;
import rog.repository.HighCommercialRiskRepository;

import java.util.List;

/**
 * Service Implementation for managing HighCommercialRisk.
 */
@Service
@Transactional
public class HighCommercialRiskService {

    private final Logger log = LoggerFactory.getLogger(HighCommercialRiskService.class);

    private final HighCommercialRiskRepository highCommercialRiskRepository;

    public HighCommercialRiskService(HighCommercialRiskRepository highCommercialRiskRepository) {
        this.highCommercialRiskRepository = highCommercialRiskRepository;
    }

    /**
     * Save a highCommercialRisk.
     *
     * @param highCommercialRisk the entity to save
     * @return the persisted entity
     */
    public HighCommercialRisk save(HighCommercialRisk highCommercialRisk) {
        log.debug("Request to save HighCommercialRisk : {}", highCommercialRisk);
        return highCommercialRiskRepository.save(highCommercialRisk);
    }

    /**
     *  Get all the highCommercialRisks.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<HighCommercialRisk> findAll() {
        log.debug("Request to get all HighCommercialRisks");
        return highCommercialRiskRepository.findAllWithEagerRelationships();
    }

    /**
     *  Get one highCommercialRisk by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public HighCommercialRisk findOne(Long id) {
        log.debug("Request to get HighCommercialRisk : {}", id);
        return highCommercialRiskRepository.findOneWithEagerRelationships(id);
    }

    /**
     *  Delete the  highCommercialRisk by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete HighCommercialRisk : {}", id);
        highCommercialRiskRepository.delete(id);
    }
}
