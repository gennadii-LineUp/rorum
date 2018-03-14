package rog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rog.domain.GlossaryOfKeyRiskIndicators;
import rog.repository.GlossaryOfKeyRiskIndicatorsRepository;

import java.util.List;

/**
 * Service Implementation for managing GlossaryOfKeyRiskIndicatorsDTO.
 */
@Service
@Transactional
public class GlossaryOfKeyRiskIndicatorsService {

    private final Logger log = LoggerFactory.getLogger(GlossaryOfKeyRiskIndicatorsService.class);

    @Autowired
    private GlossaryOfKeyRiskIndicatorsRepository glossaryOfKeyRiskIndicatorsRepository;

    /**
     * Save a glossaryOfKeyRiskIndicators.
     *
     * @param glossaryOfKeyRiskIndicators the entity to save
     * @return the persisted entity
     */
    public GlossaryOfKeyRiskIndicators save(GlossaryOfKeyRiskIndicators glossaryOfKeyRiskIndicators) {
        log.debug("Request to save GlossaryOfKeyRiskIndicatorsDTO : {}", glossaryOfKeyRiskIndicators);
        return glossaryOfKeyRiskIndicatorsRepository.save(glossaryOfKeyRiskIndicators);
    }

    /**
     *  Get all the glossaryOfKeyRiskIndicators.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<GlossaryOfKeyRiskIndicators> findAll() {
        log.debug("Request to get all GlossaryOfKeyRiskIndicatorsDTO");
        return glossaryOfKeyRiskIndicatorsRepository.findAll();
    }

    /**
     *  Get one glossaryOfKeyRiskIndicators by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public GlossaryOfKeyRiskIndicators findOne(Long id) {
        log.debug("Request to get GlossaryOfKeyRiskIndicatorsDTO : {}", id);
        return glossaryOfKeyRiskIndicatorsRepository.findOne(id);
    }

    /**
     *  Delete the  glossaryOfKeyRiskIndicators by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete GlossaryOfKeyRiskIndicatorsDTO : {}", id);
        glossaryOfKeyRiskIndicatorsRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public List<GlossaryOfKeyRiskIndicators> getAllByPurposeId(Long purposeId){
        return glossaryOfKeyRiskIndicatorsRepository.getAllByPurposeId(purposeId);
    }
}
