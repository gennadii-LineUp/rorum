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
import rog.domain.GlossaryOfMeasureUnits;
import rog.domain.GlossaryOfPurposes;
import rog.domain.GlossaryOfRisks;
import rog.security.AuthoritiesConstants;
import rog.service.*;
import rog.service.dto.GlossaryOfMeasureUnitsDTO;
import rog.service.dto.GlossaryOfRisksDTO;
import rog.service.mapper.GlossaryOfMeasureUnitsMapper;
import rog.service.mapper.GlossaryOfRisksMapper;
import rog.web.rest.errors.BadRequestAlertException;
import rog.web.rest.util.HeaderUtil;
import rog.web.rest.util.PaginationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing GlossaryOfPurposes.
 */
@RestController
@RequestMapping("/api")
public class GlossaryOfPurposesResource {

    private final Logger log = LoggerFactory.getLogger(GlossaryOfPurposesResource.class);

    private static final String ENTITY_NAME = "glossaryOfPurposes";

    @Autowired
    private GlossaryOfPurposesService glossaryOfPurposesService;

    @Autowired
    private GlossaryOfMeasureUnitsService glossaryOfMeasureUnitsService;

    @Autowired
    private GlossaryOfRisksService glossaryOfRisksService;

    @Autowired
    private OrganisationStructureService organisationStructureService;

    @Autowired
    private UserService userService;

    @Autowired
    private GlossaryOfMeasureUnitsMapper glossaryOfMeasureUnitsMapper;

    @Autowired
    private GlossaryOfRisksMapper glossaryOfRisksMapper;

    /**
     * POST  /glossary-of-purposes : Create a new glossaryOfPurposes.
     *
     * @param glossaryOfPurposes the glossaryOfPurposes to create
     * @return the ResponseEntity with status 201 (Created) and with body the new glossaryOfPurposes, or with status 400 (Bad Request) if the glossaryOfPurposes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/glossary-of-purposes")
    @Timed
    public ResponseEntity<GlossaryOfPurposes> createGlossaryOfPurposes(@Valid @RequestBody GlossaryOfPurposes glossaryOfPurposes) throws URISyntaxException {
        log.debug("REST request to save GlossaryOfPurposes : {}", glossaryOfPurposes);
        if (glossaryOfPurposes.getId() != null) {
            throw new BadRequestAlertException("A new glossaryOfPurposes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GlossaryOfPurposes result = glossaryOfPurposesService.save(glossaryOfPurposes);
        return ResponseEntity.created(new URI("/api/glossary-of-purposes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /glossary-of-purposes : Updates an existing glossaryOfPurposes.
     *
     * @param glossaryOfPurposes the glossaryOfPurposes to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated glossaryOfPurposes,
     * or with status 400 (Bad Request) if the glossaryOfPurposes is not valid,
     * or with status 500 (Internal Server Error) if the glossaryOfPurposes couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/glossary-of-purposes")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.ADMIN_GLOBAL})
    public ResponseEntity<GlossaryOfPurposes> updateGlossaryOfPurposes(@Valid @RequestBody GlossaryOfPurposes glossaryOfPurposes) throws URISyntaxException {
        log.debug("REST request to update GlossaryOfPurposes : {}", glossaryOfPurposes);
        if (glossaryOfPurposes.getId() == null) {
            return createGlossaryOfPurposes(glossaryOfPurposes);
        }
        GlossaryOfPurposes result = glossaryOfPurposesService.save(glossaryOfPurposes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, glossaryOfPurposes.getId().toString()))
            .body(result);
    }

    /**
     * GET  /glossary-of-purposes : get all the glossaryOfPurposes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of glossaryOfPurposes in body
     */
    @GetMapping("/glossary-of-purposes")
    @Timed
    public ResponseEntity<List<GlossaryOfPurposes>> getAllGlossaryOfPurposes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of GlossaryOfPurposes");
        Page<GlossaryOfPurposes> page = glossaryOfPurposesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/glossary-of-purposes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @Secured(AuthoritiesConstants.ADMIN)
    @GetMapping("/glossary-of-purposes/all")
    @Timed
    public ResponseEntity<List<GlossaryOfPurposes>> getAllGlossaryOfPurposesWithoutPageable() {
        log.debug("REST request to get all GlossaryOfPurposes");
        List<GlossaryOfPurposes> glossaryOfPurposes = glossaryOfPurposesService.findAll();
        HttpHeaders headers = new HttpHeaders();
        headers.add("ok", "ok");
        return new ResponseEntity<>(glossaryOfPurposes, headers, HttpStatus.OK);
    }

