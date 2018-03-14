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
import rog.domain.RisksPurposes;
import rog.service.RisksPurposesService;
import rog.web.rest.errors.BadRequestAlertException;
import rog.web.rest.util.HeaderUtil;
import rog.web.rest.util.PaginationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RisksPurposes.
 */
@RestController
@RequestMapping("/api")
public class RisksPurposesResource {

    private final Logger log = LoggerFactory.getLogger(RisksPurposesResource.class);

    private static final String ENTITY_NAME = "risksPurposes";

    @Autowired
    private RisksPurposesService risksPurposesService;

    /**
     * POST  /risks-purposes : Create a new risksPurposes.
     *
     * @param risksPurposes the risksPurposes to create
     * @return the ResponseEntity with status 201 (Created) and with body the new risksPurposes, or with status 400 (Bad Request) if the risksPurposes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/risks-purposes")
    @Timed
    public ResponseEntity<RisksPurposes> createRisksPurposes(@Valid @RequestBody RisksPurposes risksPurposes) throws URISyntaxException {
        log.debug("REST request to save RisksPurposes : {}", risksPurposes);
        if (risksPurposes.getId() != null) {
            throw new BadRequestAlertException("A new risksPurposes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RisksPurposes result = risksPurposesService.save(risksPurposes);
        return ResponseEntity.created(new URI("/api/risks-purposes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /risks-purposes : Updates an existing risksPurposes.
     *
     * @param risksPurposes the risksPurposes to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated risksPurposes,
     * or with status 400 (Bad Request) if the risksPurposes is not valid,
     * or with status 500 (Internal Server Error) if the risksPurposes couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/risks-purposes")
    @Timed
    public ResponseEntity<RisksPurposes> updateRisksPurposes(@Valid @RequestBody RisksPurposes risksPurposes) throws URISyntaxException {
        log.debug("REST request to update RisksPurposes : {}", risksPurposes);
        if (risksPurposes.getId() == null) {
            return createRisksPurposes(risksPurposes);
        }
        RisksPurposes result = risksPurposesService.save(risksPurposes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, risksPurposes.getId().toString()))
            .body(result);
    }

    /**
     * GET  /risks-purposes : get all the risksPurposes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of risksPurposes in body
     */
    @GetMapping("/risks-purposes")
    @Timed
    public ResponseEntity<List<RisksPurposes>> getAllRisksPurposes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RisksPurposes");
        Page<RisksPurposes> page = risksPurposesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/risks-purposes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /risks-purposes/:id : get the "id" risksPurposes.
     *
     * @param id the id of the risksPurposes to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the risksPurposes, or with status 404 (Not Found)
     */
    @GetMapping("/risks-purposes/{id}")
    @Timed
    public ResponseEntity<RisksPurposes> getRisksPurposes(@PathVariable Long id) {
        log.debug("REST request to get RisksPurposes : {}", id);
        RisksPurposes risksPurposes = risksPurposesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(risksPurposes));
    }

    /**
     * DELETE  /risks-purposes/:id : delete the "id" risksPurposes.
     *
     * @param id the id of the risksPurposes to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/risks-purposes/{id}")
    @Timed
    public ResponseEntity<Void> deleteRisksPurposes(@PathVariable Long id) {
        log.debug("REST request to delete RisksPurposes : {}", id);
        risksPurposesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
