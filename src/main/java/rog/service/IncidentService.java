package rog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rog.domain.*;
import rog.domain.enumeration.StatusOfIncident;
import rog.repository.FilledRisksRepository;
import rog.repository.IncidentRepository;
import rog.repository.SetOfSentPurposesRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Service Implementation for managing Incident.
 */
@Service
@Transactional
public class IncidentService {

    private final Logger log = LoggerFactory.getLogger(IncidentService.class);

    @Autowired
    private IncidentRepository incidentRepository;

    @Autowired
    private SetOfSentPurposesRepository setOfSentPurposesRepository;

//    @Autowired
//    private FilledRisksRepository filledRisksRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private GlossaryOfPurposesService glossaryOfPurposesService;

    @Autowired
    private OrganisationStructureService organisationStructureService;

    /**
     * Save a incident.
     *
     * @param incident the entity to save
     * @return the persisted entity
     */
    public Incident save(Incident incident) {
        log.debug("Request to save Incident : {}", incident);
        SetOfSentPurposes setOfSentPurpose = setOfSentPurposesRepository.getOne(incident.getSetOfSentPurposes().getId());

        if (!setOfSentPurpose.getStatusOfSending().isConfirmedPlan()) {
            return null;
        }

        incident.setUser(userService.getCurrentUser());
        return incidentRepository.save(incident);
    }

    /**
     * Get all the incidents.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Incident> findAll() {
        log.debug("Request to get all Incidents");
        return incidentRepository.findAll();
    }

    /**
     * Get one incident by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Incident findOne(Long id) {
        log.debug("Request to get Incident : {}", id);
        return incidentRepository.findOne(id);
    }

    /**
     * Delete the  incident by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Incident : {}", id);
        incidentRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public List<Incident> getAllOfParentedOrganisation(Long organisationId) {
        return incidentRepository.getAllOfParentedOrganisation(organisationId);
    }

    @Transactional(readOnly = true)
    public List<Incident> getAllOfSupervisingOrganisation(Long organisationId) {
        return incidentRepository.getAllOfSupervisingOrganisation(organisationId);
    }

    @Transactional(readOnly = true)
    public Set<GlossaryOfRisks> getRisksForSpecificPurpose(Long purposeId) {
        GlossaryOfPurposes glossaryOfPurposes = glossaryOfPurposesService.findOne(purposeId);
        return glossaryOfPurposes.getGlossaryOfRisks();
    }

    @Transactional(readOnly = true)
    public List<Incident> getAllParentedOrSupervisoredCellsIncidents(Long orderId) {
        User user = userService.getCurrentUser();
        List<User> users = userService.getAllParentedAndSupervisoredUsers(user.getOrganisationStructure().getId());
        Long setOfSentPurposesId;
//        Long setOfSentPurposesId = setOfSentPurposesRepository.fin

        List<Incident> result = new ArrayList<>();
        for (User user1 : users) {
            if (setOfSentPurposesRepository.findOneByOrdersIdAndUserId(orderId, user1.getId()) != null) {
                setOfSentPurposesId = setOfSentPurposesRepository.findOneByOrdersIdAndUserId(orderId, user1.getId()).getId();

                if (incidentRepository.findAllByUserIdAndSetOfSentPurposesId(user1.getId(), setOfSentPurposesId) != null) {
                    List<Incident> incidents = incidentRepository.findAllByUserIdAndSetOfSentPurposesId(user1.getId(), setOfSentPurposesId);

                    for (Incident incident : incidents) {
                        result.add(incident);
                    }
                }
            }

//            log.debug("SetOfSentPurposesId: " + setOfSentPurposesId);
//            if( setOfSentPurposesId != null ) {
//                List<Incident> incidents = incidentRepository.findAllByUserIdAndSetOfSentPurposesId(user1.getId(), setOfSentPurposesId);
//                if(incidents != null){
//
//                    for (Incident incident: incidents) {
//                        result.add(incident);
//                    }
//
//                }
//            }
        }
//        List<Incident> result = incidentRepository.findAll();
        return result;
    }

    @Transactional(readOnly = true)
    public List<Incident> getAllParentedOrSupervisoredCellsIncidentsForUser(Long orderId) {
        User user = userService.getCurrentUser();
        Long setOfSentPurposesId;
        List<Incident> incidents = new ArrayList<>();
        if (setOfSentPurposesRepository.findOneByOrdersIdAndUserId(orderId, user.getId()) != null) {
            setOfSentPurposesId = setOfSentPurposesRepository.findOneByOrdersIdAndUserId(orderId, user.getId()).getId();
            if (incidentRepository.findAllByUserIdAndSetOfSentPurposesId(user.getId(), setOfSentPurposesId) != null) {
                incidents = incidentRepository.findAllByUserIdAndSetOfSentPurposesId(user.getId(), setOfSentPurposesId);
            }
        }
        return incidents;
    }

    public Incident setSupervisedByAdmin(Incident incident) {
        log.debug("Request to set Incident as supervised : {}", incident);
        SetOfSentPurposes setOfSentPurpose = setOfSentPurposesRepository.getOne(incident.getSetOfSentPurposes().getId());
        if (!setOfSentPurpose.getStatusOfSending().isConfirmedPlan()) {
            return null;
        }
        log.debug(incident.getId() + " IDIDIDIDIDIDIDIDIDIDIDIDIDIDID\n\n\n");
        incident.setStatusOfIncident(StatusOfIncident.SUPERVISED);
//        incident.setSupervisedBy(userService.getCurrentUser());
        return incidentRepository.save(incident);
    }

}
