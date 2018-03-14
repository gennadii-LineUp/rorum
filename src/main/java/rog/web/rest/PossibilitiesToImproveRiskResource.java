package rog.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rog.domain.PossibilitiesToImproveRisk;
import rog.service.PossibilitiesToImproveRiskService;
import rog.web.rest.errors.BadRequestAlertException;
import rog.web.rest.util.HeaderUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PossibilitiesToImproveRisk.
 */
@RestController
@RequestMapping("/api")
public class PossibilitiesToImproveRiskResource {

    private final Logger log = LoggerFactory.getLogger(PossibilitiesToImproveRiskResource.class);

    private static final String ENTITY_NAME = "possibilitiesToImproveRisk";

    private final PossibilitiesToImproveRiskService possibilitiesToImproveRiskService;

    public PossibilitiesToImproveRiskResource(PossibilitiesToImproveRiskService possibilitiesToImproveRiskService) {
        this.possibilitiesToImproveRiskService = possibilitiesToImproveRiskService;
    }

    /**
     * POST  /possibilities-to-improve-risks : Create a new possibilitiesToImproveRisk.
     *
     * @param possibilitiesToImproveRisk the possibilitiesToImproveRisk to create
     * @return the ResponseEntity with status 201 (Created) and with body the new possibilitiesToImproveRisk, or with status 400 (Bad Request) if the possibilitiesToImproveRisk has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/possibilities-to-improve-risks")
    @Timed
    public ResponseEntity<PossibilitiesToImproveRisk> createPossibilitiesToImproveRisk(@Valid @RequestBody PossibilitiesToImproveRisk possibilitiesToImproveRisk) throws URISyntaxException {
        log.debug("REST request to save PossibilitiesToImproveRisk : {}", possibilitiesToImproveRisk);
        if (possibilitiesToImproveRisk.getId() != null) {
            throw new BadRequestAlertException("A new possibilitiesToImproveRisk cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PossibilitiesToImproveRisk result = possibilitiesToImproveRiskService.save(possibilitiesToImproveRisk);
        return ResponseEntity.created(new URI("/api/possibilities-to-improve-risks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /possibilities-to-improve-risks : Updates an existing possibilitiesToImproveRisk.
     *
     * @param possibilitiesToImproveRisk the possibilitiesToImproveRisk to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated possibilitiesToImproveRisk,
     * or with status 400 (Bad Request) if the possibilitiesToImproveRisk is not valid,
     * or with status 500 (Internal Server Error) if the possibilitiesToImproveRisk couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/possibilities-to-improve-risks")
    @Timed
    public ResponseEntity<PossibilitiesToImproveRisk> updatePossibilitiesToImproveRisk(@Valid @RequestBody PossibilitiesToImproveRisk possibilitiesToImproveRisk) throws URISyntaxException {
        log.debug("REST request to update PossibilitiesToImproveRisk : {}", possibilitiesToImproveRisk);
        if (possibilitiesToImproveRisk.getId() == null) {
            return createPossibilitiesToImproveRisk(possibilitiesToImproveRisk);
        }
        PossibilitiesToImproveRisk result = possibilitiesToImproveRiskService.save(possibilitiesToImproveRisk);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, possibilitiesToImproveRisk.getId().toString()))
            .body(result);
    }

    /**
     * GET  /possibilities-to-improve-risks : get all the possibilitiesToImproveRisks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of possibilitiesToImproveRisks in body
     */
    @GetMapping("/possibilities-to-improve-risks")
    @Timed
    public List<PossibilitiesToImproveRisk> getAllPossibilitiesToImproveRisks() {
        log.debug("REST request to get all PossibilitiesToImproveRisks");
        return possibilitiesToImproveRiskService.findAll();
        }

    /**
     * GET  /possibilities-to-improve-risks/:id : get the "id" possibilitiesToImproveRisk.
     *
     * @param id the id of the possibilitiesToImproveRisk to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the possibilitiesToImproveRisk, or with status 404 (Not Found)
     */
    @GetMapping("/possibilities-to-improve-risks/{id}")
    @Timed
    public ResponseEntity<PossibilitiesToImproveRisk> getPossibilitiesToImproveRisk(@PathVariable Long id) {
        log.debug("REST request to get PossibilitiesToImproveRisk : {}", id);
        PossibilitiesToImproveRisk possibilitiesToImproveRisk = possibilitiesToImproveRiskService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(possibilitiesToImproveRisk));
    }

    /**
     * DELETE  /possibilities-to-improve-risks/:id : delete the "id" possibilitiesToImproveRisk.
     *
     * @param id the id of the possibilitiesToImproveRisk to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/possibilities-to-improve-risks/{id}")
    @Timed
    public ResponseEntity<Void> deletePossibilitiesToImproveRisk(@PathVariable Long id) {
        log.debug("REST request to delete PossibilitiesToImproveRisk : {}", id);
        possibilitiesToImproveRiskService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
