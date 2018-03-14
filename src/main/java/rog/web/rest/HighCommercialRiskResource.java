package rog.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rog.domain.HighCommercialRisk;
import rog.service.HighCommercialRiskService;
import rog.web.rest.errors.BadRequestAlertException;
import rog.web.rest.util.HeaderUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing HighCommercialRisk.
 */
@RestController
@RequestMapping("/api")
public class HighCommercialRiskResource {

    private final Logger log = LoggerFactory.getLogger(HighCommercialRiskResource.class);

    private static final String ENTITY_NAME = "highCommercialRisk";

    private final HighCommercialRiskService highCommercialRiskService;

    public HighCommercialRiskResource(HighCommercialRiskService highCommercialRiskService) {
        this.highCommercialRiskService = highCommercialRiskService;
    }

    /**
     * POST  /high-commercial-risks : Create a new highCommercialRisk.
     *
     * @param highCommercialRisk the highCommercialRisk to create
     * @return the ResponseEntity with status 201 (Created) and with body the new highCommercialRisk, or with status 400 (Bad Request) if the highCommercialRisk has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/high-commercial-risks")
    @Timed
    public ResponseEntity<HighCommercialRisk> createHighCommercialRisk(@Valid @RequestBody HighCommercialRisk highCommercialRisk) throws URISyntaxException {
        log.debug("REST request to save HighCommercialRisk : {}", highCommercialRisk);
        if (highCommercialRisk.getId() != null) {
            throw new BadRequestAlertException("A new highCommercialRisk cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HighCommercialRisk result = highCommercialRiskService.save(highCommercialRisk);
        return ResponseEntity.created(new URI("/api/high-commercial-risks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /high-commercial-risks : Updates an existing highCommercialRisk.
     *
     * @param highCommercialRisk the highCommercialRisk to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated highCommercialRisk,
     * or with status 400 (Bad Request) if the highCommercialRisk is not valid,
     * or with status 500 (Internal Server Error) if the highCommercialRisk couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/high-commercial-risks")
    @Timed
    public ResponseEntity<HighCommercialRisk> updateHighCommercialRisk(@Valid @RequestBody HighCommercialRisk highCommercialRisk) throws URISyntaxException {
        log.debug("REST request to update HighCommercialRisk : {}", highCommercialRisk);
        if (highCommercialRisk.getId() == null) {
            return createHighCommercialRisk(highCommercialRisk);
        }
        HighCommercialRisk result = highCommercialRiskService.save(highCommercialRisk);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, highCommercialRisk.getId().toString()))
            .body(result);
    }

    /**
     * GET  /high-commercial-risks : get all the highCommercialRisks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of highCommercialRisks in body
     */
    @GetMapping("/high-commercial-risks")
    @Timed
    public List<HighCommercialRisk> getAllHighCommercialRisks() {
        log.debug("REST request to get all HighCommercialRisks");
        return highCommercialRiskService.findAll();
        }

    /**
     * GET  /high-commercial-risks/:id : get the "id" highCommercialRisk.
     *
     * @param id the id of the highCommercialRisk to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the highCommercialRisk, or with status 404 (Not Found)
     */
    @GetMapping("/high-commercial-risks/{id}")
    @Timed
    public ResponseEntity<HighCommercialRisk> getHighCommercialRisk(@PathVariable Long id) {
        log.debug("REST request to get HighCommercialRisk : {}", id);
        HighCommercialRisk highCommercialRisk = highCommercialRiskService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(highCommercialRisk));
    }

    /**
     * DELETE  /high-commercial-risks/:id : delete the "id" highCommercialRisk.
     *
     * @param id the id of the highCommercialRisk to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/high-commercial-risks/{id}")
    @Timed
    public ResponseEntity<Void> deleteHighCommercialRisk(@PathVariable Long id) {
        log.debug("REST request to delete HighCommercialRisk : {}", id);
        highCommercialRiskService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
