package rog.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import rog.domain.GlossaryOfCommercialRisks;
import rog.domain.GlossaryOfPurposes;
import rog.domain.OrganisationStructure;
import rog.security.AuthoritiesConstants;
import rog.service.GlossaryOfCommercialRisksService;
import rog.service.GlossaryOfPurposesService;
import rog.service.OrganisationStructureService;
import rog.service.dto.GlossaryOfCommercialRisksDTO;
import rog.service.mapper.GlossaryOfCommercialRisksMapper;
import rog.web.rest.errors.BadRequestAlertException;
import rog.web.rest.util.HeaderUtil;
import rog.web.rest.util.PaginationUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing OrganisationStructure.
 */
@RestController
@RequestMapping("/api")
public class OrganisationStructureResource {

    private final Logger log = LoggerFactory.getLogger(OrganisationStructureResource.class);

    private static final String ENTITY_NAME = "organisationStructure";

    @Autowired
    private OrganisationStructureService organisationStructureService;

    @Autowired
    private GlossaryOfPurposesService glossaryOfPurposesService;

    @Autowired
    private GlossaryOfCommercialRisksService glossaryOfCommercialRisksService;

    @Autowired
    private GlossaryOfCommercialRisksMapper glossaryOfCommercialRisksMapper;

    /**
     * POST  /organisation-structures : Create a new organisationStructure.
     *
     * @param organisationStructure the organisationStructure to create
     * @return the ResponseEntity with status 201 (Created) and with body the new organisationStructure, or with status 400 (Bad Request) if the organisationStructure has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/organisation-structures")
    @Timed
    public ResponseEntity<OrganisationStructure> createOrganisationStructure(@RequestBody OrganisationStructure organisationStructure) throws URISyntaxException {
        log.debug("REST request to save OrganisationStructure : {}", organisationStructure);
        if (organisationStructure.getId() != null) {
            throw new BadRequestAlertException("A new organisationStructure cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrganisationStructure result = organisationStructureService.save(organisationStructure);
        return ResponseEntity.created(new URI("/api/organisation-structures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /organisation-structures : Updates an existing organisationStructure.
     *
     * @param organisationStructure the organisationStructure to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated organisationStructure,
     * or with status 400 (Bad Request) if the organisationStructure is not valid,
     * or with status 500 (Internal Server Error) if the organisationStructure couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/organisation-structures")
    @Timed
    public ResponseEntity<OrganisationStructure> updateOrganisationStructure(@RequestBody OrganisationStructure organisationStructure) throws URISyntaxException {
        log.debug("REST request to update OrganisationStructure : {}", organisationStructure);
        if (organisationStructure.getId() == null) {
            return createOrganisationStructure(organisationStructure);
        }
        OrganisationStructure result = organisationStructureService.save(organisationStructure);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, organisationStructure.getId().toString()))
            .body(result);
    }

    /**
     * GET  /organisation-structures : get all the organisationStructures.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of organisationStructures in body
     */
    @GetMapping("/organisation-structures")
    @Timed
    public ResponseEntity<List<OrganisationStructure>> getAllOrganisationStructures(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of OrganisationStructures");
        Page<OrganisationStructure> page = organisationStructureService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/organisation-structures");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /organisation-structures/:id : get the "id" organisationStructure.
     *
     * @param id the id of the organisationStructure to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the organisationStructure, or with status 404 (Not Found)
     */
    @GetMapping("/organisation-structures/{id}")
    @Timed
    public ResponseEntity<OrganisationStructure> getOrganisationStructure(@PathVariable Long id) {
        log.debug("REST request to get OrganisationStructure : {}", id);
        OrganisationStructure organisationStructure = organisationStructureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(organisationStructure));
    }

    /**
     * DELETE  /organisation-structures/:id : delete the "id" organisationStructure.
     *
     * @param id the id of the organisationStructure to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/organisation-structures/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrganisationStructure(@PathVariable Long id) {
        log.debug("REST request to delete OrganisationStructure : {}", id);
        organisationStructureService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/organisation-structures/{id}/glossary-of-purposes")
    @Timed
    public ResponseEntity<List<GlossaryOfPurposes>> getListOfPurposesByOrganisationStructure(@PathVariable("id") Long id) {
        log.debug("REST request to get list of GlossaryOfPurposes : {}", id);
        List<GlossaryOfPurposes> list = glossaryOfPurposesService.getAllByOrganisationId(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(list));
    }

    @GetMapping("/organisation-structures/{id}/possibleSenders")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<List<OrganisationStructure>> getPossibleSendersOrganisationStructure(@PathVariable("id") Long id) {
        log.debug("REST request to get OrganisationStructure : {}", id);
        List<OrganisationStructure> list = organisationStructureService.getListOfPossibleSenderOrganisations(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(list));
    }

    @GetMapping("/organisation-structures/glossary-of-commercial-risks")
    @Timed
    @Secured({AuthoritiesConstants.USER, AuthoritiesConstants.ADMIN, AuthoritiesConstants.ADMIN_GLOBAL})
    public ResponseEntity<List<GlossaryOfCommercialRisks>> getGlossaryOfCommercialRisks() {
        log.debug("REST request to get GlossaryOfCommercialRisks : {}");
        List<GlossaryOfCommercialRisks> glossaryOfCommercialRisks =
            glossaryOfCommercialRisksService.getAllOfCurrentOrganisation();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(glossaryOfCommercialRisks));
    }

    @PostMapping("/organisation-structures/glossary-of-commercial-risks")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<GlossaryOfCommercialRisks> createGlossaryOfRisks(@RequestBody GlossaryOfCommercialRisksDTO glossaryOfCommercialRisksDTO) {
        log.debug("REST request to create GlossaryOfCommercialRisks : {}");

        Long organisationId = organisationStructureService.getOneByCurrentUser().getId();
        glossaryOfCommercialRisksDTO.setOrganisationId(organisationId);

        GlossaryOfCommercialRisks result = glossaryOfCommercialRisksService
            .save(glossaryOfCommercialRisksMapper.glossaryOfCommercialRisk(glossaryOfCommercialRisksDTO));

        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/organisation-structures/glossary-of-commercial-risks")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.ADMIN_GLOBAL})
    public ResponseEntity<GlossaryOfCommercialRisks> updateGlossaryOfRisks(@RequestBody GlossaryOfCommercialRisksDTO glossaryOfCommercialRisksDTO) {
        log.debug("REST request to update GlossaryOfCommercialRisks : {}", glossaryOfCommercialRisksDTO);
        return createGlossaryOfRisks(glossaryOfCommercialRisksDTO);
    }

    @GetMapping("/organisation-structures/current")
    @Timed
    public ResponseEntity<OrganisationStructure> getCurrentOrganisation() {
        log.debug("REST request to get current OrganisationStructure : {}");
        OrganisationStructure organisationStructure = organisationStructureService.getOneByCurrentUser();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(organisationStructure));
    }

}
