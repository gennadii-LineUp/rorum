package rog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rog.domain.DecisionForRisk;
import rog.repository.DecisionForRiskRepository;

import java.util.List;

/**
 * Service Implementation for managing DecisionForRisk.
 */
@Service
@Transactional
public class DecisionForRiskService {

    private final Logger log = LoggerFactory.getLogger(DecisionForRiskService.class);

    @Autowired
    private DecisionForRiskRepository decisionForRiskRepository;

    /**
     * Save a decisionForRisk.
     *
     * @param decisionForRisk the entity to save
     * @return the persisted entity
     */
    public DecisionForRisk save(DecisionForRisk decisionForRisk) {
        log.debug("Request to save DecisionForRisk : {}", decisionForRisk);
        return decisionForRiskRepository.save(decisionForRisk);
    }

    /**
     *  Get all the decisionForRisks.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<DecisionForRisk> findAll() {
        log.debug("Request to get all DecisionForRisks");
        return decisionForRiskRepository.findAll();
    }

    /**
     *  Get one decisionForRisk by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public DecisionForRisk findOne(Long id) {
        log.debug("Request to get DecisionForRisk : {}", id);
        return decisionForRiskRepository.findOne(id);
    }

    /**
     *  Delete the  decisionForRisk by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DecisionForRisk : {}", id);
        decisionForRiskRepository.delete(id);
    }
}
