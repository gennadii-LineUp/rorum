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
import rog.domain.GlossaryOfRisks;
import rog.service.GlossaryOfRisksService;
import rog.web.rest.errors.BadRequestAlertException;
import rog.web.rest.util.HeaderUtil;
import rog.web.rest.util.PaginationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing GlossaryOfRisks.
 */
@RestController
@RequestMapping("/api")
public class GlossaryOfRisksResource {

    private final Logger log = LoggerFactory.getLogger(GlossaryOfRisksResource.class);

    private static final String ENTITY_NAME = "glossaryOfRisks";

    @Autowired
    private GlossaryOfRisksService glossaryOfRisksService;

    /**
     * POST  /glossary-of-risks : Create a new glossaryOfRisks.
     *
     * @param glossaryOfRisks the glossaryOfRisks to create
     * @return the ResponseEntity with status 201 (Created) and with body the new glossaryOfRisks, or with status 400 (Bad Request) if the glossaryOfRisks has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/glossary-of-risks")
    @Timed
    public ResponseEntity<GlossaryOfRisks> createGlossaryOfRisks(@Valid @RequestBody GlossaryOfRisks glossaryOfRisks) throws URISyntaxException {
        log.debug("REST request to save GlossaryOfRisks : {}", glossaryOfRisks);
        if (glossaryOfRisks.getId() != null) {
            throw new BadRequestAlertException("A new glossaryOfRisks cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GlossaryOfRisks result = glossaryOfRisksService.save(glossaryOfRisks);
        return ResponseEntity.created(new URI("/api/glossary-of-risks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /glossary-of-risks : Updates an existing glossaryOfRisks.
     *
     * @param glossaryOfRisks the glossaryOfRisks to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated glossaryOfRisks,
     * or with status 400 (Bad Request) if the glossaryOfRisks is not valid,
     * or with status 500 (Internal Server Error) if the glossaryOfRisks couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/glossary-of-risks")
    @Timed
    public ResponseEntity<GlossaryOfRisks> updateGlossaryOfRisks(@Valid @RequestBody GlossaryOfRisks glossaryOfRisks) throws URISyntaxException {
        log.debug("REST request to update GlossaryOfRisks : {}", glossaryOfRisks);
        if (glossaryOfRisks.getId() == null) {
            return createGlossaryOfRisks(glossaryOfRisks);
        }
        GlossaryOfRisks result = glossaryOfRisksService.save(glossaryOfRisks);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, glossaryOfRisks.getId().toString()))
            .body(result);
    }

    /**
     * GET  /glossary-of-risks : get all the glossaryOfRisks.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of glossaryOfRisks in body
     */
    @GetMapping("/glossary-of-risks")
    @Timed
    public ResponseEntity<List<GlossaryOfRisks>> getAllGlossaryOfRisks(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of GlossaryOfRisks");
        Page<GlossaryOfRisks> page = glossaryOfRisksService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/glossary-of-risks");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /glossary-of-risks/:id : get the "id" glossaryOfRisks.
     *
     * @param id the id of the glossaryOfRisks to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the glossaryOfRisks, or with status 404 (Not Found)
     */
    @GetMapping("/glossary-of-risks/{id}")
    @Timed
    public ResponseEntity<GlossaryOfRisks> getGlossaryOfRisks(@PathVariable Long id) {
        log.debug("REST request to get GlossaryOfRisks : {}", id);
        GlossaryOfRisks glossaryOfRisks = glossaryOfRisksService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(glossaryOfRisks));
    }

    /**
     * DELETE  /glossary-of-risks/:id : delete the "id" glossaryOfRisks.
     *
     * @param id the id of the glossaryOfRisks to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/glossary-of-risks/{id}")
    @Timed
    public ResponseEntity<Void> deleteGlossaryOfRisks(@PathVariable Long id) {
        log.debug("REST request to delete GlossaryOfRisks : {}", id);
        glossaryOfRisksService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
