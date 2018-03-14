package rog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import rog.domain.Role;
import rog.repository.RoleRepository;

public class RoleService {

    private final Logger log = LoggerFactory.getLogger(RoleService.class);

    @Autowired
    private RoleRepository roleRepository;

    /**
     * Save a filledRisks.
     *
     * @param filledRisks the entity to save
     * @return the persisted entity
     */
    @Transactional
    public Role save(Role filledRisks) {
        log.debug("Request to save Role : {}", filledRisks);
        return roleRepository.save(filledRisks);
    }

    /**
     *  Get all the filledRisks.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Role> findAll(Pageable pageable) {
        log.debug("Request to get all Role");
        return roleRepository.findAll(pageable);
    }

    /**
     *  Get one filledRisks by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Role findOne(Long id) {
        log.debug("Request to get Role : {}", id);
        return roleRepository.findOne(id);
    }

    /**
     *  Delete the  filledRisks by id.
     *
     *  @param id the id of the entity
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Role : {}", id);
        roleRepository.delete(id);
    }
}
