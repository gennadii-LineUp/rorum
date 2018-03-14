package rog.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rog.domain.DecisionForRisk;
import rog.service.DecisionForRiskService;
import rog.web.rest.errors.BadRequestAlertException;
import rog.web.rest.util.HeaderUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DecisionForRisk.
 */
@RestController
@RequestMapping("/api")
public class DecisionForRiskResource {

    private final Logger log = LoggerFactory.getLogger(DecisionForRiskResource.class);

    private static final String ENTITY_NAME = "decisionForRisk";

    @Autowired
    private DecisionForRiskService decisionForRiskService;

    /**
     * POST  /decision-for-risks : Create a new decisionForRisk.
     *
     * @param decisionForRisk the decisionForRisk to create
     * @return the ResponseEntity with status 201 (Created) and with body the new decisionForRisk, or with status 400 (Bad Request) if the decisionForRisk has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/decision-for-risks")
    @Timed
    public ResponseEntity<DecisionForRisk> createDecisionForRisk(@Valid @RequestBody DecisionForRisk decisionForRisk) throws URISyntaxException {
        log.debug("REST request to save DecisionForRisk : {}", decisionForRisk);
        if (decisionForRisk.getId() != null) {
            throw new BadRequestAlertException("A new decisionForRisk cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DecisionForRisk result = decisionForRiskService.save(decisionForRisk);
        return ResponseEntity.created(new URI("/api/decision-for-risks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /decision-for-risks : Updates an existing decisionForRisk.
     *
     * @param decisionForRisk the decisionForRisk to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated decisionForRisk,
     * or with status 400 (Bad Request) if the decisionForRisk is not valid,
     * or with status 500 (Internal Server Error) if the decisionForRisk couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/decision-for-risks")
    @Timed
    public ResponseEntity<DecisionForRisk> updateDecisionForRisk(@Valid @RequestBody DecisionForRisk decisionForRisk) throws URISyntaxException {
        log.debug("REST request to update DecisionForRisk : {}", decisionForRisk);
        if (decisionForRisk.getId() == null) {
            return createDecisionForRisk(decisionForRisk);
        }
        DecisionForRisk result = decisionForRiskService.save(decisionForRisk);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, decisionForRisk.getId().toString()))
            .body(result);
    }

    /**
     * GET  /decision-for-risks : get all the decisionForRisks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of decisionForRisks in body
     */
    @GetMapping("/decision-for-risks")
    @Timed
    public List<DecisionForRisk> getAllDecisionForRisks() {
        log.debug("REST request to get all DecisionForRisks");
        return decisionForRiskService.findAll();
        }

    /**
     * GET  /decision-for-risks/:id : get the "id" decisionForRisk.
     *
     * @param id the id of the decisionForRisk to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the decisionForRisk, or with status 404 (Not Found)
     */
    @GetMapping("/decision-for-risks/{id}")
    @Timed
    public ResponseEntity<DecisionForRisk> getDecisionForRisk(@PathVariable Long id) {
        log.debug("REST request to get DecisionForRisk : {}", id);
        DecisionForRisk decisionForRisk = decisionForRiskService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(decisionForRisk));
    }

    /**
     * DELETE  /decision-for-risks/:id : delete the "id" decisionForRisk.
     *
     * @param id the id of the decisionForRisk to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/decision-for-risks/{id}")
    @Timed
    public ResponseEntity<Void> deleteDecisionForRisk(@PathVariable Long id) {
        log.debug("REST request to delete DecisionForRisk : {}", id);
        decisionForRiskService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
