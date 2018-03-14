package rog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rog.domain.GlossaryOfProcesses;
import rog.repository.GlossaryOfProcessesRepository;

import java.util.ArrayList;
import java.util.List;


/**
 * Service Implementation for managing GlossaryOfProcesses.
 */
@Service
@Transactional
public class GlossaryOfProcessesService {

    private final Logger log = LoggerFactory.getLogger(GlossaryOfProcessesService.class);

    @Autowired
    private GlossaryOfProcessesRepository glossaryOfProcessesRepository;

    /**
     * Save a glossaryOfProcesses.
     *
     * @param glossaryOfProcesses the entity to save
     * @return the persisted entity
     */
    public GlossaryOfProcesses save(GlossaryOfProcesses glossaryOfProcesses) {
        log.debug("Request to save GlossaryOfProcesses : {}", glossaryOfProcesses);
        return glossaryOfProcessesRepository.save(glossaryOfProcesses);
    }

    /**
     *  Get all the glossaryOfProcesses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<GlossaryOfProcesses> findAll(Pageable pageable) {
        log.debug("Request to get all GlossaryOfProcesses");
        return glossaryOfProcessesRepository.findAll(pageable);
    }

    /**
     *  Get one glossaryOfProcesses by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public GlossaryOfProcesses findOne(Long id) {
        log.debug("Request to get GlossaryOfProcesses : {}", id);
        return glossaryOfProcessesRepository.findOne(id);
    }

    /**
     *  Delete the  glossaryOfProcesses by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete GlossaryOfProcesses : {}", id);
        glossaryOfProcessesRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public List<GlossaryOfProcesses> findAll(){
    	return glossaryOfProcessesRepository.findAll();
    }
}
