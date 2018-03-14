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
import rog.domain.GlossaryOfCommercialRisks;
import rog.service.GlossaryOfCommercialRisksService;
import rog.web.rest.errors.BadRequestAlertException;
import rog.web.rest.util.HeaderUtil;
import rog.web.rest.util.PaginationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing GlossaryOfCommercialRisks.
 */
@RestController
@RequestMapping("/api")
public class GlossaryOfCommercialRisksResource {

    private final Logger log = LoggerFactory.getLogger(GlossaryOfCommercialRisksResource.class);

    private static final String ENTITY_NAME = "glossaryOfCommercialRisks";

    @Autowired
    private GlossaryOfCommercialRisksService glossaryOfCommercialRisksService;

    /**
     * POST  /glossary-of-commercial-risks : Create a new glossaryOfCommercialRisks.
     *
     * @param glossaryOfCommercialRisks the glossaryOfCommercialRisks to create
     * @return the ResponseEntity with status 201 (Created) and with body the new glossaryOfCommercialRisks, or with status 400 (Bad Request) if the glossaryOfCommercialRisks has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/glossary-of-commercial-risks")
    @Timed
    public ResponseEntity<GlossaryOfCommercialRisks> createGlossaryOfCommercialRisks(@Valid @RequestBody GlossaryOfCommercialRisks glossaryOfCommercialRisks) throws URISyntaxException {
        log.debug("REST request to save GlossaryOfCommercialRisks : {}", glossaryOfCommercialRisks);
        if (glossaryOfCommercialRisks.getId() != null) {
            throw new BadRequestAlertException("A new glossaryOfCommercialRisks cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GlossaryOfCommercialRisks result = glossaryOfCommercialRisksService.save(glossaryOfCommercialRisks);
        return ResponseEntity.created(new URI("/api/glossary-of-commercial-risks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /glossary-of-commercial-risks : Updates an existing glossaryOfCommercialRisks.
     *
     * @param glossaryOfCommercialRisks the glossaryOfCommercialRisks to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated glossaryOfCommercialRisks,
     * or with status 400 (Bad Request) if the glossaryOfCommercialRisks is not valid,
     * or with status 500 (Internal Server Error) if the glossaryOfCommercialRisks couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/glossary-of-commercial-risks")
    @Timed
    public ResponseEntity<GlossaryOfCommercialRisks> updateGlossaryOfCommercialRisks(@Valid @RequestBody GlossaryOfCommercialRisks glossaryOfCommercialRisks) throws URISyntaxException {
        log.debug("REST request to update GlossaryOfCommercialRisks : {}", glossaryOfCommercialRisks);
        if (glossaryOfCommercialRisks.getId() == null) {
            return createGlossaryOfCommercialRisks(glossaryOfCommercialRisks);
        }
        GlossaryOfCommercialRisks result = glossaryOfCommercialRisksService.save(glossaryOfCommercialRisks);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, glossaryOfCommercialRisks.getId().toString()))
            .body(result);
    }

    /**
     * GET  /glossary-of-commercial-risks : get all the glossaryOfCommercialRisks.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of glossaryOfCommercialRisks in body
     */
    @GetMapping("/glossary-of-commercial-risks")
    @Timed
    public ResponseEntity<List<GlossaryOfCommercialRisks>> getAllGlossaryOfCommercialRisks(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of GlossaryOfCommercialRisks");
        Page<GlossaryOfCommercialRisks> page = glossaryOfCommercialRisksService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/glossary-of-commercial-risks");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /glossary-of-commercial-risks/:id : get the "id" glossaryOfCommercialRisks.
     *
     * @param id the id of the glossaryOfCommercialRisks to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the glossaryOfCommercialRisks, or with status 404 (Not Found)
     */
    @GetMapping("/glossary-of-commercial-risks/{id}")
    @Timed
    public ResponseEntity<GlossaryOfCommercialRisks> getGlossaryOfCommercialRisks(@PathVariable Long id) {
        log.debug("REST request to get GlossaryOfCommercialRisks : {}", id);
        GlossaryOfCommercialRisks glossaryOfCommercialRisks = glossaryOfCommercialRisksService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(glossaryOfCommercialRisks));
    }

    /**
     * DELETE  /glossary-of-commercial-risks/:id : delete the "id" glossaryOfCommercialRisks.
     *
     * @param id the id of the glossaryOfCommercialRisks to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/glossary-of-commercial-risks/{id}")
    @Timed
    public ResponseEntity<Void> deleteGlossaryOfCommercialRisks(@PathVariable Long id) {
        log.debug("REST request to delete GlossaryOfCommercialRisks : {}", id);
        glossaryOfCommercialRisksService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
