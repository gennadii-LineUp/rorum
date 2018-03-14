package rog.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rog.domain.HighRisk;
import rog.service.HighRiskService;
import rog.web.rest.errors.BadRequestAlertException;
import rog.web.rest.util.HeaderUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing HighRisk.
 */
@RestController
@RequestMapping("/api")
public class HighRiskResource {

    private final Logger log = LoggerFactory.getLogger(HighRiskResource.class);

    private static final String ENTITY_NAME = "highRisk";

    @Autowired
    private HighRiskService highRiskService;

    /**
     * POST  /high-risks : Create a new highRisk.
     *
     * @param highRisk the highRisk to create
     * @return the ResponseEntity with status 201 (Created) and with body the new highRisk, or with status 400 (Bad Request) if the highRisk has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/high-risks")
    @Timed
    public ResponseEntity<HighRisk> createHighRisk(@Valid @RequestBody HighRisk highRisk) throws URISyntaxException {
        log.debug("REST request to save HighRisk : {}", highRisk);
        if (highRisk.getId() != null) {
            throw new BadRequestAlertException("A new highRisk cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HighRisk result = highRiskService.save(highRisk);
        return ResponseEntity.created(new URI("/api/high-risks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /high-risks : Updates an existing highRisk.
     *
     * @param highRisk the highRisk to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated highRisk,
     * or with status 400 (Bad Request) if the highRisk is not valid,
     * or with status 500 (Internal Server Error) if the highRisk couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/high-risks")
    @Timed
    public ResponseEntity<HighRisk> updateHighRisk(@Valid @RequestBody HighRisk highRisk) throws URISyntaxException {
        log.debug("REST request to update HighRisk : {}", highRisk);
        if (highRisk.getId() == null) {
            return createHighRisk(highRisk);
        }
        HighRisk result = highRiskService.save(highRisk);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, highRisk.getId().toString()))
            .body(result);
    }

    /**
     * GET  /high-risks : get all the highRisks.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of highRisks in body
     */
    @GetMapping("/high-risks")
    @Timed
    public List<HighRisk> getAllHighRisks(@RequestParam(required = false) String filter) {
        if ("filledrisks-is-null".equals(filter)) {
            log.debug("REST request to get all HighRisks where filledRisks is null");
            return highRiskService.findAllWhereFilledRisksIsNull();
        }
        log.debug("REST request to get all HighRisks");
        return highRiskService.findAll();
        }

    /**
     * GET  /high-risks/:id : get the "id" highRisk.
     *
     * @param id the id of the highRisk to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the highRisk, or with status 404 (Not Found)
     */
    @GetMapping("/high-risks/{id}")
    @Timed
    public ResponseEntity<HighRisk> getHighRisk(@PathVariable Long id) {
        log.debug("REST request to get HighRisk : {}", id);
        HighRisk highRisk = highRiskService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(highRisk));
    }

    /**
     * DELETE  /high-risks/:id : delete the "id" highRisk.
     *
     * @param id the id of the highRisk to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/high-risks/{id}")
    @Timed
    public ResponseEntity<Void> deleteHighRisk(@PathVariable Long id) {
        log.debug("REST request to delete HighRisk : {}", id);
        highRiskService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
