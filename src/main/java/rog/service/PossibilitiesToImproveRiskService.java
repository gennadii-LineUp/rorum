package rog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rog.domain.PossibilitiesToImproveRisk;
import rog.repository.PossibilitiesToImproveRiskRepository;

import java.util.List;

/**
 * Service Implementation for managing PossibilitiesToImproveRisk.
 */
@Service
public class PossibilitiesToImproveRiskService {

    private final Logger log = LoggerFactory.getLogger(PossibilitiesToImproveRiskService.class);

    @Autowired
    private PossibilitiesToImproveRiskRepository possibilitiesToImproveRiskRepository;

    /**
     * Save a possibilitiesToImproveRisk.
     *
     * @param possibilitiesToImproveRisk the entity to save
     * @return the persisted entity
     */
    @Transactional
    public PossibilitiesToImproveRisk save(PossibilitiesToImproveRisk possibilitiesToImproveRisk) {
        log.debug("Request to save PossibilitiesToImproveRisk : {}", possibilitiesToImproveRisk);
        return possibilitiesToImproveRiskRepository.save(possibilitiesToImproveRisk);
    }

    /**
     *  Get all the possibilitiesToImproveRisks.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PossibilitiesToImproveRisk> findAll() {
        log.debug("Request to get all PossibilitiesToImproveRisks");
        return possibilitiesToImproveRiskRepository.findAll();
    }

    /**
     *  Get one possibilitiesToImproveRisk by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PossibilitiesToImproveRisk findOne(Long id) {
        log.debug("Request to get PossibilitiesToImproveRisk : {}", id);
        return possibilitiesToImproveRiskRepository.findOne(id);
    }

    /**
     *  Delete the  possibilitiesToImproveRisk by id.
     *
     *  @param id the id of the entity
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete PossibilitiesToImproveRisk : {}", id);
        possibilitiesToImproveRiskRepository.delete(id);
    }
}
