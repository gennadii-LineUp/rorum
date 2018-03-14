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
import rog.domain.FilledCommercialRisks;
import rog.service.FilledCommercialRisksService;
import rog.web.rest.errors.BadRequestAlertException;
import rog.web.rest.util.HeaderUtil;
import rog.web.rest.util.PaginationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing FilledCommercialRisks.
 */
@RestController
@RequestMapping("/api")
public class FilledCommercialRisksResource {

    private final Logger log = LoggerFactory.getLogger(FilledCommercialRisksResource.class);

    private static final String ENTITY_NAME = "filledCommercialRisks";

    @Autowired
    private FilledCommercialRisksService filledCommercialRisksService;

    /**
     * POST  /filled-commercial-risks : Create a new filledCommercialRisks.
     *
     * @param filledCommercialRisks the filledCommercialRisks to create
     * @return the ResponseEntity with status 201 (Created) and with body the new filledCommercialRisks, or with status 400 (Bad Request) if the filledCommercialRisks has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/filled-commercial-risks")
    @Timed
    public ResponseEntity<FilledCommercialRisks> createFilledCommercialRisks(@Valid @RequestBody FilledCommercialRisks filledCommercialRisks) throws URISyntaxException {
        log.debug("REST request to save FilledCommercialRisks : {}", filledCommercialRisks);
        if (filledCommercialRisks.getId() != null) {
            throw new BadRequestAlertException("A new filledCommercialRisks cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FilledCommercialRisks result = filledCommercialRisksService.save(filledCommercialRisks);
        return ResponseEntity.created(new URI("/api/filled-commercial-risks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /filled-commercial-risks : Updates an existing filledCommercialRisks.
     *
     * @param filledCommercialRisks the filledCommercialRisks to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated filledCommercialRisks,
     * or with status 400 (Bad Request) if the filledCommercialRisks is not valid,
     * or with status 500 (Internal Server Error) if the filledCommercialRisks couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/filled-commercial-risks")
    @Timed
    public ResponseEntity<FilledCommercialRisks> updateFilledCommercialRisks(@Valid @RequestBody FilledCommercialRisks filledCommercialRisks) throws URISyntaxException {
        log.debug("REST request to update FilledCommercialRisks : {}", filledCommercialRisks);
        if (filledCommercialRisks.getId() == null) {
            return createFilledCommercialRisks(filledCommercialRisks);
        }
        FilledCommercialRisks result = filledCommercialRisksService.save(filledCommercialRisks);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, filledCommercialRisks.getId().toString()))
            .body(result);
    }

    /**
     * GET  /filled-commercial-risks : get all the filledCommercialRisks.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of filledCommercialRisks in body
     */
    @GetMapping("/filled-commercial-risks")
    @Timed
    public ResponseEntity<List<FilledCommercialRisks>> getAllFilledCommercialRisks(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of FilledCommercialRisks");
        Page<FilledCommercialRisks> page = filledCommercialRisksService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/filled-commercial-risks");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /filled-commercial-risks/:id : get the "id" filledCommercialRisks.
     *
     * @param id the id of the filledCommercialRisks to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the filledCommercialRisks, or with status 404 (Not Found)
     */
    @GetMapping("/filled-commercial-risks/{id}")
    @Timed
    public ResponseEntity<FilledCommercialRisks> getFilledCommercialRisks(@PathVariable Long id) {
        log.debug("REST request to get FilledCommercialRisks : {}", id);
        FilledCommercialRisks filledCommercialRisks = filledCommercialRisksService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(filledCommercialRisks));
    }

    /**
     * DELETE  /filled-commercial-risks/:id : delete the "id" filledCommercialRisks.
     *
     * @param id the id of the filledCommercialRisks to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/filled-commercial-risks/{id}")
    @Timed
    public ResponseEntity<Void> deleteFilledCommercialRisks(@PathVariable Long id) {
        log.debug("REST request to delete FilledCommercialRisks : {}", id);
        filledCommercialRisksService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
