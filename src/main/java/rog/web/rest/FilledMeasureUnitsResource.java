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
import rog.domain.FilledMeasureUnits;
import rog.service.FilledMeasureUnitsService;
import rog.web.rest.errors.BadRequestAlertException;
import rog.web.rest.util.HeaderUtil;
import rog.web.rest.util.PaginationUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing FilledMeasureUnits.
 */
@RestController
@RequestMapping("/api")
public class FilledMeasureUnitsResource {

    private final Logger log = LoggerFactory.getLogger(FilledMeasureUnitsResource.class);

    private static final String ENTITY_NAME = "filledMeasureUnits";

    @Autowired
    private FilledMeasureUnitsService filledMeasureUnitsService;

    /**
     * POST  /filled-measure-units : Create a new filledMeasureUnits.
     *
     * @param filledMeasureUnits the filledMeasureUnits to create
     * @return the ResponseEntity with status 201 (Created) and with body the new filledMeasureUnits, or with status 400 (Bad Request) if the filledMeasureUnits has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/filled-measure-units")
    @Timed
    public ResponseEntity<FilledMeasureUnits> createFilledMeasureUnits(@RequestBody FilledMeasureUnits filledMeasureUnits) throws URISyntaxException {
        log.debug("REST request to save FilledMeasureUnits : {}", filledMeasureUnits);
        if (filledMeasureUnits.getId() != null) {
            throw new BadRequestAlertException("A new filledMeasureUnits cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FilledMeasureUnits result = filledMeasureUnitsService.save(filledMeasureUnits);
        return ResponseEntity.created(new URI("/api/filled-measure-units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /filled-measure-units : Updates an existing filledMeasureUnits.
     *
     * @param filledMeasureUnits the filledMeasureUnits to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated filledMeasureUnits,
     * or with status 400 (Bad Request) if the filledMeasureUnits is not valid,
     * or with status 500 (Internal Server Error) if the filledMeasureUnits couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/filled-measure-units")
    @Timed
    public ResponseEntity<FilledMeasureUnits> updateFilledMeasureUnits(@RequestBody FilledMeasureUnits filledMeasureUnits) throws URISyntaxException {
        log.debug("REST request to update FilledMeasureUnits : {}", filledMeasureUnits);
        if (filledMeasureUnits.getId() == null) {
            return createFilledMeasureUnits(filledMeasureUnits);
        }
        FilledMeasureUnits result = filledMeasureUnitsService.save(filledMeasureUnits);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, filledMeasureUnits.getId().toString()))
            .body(result);
    }

    /**
     * GET  /filled-measure-units : get all the filledMeasureUnits.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of filledMeasureUnits in body
     */
    @GetMapping("/filled-measure-units")
    @Timed
    public ResponseEntity<List<FilledMeasureUnits>> getAllFilledMeasureUnits(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of FilledMeasureUnits");
        Page<FilledMeasureUnits> page = filledMeasureUnitsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/filled-measure-units");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /filled-measure-units/:id : get the "id" filledMeasureUnits.
     *
     * @param id the id of the filledMeasureUnits to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the filledMeasureUnits, or with status 404 (Not Found)
     */
    @GetMapping("/filled-measure-units/{id}")
    @Timed
    public ResponseEntity<FilledMeasureUnits> getFilledMeasureUnits(@PathVariable Long id) {
        log.debug("REST request to get FilledMeasureUnits : {}", id);
        FilledMeasureUnits filledMeasureUnits = filledMeasureUnitsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(filledMeasureUnits));
    }

    /**
     * DELETE  /filled-measure-units/:id : delete the "id" filledMeasureUnits.
     *
     * @param id the id of the filledMeasureUnits to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/filled-measure-units/{id}")
    @Timed
    public ResponseEntity<Void> deleteFilledMeasureUnits(@PathVariable Long id) {
        log.debug("REST request to delete FilledMeasureUnits : {}", id);
        filledMeasureUnitsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
