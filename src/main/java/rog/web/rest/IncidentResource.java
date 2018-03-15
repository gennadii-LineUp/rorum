package rog.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import rog.domain.*;
import rog.security.AuthoritiesConstants;
import rog.service.*;
import rog.service.dto.IncidentDTO;
import rog.service.mapper.IncidentMapper;
import rog.service.validation.Validator;
import rog.web.rest.errors.BadRequestAlertException;
import rog.web.rest.util.HeaderUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing Incident.
 */
@RestController
@RequestMapping("/api")
public class IncidentResource {

    private final Logger log = LoggerFactory.getLogger(IncidentResource.class);

    private static final String ENTITY_NAME = "incident";

    @Autowired
    private IncidentService incidentService;

    @Autowired
    private OrganisationStructureService organisationStructureService;

    @Autowired
    private IncidentMapper incidentMapper;

    @Autowired
    private Validator incidentDTOValidator;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private SetOfSentPurposesService setOfSentPurposesService;

    @Autowired
    private UserService userService;

    @Autowired
    private FilledRisksService filledRisksService;

    /**
     * POST  /incident : Create a new incidentDTO.
     *
     * @param incidentDTO the incidentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new incidentDTO, or with status 400 (Bad Request) if the incidentDTO has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/incident")
    @Secured(AuthoritiesConstants.USER)
    @Timed
    public ResponseEntity<IncidentDTO> createIncident(@RequestBody IncidentDTO incidentDTO) throws URISyntaxException {
        log.debug("REST request to save Incident : {}", incidentDTO);
        if (incidentDTO.getId() != null) {
            throw new BadRequestAlertException("A new incidentDTO cannot already have an ID", ENTITY_NAME, "idexists");
        }

        if(!isValidIncidentDTO(incidentDTO)){
            return ResponseEntity.badRequest().body(incidentDTO);
        }

        Incident result = incidentService.save(incidentMapper.incident(incidentDTO));
        if(result == null){
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.created(new URI("/api/incidents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(new IncidentDTO(result));
    }

    private boolean isValidIncidentDTO(IncidentDTO incidentDTO){
        incidentDTOValidator.validate(incidentDTO);
        return incidentDTO.getErrors().isEmpty();
    }

    /**
     * PUT  /incident : Updates an existing incidentDTO.
     *
     * @param incidentDTO the incidentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated incidentDTO,
     * or with status 400 (Bad Request) if the incidentDTO is not valid,
     * or with status 500 (Internal Server Error) if the incidentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/incident")
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.ADMIN_GLOBAL})
    @Timed
    public ResponseEntity<IncidentDTO> updateIncident(@RequestBody IncidentDTO incidentDTO) throws URISyntaxException {
        log.debug("REST request to update Incident : {}", incidentDTO);
        if (incidentDTO.getId() == null) {
            return createIncident(incidentDTO);
        }
        Incident result = incidentService.save(incidentMapper.incident(incidentDTO));
        if(result == null){
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, incidentDTO.getId().toString()))
            .body(new IncidentDTO(result));
    }

//    /**
//     * GET  /incidents/:id : get the "id" incident.
//     *
//     * @param id the id of the incident to retrieve
//     * @return the ResponseEntity with status 200 (OK) and with body the incident, or with status 404 (Not Found)
//     */
//    @GetMapping("/incidents/{id}")
//    @Timed
//    @Secured({AuthoritiesConstants.USER, AuthoritiesConstants.ADMIN, AuthoritiesConstants.ADMIN_GLOBAL})
//    public ResponseEntity<Incident> getIncident(@PathVariable Long id) {
//        log.debug("REST request to get Incident : {}", id);
//        Incident incident = incidentService.findOne(id);
//        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(incident));
//    }

