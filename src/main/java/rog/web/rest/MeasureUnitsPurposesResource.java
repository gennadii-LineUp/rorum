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
import rog.domain.MeasureUnitsPurposes;
import rog.service.MeasureUnitsPurposesService;
import rog.web.rest.errors.BadRequestAlertException;
import rog.web.rest.util.HeaderUtil;
import rog.web.rest.util.PaginationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MeasureUnitsPurposes.
 */
@RestController
@RequestMapping("/api")
public class MeasureUnitsPurposesResource {

    private final Logger log = LoggerFactory.getLogger(MeasureUnitsPurposesResource.class);

    private static final String ENTITY_NAME = "measureUnitsPurposes";

    @Autowired
    private MeasureUnitsPurposesService measureUnitsPurposesService;

    /**
     * POST  /measure-units-purposes : Create a new measureUnitsPurposes.
     *
     * @param measureUnitsPurposes the measureUnitsPurposes to create
     * @return the ResponseEntity with status 201 (Created) and with body the new measureUnitsPurposes, or with status 400 (Bad Request) if the measureUnitsPurposes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/measure-units-purposes")
    @Timed
    public ResponseEntity<MeasureUnitsPurposes> createMeasureUnitsPurposes(@Valid @RequestBody MeasureUnitsPurposes measureUnitsPurposes) throws URISyntaxException {
        log.debug("REST request to save MeasureUnitsPurposes : {}", measureUnitsPurposes);
        if (measureUnitsPurposes.getId() != null) {
            throw new BadRequestAlertException("A new measureUnitsPurposes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MeasureUnitsPurposes result = measureUnitsPurposesService.save(measureUnitsPurposes);
        return ResponseEntity.created(new URI("/api/measure-units-purposes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /measure-units-purposes : Updates an existing measureUnitsPurposes.
     *
     * @param measureUnitsPurposes the measureUnitsPurposes to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated measureUnitsPurposes,
     * or with status 400 (Bad Request) if the measureUnitsPurposes is not valid,
     * or with status 500 (Internal Server Error) if the measureUnitsPurposes couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/measure-units-purposes")
    @Timed
    public ResponseEntity<MeasureUnitsPurposes> updateMeasureUnitsPurposes(@Valid @RequestBody MeasureUnitsPurposes measureUnitsPurposes) throws URISyntaxException {
        log.debug("REST request to update MeasureUnitsPurposes : {}", measureUnitsPurposes);
        if (measureUnitsPurposes.getId() == null) {
            return createMeasureUnitsPurposes(measureUnitsPurposes);
        }
        MeasureUnitsPurposes result = measureUnitsPurposesService.save(measureUnitsPurposes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, measureUnitsPurposes.getId().toString()))
            .body(result);
    }

    /**
     * GET  /measure-units-purposes : get all the measureUnitsPurposes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of measureUnitsPurposes in body
     */
    @GetMapping("/measure-units-purposes")
    @Timed
    public ResponseEntity<List<MeasureUnitsPurposes>> getAllMeasureUnitsPurposes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MeasureUnitsPurposes");
        Page<MeasureUnitsPurposes> page = measureUnitsPurposesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/measure-units-purposes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /measure-units-purposes/:id : get the "id" measureUnitsPurposes.
     *
     * @param id the id of the measureUnitsPurposes to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the measureUnitsPurposes, or with status 404 (Not Found)
     */
    @GetMapping("/measure-units-purposes/{id}")
    @Timed
    public ResponseEntity<MeasureUnitsPurposes> getMeasureUnitsPurposes(@PathVariable Long id) {
        log.debug("REST request to get MeasureUnitsPurposes : {}", id);
        MeasureUnitsPurposes measureUnitsPurposes = measureUnitsPurposesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(measureUnitsPurposes));
    }

    /**
     * DELETE  /measure-units-purposes/:id : delete the "id" measureUnitsPurposes.
     *
     * @param id the id of the measureUnitsPurposes to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/measure-units-purposes/{id}")
    @Timed
    public ResponseEntity<Void> deleteMeasureUnitsPurposes(@PathVariable Long id) {
        log.debug("REST request to delete MeasureUnitsPurposes : {}", id);
        measureUnitsPurposesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
