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
import rog.domain.CommercialRisksPurposes;
import rog.security.AuthoritiesConstants;
import rog.service.CommercialRisksPurposesService;
import rog.web.rest.errors.BadRequestAlertException;
import rog.web.rest.util.HeaderUtil;
import rog.web.rest.util.PaginationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CommercialRisksPurposes.
 */
@RestController
@RequestMapping("/api")
public class CommercialRisksPurposesResource {

    private final Logger log = LoggerFactory.getLogger(CommercialRisksPurposesResource.class);

    private static final String ENTITY_NAME = "commercialRisksPurposes";

    @Autowired
    private CommercialRisksPurposesService commercialRisksPurposesService;

    /**
     * POST  /commercial-risks-purposes : Create a new commercialRisksPurposes.
     *
     * @param commercialRisksPurposes the commercialRisksPurposes to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commercialRisksPurposes, or with status 400 (Bad Request) if the commercialRisksPurposes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commercial-risks-purposes")
    @Timed
    public ResponseEntity<CommercialRisksPurposes> createCommercialRisksPurposes(@Valid @RequestBody CommercialRisksPurposes commercialRisksPurposes) throws URISyntaxException {
        log.debug("REST request to save CommercialRisksPurposes : {}", commercialRisksPurposes);
        if (commercialRisksPurposes.getId() != null) {
            throw new BadRequestAlertException("A new commercialRisksPurposes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommercialRisksPurposes result = commercialRisksPurposesService.save(commercialRisksPurposes);
        return ResponseEntity.created(new URI("/api/commercial-risks-purposes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commercial-risks-purposes : Updates an existing commercialRisksPurposes.
     *
     * @param commercialRisksPurposes the commercialRisksPurposes to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commercialRisksPurposes,
     * or with status 400 (Bad Request) if the commercialRisksPurposes is not valid,
     * or with status 500 (Internal Server Error) if the commercialRisksPurposes couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commercial-risks-purposes")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<CommercialRisksPurposes> updateCommercialRisksPurposes(@Valid @RequestBody CommercialRisksPurposes commercialRisksPurposes) throws URISyntaxException {
        log.debug("REST request to update CommercialRisksPurposes : {}", commercialRisksPurposes);
        if (commercialRisksPurposes.getId() == null) {
            return createCommercialRisksPurposes(commercialRisksPurposes);
        }
        CommercialRisksPurposes result = commercialRisksPurposesService.save(commercialRisksPurposes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commercialRisksPurposes.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commercial-risks-purposes : get all the commercialRisksPurposes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of commercialRisksPurposes in body
     */
    @GetMapping("/commercial-risks-purposes")
    @Timed
    public ResponseEntity<List<CommercialRisksPurposes>> getAllCommercialRisksPurposes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of CommercialRisksPurposes");
        Page<CommercialRisksPurposes> page = commercialRisksPurposesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/commercial-risks-purposes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /commercial-risks-purposes/:id : get the "id" commercialRisksPurposes.
     *
     * @param id the id of the commercialRisksPurposes to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commercialRisksPurposes, or with status 404 (Not Found)
     */
    @GetMapping("/commercial-risks-purposes/{id}")
    @Timed
    public ResponseEntity<CommercialRisksPurposes> getCommercialRisksPurposes(@PathVariable Long id) {
        log.debug("REST request to get CommercialRisksPurposes : {}", id);
        CommercialRisksPurposes commercialRisksPurposes = commercialRisksPurposesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(commercialRisksPurposes));
    }

    /**
     * DELETE  /commercial-risks-purposes/:id : delete the "id" commercialRisksPurposes.
     *
     * @param id the id of the commercialRisksPurposes to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commercial-risks-purposes/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommercialRisksPurposes(@PathVariable Long id) {
        log.debug("REST request to delete CommercialRisksPurposes : {}", id);
        commercialRisksPurposesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
