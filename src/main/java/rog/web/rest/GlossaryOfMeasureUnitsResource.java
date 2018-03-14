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
import org.springframework.web.bind.annotation.*;
import rog.domain.GlossaryOfMeasureUnits;
import rog.service.GlossaryOfMeasureUnitsService;
import rog.web.rest.errors.BadRequestAlertException;
import rog.web.rest.util.HeaderUtil;
import rog.web.rest.util.PaginationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing GlossaryOfMeasureUnits.
 */
@RestController
@RequestMapping("/api")
public class GlossaryOfMeasureUnitsResource {

    private final Logger log = LoggerFactory.getLogger(GlossaryOfMeasureUnitsResource.class);

    private static final String ENTITY_NAME = "glossaryOfMeasureUnits";

    @Autowired
    private GlossaryOfMeasureUnitsService glossaryOfMeasureUnitsService;

    /**
     * POST  /glossary-of-measure-units : Create a new glossaryOfMeasureUnits.
     *
     * @param glossaryOfMeasureUnits the glossaryOfMeasureUnits to create
     * @return the ResponseEntity with status 201 (Created) and with body the new glossaryOfMeasureUnits, or with status 400 (Bad Request) if the glossaryOfMeasureUnits has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/glossary-of-measure-units")
    @Timed
    public ResponseEntity<GlossaryOfMeasureUnits> createGlossaryOfMeasureUnits(@Valid @RequestBody GlossaryOfMeasureUnits glossaryOfMeasureUnits) throws URISyntaxException {
        log.debug("REST request to save GlossaryOfMeasureUnits : {}", glossaryOfMeasureUnits);
        if (glossaryOfMeasureUnits.getId() != null) {
            throw new BadRequestAlertException("A new glossaryOfMeasureUnits cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GlossaryOfMeasureUnits result = glossaryOfMeasureUnitsService.save(glossaryOfMeasureUnits);
        return ResponseEntity.created(new URI("/api/glossary-of-measure-units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /glossary-of-measure-units : Updates an existing glossaryOfMeasureUnits.
     *
     * @param glossaryOfMeasureUnits the glossaryOfMeasureUnits to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated glossaryOfMeasureUnits,
     * or with status 400 (Bad Request) if the glossaryOfMeasureUnits is not valid,
     * or with status 500 (Internal Server Error) if the glossaryOfMeasureUnits couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/glossary-of-measure-units")
    @Timed
    public ResponseEntity<GlossaryOfMeasureUnits> updateGlossaryOfMeasureUnits(@Valid @RequestBody GlossaryOfMeasureUnits glossaryOfMeasureUnits) throws URISyntaxException {
        log.debug("REST request to update GlossaryOfMeasureUnits : {}", glossaryOfMeasureUnits);
        if (glossaryOfMeasureUnits.getId() == null) {
            return createGlossaryOfMeasureUnits(glossaryOfMeasureUnits);
        }
        GlossaryOfMeasureUnits result = glossaryOfMeasureUnitsService.save(glossaryOfMeasureUnits);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, glossaryOfMeasureUnits.getId().toString()))
            .body(result);
    }

    /**
     * GET  /glossary-of-measure-units : get all the glossaryOfMeasureUnits.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of glossaryOfMeasureUnits in body
     */
    @GetMapping("/glossary-of-measure-units")
    @Timed
    public ResponseEntity<List<GlossaryOfMeasureUnits>> getAllGlossaryOfMeasureUnits(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of GlossaryOfMeasureUnits");
        Page<GlossaryOfMeasureUnits> page = glossaryOfMeasureUnitsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/glossary-of-measure-units");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /glossary-of-measure-units/:id : get the "id" glossaryOfMeasureUnits.
     *
     * @param id the id of the glossaryOfMeasureUnits to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the glossaryOfMeasureUnits, or with status 404 (Not Found)
     */
    @GetMapping("/glossary-of-measure-units/{id}")
    @Timed
    public ResponseEntity<GlossaryOfMeasureUnits> getGlossaryOfMeasureUnits(@PathVariable Long id) {
        log.debug("REST request to get GlossaryOfMeasureUnits : {}", id);
        GlossaryOfMeasureUnits glossaryOfMeasureUnits = glossaryOfMeasureUnitsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(glossaryOfMeasureUnits));
    }

    /**
     * DELETE  /glossary-of-measure-units/:id : delete the "id" glossaryOfMeasureUnits.
     *
     * @param id the id of the glossaryOfMeasureUnits to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/glossary-of-measure-units/{id}")
    @Timed
    public ResponseEntity<Void> deleteGlossaryOfMeasureUnits(@PathVariable Long id) {
        log.debug("REST request to delete GlossaryOfMeasureUnits : {}", id);
        glossaryOfMeasureUnitsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
