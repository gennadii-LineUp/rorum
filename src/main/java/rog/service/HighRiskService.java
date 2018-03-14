package rog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rog.domain.HighRisk;
import rog.repository.HighRiskRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing HighRisk.
 */
@Service
@Transactional
public class HighRiskService {

    private final Logger log = LoggerFactory.getLogger(HighRiskService.class);

    private final HighRiskRepository highRiskRepository;

    public HighRiskService(HighRiskRepository highRiskRepository) {
        this.highRiskRepository = highRiskRepository;
    }

    /**
     * Save a highRisk.
     *
     * @param highRisk the entity to save
     * @return the persisted entity
     */
    public HighRisk save(HighRisk highRisk) {
        log.debug("Request to save HighRisk : {}", highRisk);
        return highRiskRepository.save(highRisk);
    }

    /**
     *  Get all the highRisks.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<HighRisk> findAll() {
        log.debug("Request to get all HighRisks");
        return highRiskRepository.findAllWithEagerRelationships();
    }


    /**
     *  get all the highRisks where FilledRisks is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<HighRisk> findAllWhereFilledRisksIsNull() {
        log.debug("Request to get all highRisks where FilledRisks is null");
        return highRiskRepository.findAll().stream()
            .filter(highRisk -> highRisk.getFilledRisks() == null)
            .collect(Collectors.toList());
    }

    /**
     *  Get one highRisk by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public HighRisk findOne(Long id) {
        log.debug("Request to get HighRisk : {}", id);
        return highRiskRepository.findOneWithEagerRelationships(id);
    }

    /**
     *  Delete the  highRisk by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete HighRisk : {}", id);
        highRiskRepository.delete(id);
    }
}