    /**
     * DELETE  /incidents/:id : delete the "id" incident.
     *
     * @param id the id of the incident to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/incidents/{id}")
    @Timed
    public ResponseEntity<Void> deleteIncident(@PathVariable Long id) {
        log.debug("REST request to delete Incident : {}", id);
        incidentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/incidents/parented")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<List<Incident>> getAllOfParentedOrganisation() {
        log.debug("REST request to get list of Incident of parented organisation for local admin: {}");

        Long organisationId = organisationStructureService.getOneByCurrentUser().getId();
        List<Incident> list = incidentService.getAllOfParentedOrganisation(organisationId);

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(list));
    }

    @GetMapping("/incidents/supervising")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<List<Incident>> getAllOfSupervisingOrganisation() {
        log.debug("REST request to get list of Incident of supervising organisation for local admin: {}");

        Long organisationId = organisationStructureService.getOneByCurrentUser().getId();
        List<Incident> list = incidentService.getAllOfSupervisingOrganisation(organisationId);

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(list));
    }

    @PutMapping("/incident/add-supervisor")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<IncidentDTO> setSupervisedBy(@RequestBody IncidentDTO incidentDTO) throws URISyntaxException {
        log.debug("REST request to save Incident with supervisedBy : {}", incidentDTO);

        if(!isValidIncidentDTO(incidentDTO)){
            return ResponseEntity.badRequest().body(incidentDTO);
        }


        Incident result = incidentService.setSupervisedByAdmin(incidentMapper.incident(incidentDTO));
        if(result == null){
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.created(new URI("/api/incident/add-supervisor" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(new IncidentDTO(result));
    }

    @GetMapping("/incident-user")
    @Timed
    public ResponseEntity<List<Orders>> getAllOrders() {
        log.debug("REST request to get a page of Orders");

        return new ResponseEntity<>(ordersService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/incident-user/{orderId}")
    @Timed
    public ResponseEntity<SetOfSentPurposes> getSetOfSentPurposes(@PathVariable Long orderId) {
        log.debug("REST request to get setOfSentPurposes");
        SetOfSentPurposes setOfSentPurposes = setOfSentPurposesService.getOneByOrganisationIdAndOrderId(userService.getCurrentUser().getOrganisationStructure().getId(), orderId);
        return new ResponseEntity<>(setOfSentPurposes, HttpStatus.OK);
    }
    @GetMapping("/incident-user/{orderId}/{purposeId}")
    @Timed
    public ResponseEntity<Set<GlossaryOfRisks>> getRisksForSpecificPurpose(@PathVariable Long orderId, @PathVariable Long purposeId) {
        log.debug("REST request to get setOfSentPurposes");
        return new ResponseEntity<Set<GlossaryOfRisks>>(incidentService.getRisksForSpecificPurpose(purposeId) , HttpStatus.OK);
    }

    @Timed
    @Secured(AuthoritiesConstants.USER)
    @GetMapping("/incident-user/filled/{riskId}/{purposeId}")
    public ResponseEntity<FilledRisks> getFilledRisksForIncidentReport(@PathVariable Long riskId, @PathVariable Long purposeId) {
        log.debug("REST request to get FilledRisks (risk id: " + riskId + ", purpose id: " + purposeId + ")");
        Long currentUserId = userService.getCurrentUser().getId();
        return new ResponseEntity<>(filledRisksService.findFilledRiskForIncident(riskId, purposeId, currentUserId), HttpStatus.OK);
    }

    @GetMapping("/incident/{orderId}/admin")
    @Timed
    public ResponseEntity<List<Incident>> getAllParentedOrSupervisoredCellsIncidents(@PathVariable Long orderId) {
        log.debug("REST request to get setOfSentPurposes");
        return new ResponseEntity<>(incidentService.getAllParentedOrSupervisoredCellsIncidents(orderId) , HttpStatus.OK);
    }

    @GetMapping("/incident-user/{orderId}/get-all")
    @Secured(AuthoritiesConstants.USER)
    @Timed
    public ResponseEntity<List<Incident>> getAllParentedOrSupervisoredCellsIncidentsForUser(@PathVariable Long orderId) {
        log.debug("REST request to get setOfSentPurposes");
        return new ResponseEntity<>(incidentService.getAllParentedOrSupervisoredCellsIncidentsForUser(orderId) , HttpStatus.OK);
    }
}