    /**
     * GET  /glossary-of-purposes/:id : get the "id" glossaryOfPurposes.
     *
     * @param id the id of the glossaryOfPurposes to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the glossaryOfPurposes, or with status 404 (Not Found)
     */
    @GetMapping("/glossary-of-purposes/{id}")
    @Timed
    public ResponseEntity<GlossaryOfPurposes> getGlossaryOfPurposes(@PathVariable Long id) {
        log.debug("REST request to get GlossaryOfPurposes : {}", id);
        GlossaryOfPurposes glossaryOfPurposes = glossaryOfPurposesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(glossaryOfPurposes));
    }

    /**
     * DELETE  /glossary-of-purposes/:id : delete the "id" glossaryOfPurposes.
     *
     * @param id the id of the glossaryOfPurposes to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/glossary-of-purposes/{id}")
    @Timed
    public ResponseEntity<Void> deleteGlossaryOfPurposes(@PathVariable Long id) {
        log.debug("REST request to delete GlossaryOfPurposes : {}", id);
        glossaryOfPurposesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/glossary-of-purposes/{purposeId}/glossary-of-measure-units")
    @Timed
    @Secured({AuthoritiesConstants.USER, AuthoritiesConstants.ADMIN, AuthoritiesConstants.ADMIN_GLOBAL})
    public ResponseEntity<List<GlossaryOfMeasureUnits>> getGlossaryOfMeasureUnits(@PathVariable Long purposeId) {
        log.debug("REST request to get GlossaryOfMeasureUnits : {}", purposeId);

        GlossaryOfPurposes glossaryOfPurposes = glossaryOfPurposesService.findOne(purposeId);
        if(glossaryOfPurposes ==  null){
            return ResponseEntity.badRequest().build();
        }

        List<GlossaryOfMeasureUnits> glossaryOfMeasureUnits =
            glossaryOfMeasureUnitsService.getAllOfCurrentOrganisationByGlossaryOfPurposesId(purposeId);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(glossaryOfMeasureUnits));
    }

    @GetMapping("/glossary-of-purposes/{purposeId}/glossary-of-risks")
    @Timed
    @Secured({AuthoritiesConstants.USER, AuthoritiesConstants.ADMIN, AuthoritiesConstants.ADMIN_GLOBAL})
    public ResponseEntity<List<GlossaryOfRisks>> getGlossaryOfRisks(@PathVariable Long purposeId) {
        log.debug("REST request to get GlossaryOfRisks : {}", purposeId);

        GlossaryOfPurposes glossaryOfPurposes = glossaryOfPurposesService.findOne(purposeId);
        if(glossaryOfPurposes ==  null){
            return ResponseEntity.badRequest().build();
        }

        List<GlossaryOfRisks> glossaryOfRisks =
            glossaryOfRisksService.getAllOfCurrentOrganisationByGlossaryOfPurposesId(purposeId);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(glossaryOfRisks));
    }

    @PostMapping("/glossary-of-purposes/{purposeId}/glossary-of-measure-units")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<GlossaryOfMeasureUnits> createGlossaryOfMeasureUnits(@PathVariable Long purposeId,
                                                                               @RequestBody GlossaryOfMeasureUnitsDTO glossaryOfMeasureUnitsDTO) {
        log.debug("REST request to create GlossaryOfMeasureUnits : {}", purposeId);

        GlossaryOfPurposes glossaryOfPurposes = glossaryOfPurposesService.findOne(purposeId);
        if(glossaryOfPurposes ==  null){
            return ResponseEntity.badRequest().build();
        }

        Long organisationId = organisationStructureService.getOneByCurrentUser().getId();
        glossaryOfMeasureUnitsDTO.setOrganisationId(organisationId);
        glossaryOfMeasureUnitsDTO.setPurposeId(purposeId);

        GlossaryOfMeasureUnits glossaryOfMeasureUnits =
            glossaryOfMeasureUnitsMapper.glossaryOfMeasureUnit(glossaryOfMeasureUnitsDTO);
        glossaryOfMeasureUnits.setUser(userService.getCurrentUser());

        GlossaryOfMeasureUnits result = glossaryOfMeasureUnitsService.save(glossaryOfMeasureUnits);

        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/glossary-of-purposes/{purposeId}/glossary-of-measure-units")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.ADMIN_GLOBAL})
    public ResponseEntity<GlossaryOfMeasureUnits> updateGlossaryOfMeasureUnits(@PathVariable Long purposeId,
                                                                           @RequestBody GlossaryOfMeasureUnitsDTO glossaryOfMeasureUnitsDTO) {
        log.debug("REST request to update GlossaryOfMeasureUnits : {}", glossaryOfMeasureUnitsDTO);
        return createGlossaryOfMeasureUnits(purposeId, glossaryOfMeasureUnitsDTO);
    }

    @PostMapping("/glossary-of-purposes/{purposeId}/glossary-of-risks")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<GlossaryOfRisks> createGlossaryOfRisks(@PathVariable Long purposeId,
                                                                 @RequestBody GlossaryOfRisksDTO glossaryOfRisksDTO) {
        log.debug("REST request to create GlossaryOfRisks : {}", purposeId);

        GlossaryOfPurposes glossaryOfPurposes = glossaryOfPurposesService.findOne(purposeId);
        if(glossaryOfPurposes ==  null){
            return ResponseEntity.badRequest().build();
        }

        Long organisationId = organisationStructureService.getOneByCurrentUser().getId();
        glossaryOfRisksDTO.setOrganisationId(organisationId);
        glossaryOfRisksDTO.setPurposeId(purposeId);

        GlossaryOfRisks glossaryOfRisks = glossaryOfRisksMapper.glossaryOfRisks(glossaryOfRisksDTO);
        glossaryOfRisks.setUser(userService.getCurrentUser());

        GlossaryOfRisks result = glossaryOfRisksService.save(glossaryOfRisks);

        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/glossary-of-purposes/{purposeId}/glossary-of-risks")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.ADMIN_GLOBAL})
    public ResponseEntity<GlossaryOfRisks> updateGlossaryOfRisks(@PathVariable Long purposeId,
                                                                 @RequestBody GlossaryOfRisksDTO glossaryOfRisksDTO) {
        log.debug("REST request to update GlossaryOfRisks : {}", glossaryOfRisksDTO);
        return createGlossaryOfRisks(purposeId, glossaryOfRisksDTO);
    }

}
