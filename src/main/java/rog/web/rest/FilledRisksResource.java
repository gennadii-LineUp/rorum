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
import rog.domain.FilledRisks;
import rog.service.FilledRisksService;
import rog.web.rest.errors.BadRequestAlertException;
import rog.web.rest.util.HeaderUtil;
import rog.web.rest.util.PaginationUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing FilledRisks.
 */
@RestController
@RequestMapping("/api")
public class FilledRisksResource {

    private final Logger log = LoggerFactory.getLogger(FilledRisksResource.class);

    private static final String ENTITY_NAME = "filledRisks";

    @Autowired
    private FilledRisksService filledRisksService;

    /**
     * POST  /filled-risks : Create a new filledRisks.
     *
     * @param filledRisks the filledRisks to create
     * @return the ResponseEntity with status 201 (Created) and with body the new filledRisks, or with status 400 (Bad Request) if the filledRisks has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/filled-risks")
    @Timed
    public ResponseEntity<FilledRisks> createFilledRisks(@RequestBody FilledRisks filledRisks) throws URISyntaxException {
        log.debug("REST request to save FilledRisks : {}", filledRisks);
        if (filledRisks.getId() != null) {
            throw new BadRequestAlertException("A new filledRisks cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FilledRisks result = filledRisksService.save(filledRisks);
        return ResponseEntity.created(new URI("/api/filled-risks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /filled-risks : Updates an existing filledRisks.
     *
     * @param filledRisks the filledRisks to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated filledRisks,
     * or with status 400 (Bad Request) if the filledRisks is not valid,
     * or with status 500 (Internal Server Error) if the filledRisks couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/filled-risks")
    @Timed
    public ResponseEntity<FilledRisks> updateFilledRisks(@RequestBody FilledRisks filledRisks) throws URISyntaxException {
        log.debug("REST request to update FilledRisks : {}", filledRisks);
        if (filledRisks.getId() == null) {
            return createFilledRisks(filledRisks);
        }
        FilledRisks result = filledRisksService.save(filledRisks);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, filledRisks.getId().toString()))
            .body(result);
    }

    /**
     * GET  /filled-risks : get all the filledRisks.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of filledRisks in body
     */
    @GetMapping("/filled-risks")
    @Timed
    public ResponseEntity<List<FilledRisks>> getAllFilledRisks(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of FilledRisks");
        Page<FilledRisks> page = filledRisksService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/filled-risks");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /filled-risks/:id : get the "id" filledRisks.
     *
     * @param id the id of the filledRisks to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the filledRisks, or with status 404 (Not Found)
     */
    @GetMapping("/filled-risks/{id}")
    @Timed
    public ResponseEntity<FilledRisks> getFilledRisks(@PathVariable Long id) {
        log.debug("REST request to get FilledRisks : {}", id);
        FilledRisks filledRisks = filledRisksService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(filledRisks));
    }

    /**
     * DELETE  /filled-risks/:id : delete the "id" filledRisks.
     *
     * @param id the id of the filledRisks to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/filled-risks/{id}")
    @Timed
    public ResponseEntity<Void> deleteFilledRisks(@PathVariable Long id) {
        log.debug("REST request to delete FilledRisks : {}", id);
        filledRisksService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
