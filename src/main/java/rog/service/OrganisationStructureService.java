package rog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.primitives.Ints;

import rog.domain.OrganisationStructure;
import rog.domain.User;
import rog.repository.OrganisationStructureRepository;

import java.util.List;


/**
 * Service Implementation for managing OrganisationStructure.
 */
@Service
@Transactional
public class OrganisationStructureService {

    private final Logger log = LoggerFactory.getLogger(OrganisationStructureService.class);

    @Autowired
    private OrganisationStructureRepository organisationStructureRepository;

    @Autowired
    private UserService userService;
    /**
     * Save a organisationStructure.
     *
     * @param organisationStructure the entity to save
     * @return the persisted entity
     */
    public OrganisationStructure save(OrganisationStructure organisationStructure) {
        log.debug("Request to save OrganisationStructure : {}", organisationStructure);
        return organisationStructureRepository.save(organisationStructure);
    }

    /**
     *  Get all the organisationStructures.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<OrganisationStructure> findAll(Pageable pageable) {
        log.debug("Request to get all OrganisationStructures");
        return organisationStructureRepository.findAll(pageable);
    }

    /**
     *  Get all the organisationStructures.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<OrganisationStructure> findAll() {
        log.debug("Request to get all OrganisationStructures");
        return organisationStructureRepository.findAll();
    }

    /**
     *  Get one organisationStructure by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public OrganisationStructure findOne(Long id) {
        log.debug("Request to get OrganisationStructure : {}", id);
        return organisationStructureRepository.findOne(id);
    }

    /**
     *  Delete the  organisationStructure by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete OrganisationStructure : {}", id);
        organisationStructureRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public List<OrganisationStructure> getListOfPossibleSenderOrganisations(Long organisationId) {
        return organisationStructureRepository.getListOfPossibleSendersOrganisationId(organisationId);
    }

    @Transactional(readOnly = true)
    public OrganisationStructure getOneByCurrentUser(){
        return organisationStructureRepository.getOneByCurrentUser();
    }
    
    @Transactional(readOnly=true)
    public List<OrganisationStructure> getAllParentedOrSupervisoredOrganisationStructures() {
    	User user = userService.getCurrentUser();
    	return organisationStructureRepository.getAllParentedOrSupervisoredOrganisationStructures(Ints.checkedCast(user.getOrganisationStructure().getId()));
    }

}
