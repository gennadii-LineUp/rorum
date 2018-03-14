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
import rog.domain.GlossaryOfProcesses;
import rog.service.GlossaryOfProcessesService;
import rog.web.rest.errors.BadRequestAlertException;
import rog.web.rest.util.HeaderUtil;
import rog.web.rest.util.PaginationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing GlossaryOfProcesses.
 */
@RestController
@RequestMapping("/api")
public class GlossaryOfProcessesResource {

    private final Logger log = LoggerFactory.getLogger(GlossaryOfProcessesResource.class);

    private static final String ENTITY_NAME = "glossaryOfProcesses";

    @Autowired
    private GlossaryOfProcessesService glossaryOfProcessesService;

    /**
     * POST  /glossary-of-processes : Create a new glossaryOfProcesses.
     *
     * @param glossaryOfProcesses the glossaryOfProcesses to create
     * @return the ResponseEntity with status 201 (Created) and with body the new glossaryOfProcesses, or with status 400 (Bad Request) if the glossaryOfProcesses has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/glossary-of-processes")
    @Timed
    public ResponseEntity<GlossaryOfProcesses> createGlossaryOfProcesses(@Valid @RequestBody GlossaryOfProcesses glossaryOfProcesses) throws URISyntaxException {
        log.debug("REST request to save GlossaryOfProcesses : {}", glossaryOfProcesses);
        if (glossaryOfProcesses.getId() != null) {
            throw new BadRequestAlertException("A new glossaryOfProcesses cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GlossaryOfProcesses result = glossaryOfProcessesService.save(glossaryOfProcesses);
        return ResponseEntity.created(new URI("/api/glossary-of-processes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /glossary-of-processes : Updates an existing glossaryOfProcesses.
     *
     * @param glossaryOfProcesses the glossaryOfProcesses to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated glossaryOfProcesses,
     * or with status 400 (Bad Request) if the glossaryOfProcesses is not valid,
     * or with status 500 (Internal Server Error) if the glossaryOfProcesses couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/glossary-of-processes")
    @Timed
    public ResponseEntity<GlossaryOfProcesses> updateGlossaryOfProcesses(@Valid @RequestBody GlossaryOfProcesses glossaryOfProcesses) throws URISyntaxException {
        log.debug("REST request to update GlossaryOfProcesses : {}", glossaryOfProcesses);
        if (glossaryOfProcesses.getId() == null) {
            return createGlossaryOfProcesses(glossaryOfProcesses);
        }
        GlossaryOfProcesses result = glossaryOfProcessesService.save(glossaryOfProcesses);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, glossaryOfProcesses.getId().toString()))
            .body(result);
    }

    /**
     * GET  /glossary-of-processes : get all the glossaryOfProcesses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of glossaryOfProcesses in body
     */
    @GetMapping("/glossary-of-processes")
    @Timed
    public ResponseEntity<List<GlossaryOfProcesses>> getAllGlossaryOfProcesses(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of GlossaryOfProcesses");
        Page<GlossaryOfProcesses> page = glossaryOfProcessesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/glossary-of-processes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /glossary-of-processes/:id : get the "id" glossaryOfProcesses.
     *
     * @param id the id of the glossaryOfProcesses to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the glossaryOfProcesses, or with status 404 (Not Found)
     */
    @GetMapping("/glossary-of-processes/{id}")
    @Timed
    public ResponseEntity<GlossaryOfProcesses> getGlossaryOfProcesses(@PathVariable Long id) {
        log.debug("REST request to get GlossaryOfProcesses : {}", id);
        GlossaryOfProcesses glossaryOfProcesses = glossaryOfProcessesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(glossaryOfProcesses));
    }

    /**
     * DELETE  /glossary-of-processes/:id : delete the "id" glossaryOfProcesses.
     *
     * @param id the id of the glossaryOfProcesses to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/glossary-of-processes/{id}")
    @Timed
    public ResponseEntity<Void> deleteGlossaryOfProcesses(@PathVariable Long id) {
        log.debug("REST request to delete GlossaryOfProcesses : {}", id);
        glossaryOfProcessesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
