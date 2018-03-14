package rog.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import net.sf.dynamicreports.report.exception.DRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;
import rog.domain.SetOfSentPurposes;
import rog.service.OrdersService;
import rog.service.PurposeAccomplishmentReportService;
import rog.service.SetOfSentPurposesService;
import rog.service.dto.SetOfSentPurposesDTO;
import rog.service.excel.RisksRegisterReport;
import rog.service.mapper.SetOfSentPurposesMapper;
import rog.web.rest.errors.BadRequestAlertException;
import rog.web.rest.util.HeaderUtil;
import rog.web.rest.util.PaginationUtil;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SetOfSentPurposes.
 */
@RestController
@RequestMapping("/api")
public class SetOfSentPurposesResource {

    private final Logger log = LoggerFactory.getLogger(SetOfSentPurposesResource.class);

    private static final String ENTITY_NAME = "setOfSentPurposes";

    @Autowired
    private SetOfSentPurposesService setOfSentPurposesService;

    private final PurposeAccomplishmentReportService purposeAccomplishmentReportService;

    @Autowired
    private RisksRegisterReport risksRegisterReport;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private SetOfSentPurposesMapper setOfSentPurposesMapper;


public SetOfSentPurposesResource(PurposeAccomplishmentReportService purposeAccomplishmentReportService) {
        this.purposeAccomplishmentReportService = purposeAccomplishmentReportService;
    }
    /**
     * POST  /set-of-sent-purposes : Create a new setOfSentPurposes.
     *
     * @param setOfSentPurposes the setOfSentPurposes to create
     * @return the ResponseEntity with status 201 (Created) and with body the new setOfSentPurposes, or with status 400 (Bad Request) if the setOfSentPurposes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/set-of-sent-purposes")
    @Timed
    public ResponseEntity<SetOfSentPurposes> createSetOfSentPurposes(@RequestBody SetOfSentPurposesDTO setOfSentPurposes) throws URISyntaxException {
        log.debug("REST request to save SetOfSentPurposes : {}", setOfSentPurposes);
        if (setOfSentPurposes.getId() != null) {
            throw new BadRequestAlertException("A new setOfSentPurposes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SetOfSentPurposes result = setOfSentPurposesService.create(setOfSentPurposesMapper.
            setOfSentPurposes(setOfSentPurposes));
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /set-of-sent-purposes : Updates an existing setOfSentPurposes.
     *
     * @param setOfSentPurposes the setOfSentPurposes to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated setOfSentPurposes,
     * or with status 400 (Bad Request) if the setOfSentPurposes is not valid,
     * or with status 500 (Internal Server Error) if the setOfSentPurposes couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/set-of-sent-purposes")
    @Timed
    public ResponseEntity<SetOfSentPurposes> updateSetOfSentPurposes(@RequestBody SetOfSentPurposesDTO setOfSentPurposes) throws URISyntaxException {
        log.debug("REST request to update SetOfSentPurposes : {}", setOfSentPurposes);
        if (setOfSentPurposes.getId() == null) {
            return createSetOfSentPurposes(setOfSentPurposes);
        }
        SetOfSentPurposes result = setOfSentPurposesService.create(setOfSentPurposesMapper.
            setOfSentPurposes(setOfSentPurposes));
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * GET  /set-of-sent-purposes : get all the setOfSentPurposes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of setOfSentPurposes in body
     */
    @GetMapping("/set-of-sent-purposes")
    @Timed
    public ResponseEntity<List<SetOfSentPurposes>> getAllSetOfSentPurposes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of SetOfSentPurposes");
        Page<SetOfSentPurposes> page = setOfSentPurposesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/set-of-sent-purposes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /set-of-sent-purposes/:id : get the "id" setOfSentPurposes.
     *
     * @param id the id of the setOfSentPurposes to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the setOfSentPurposes, or with status 404 (Not Found)
     */
    @GetMapping("/set-of-sent-purposes/{id}")
    @Timed
    public ResponseEntity<SetOfSentPurposes> getSetOfSentPurposes(@PathVariable Long id) {
        log.debug("REST request to get SetOfSentPurposes : {}", id);
        SetOfSentPurposes setOfSentPurposes = setOfSentPurposesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(setOfSentPurposes));
    }

    /**
     * DELETE  /set-of-sent-purposes/:id : delete the "id" setOfSentPurposes.
     *
     * @param id the id of the setOfSentPurposes to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/set-of-sent-purposes/{id}")
    @Timed
    public ResponseEntity<Void> deleteSetOfSentPurposes(@PathVariable Long id) {
        log.debug("REST request to delete SetOfSentPurposes : {}", id);
        setOfSentPurposesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/set-of-sent-purposes/{orderId}/excel/purpose-accomplishement/{userId}")
    @Timed
    public ResponseEntity<byte[]> getSetOfSentPurposes(@PathVariable Long orderId, @PathVariable Long userId) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, DRException {
//        log.debug("REST request to get SetOfSentPurposes : {}", orderId, organisatioStructureId);
//
//        Orders orders = ordersService.findOne(orderId);
//        Set<GlossaryOfPurposes> glossaryOfPurposes =  setOfSentPurposesService.findOneByOrderIdAndUserId(orderId, userId);


//        return purposeAccomplishmentRaport.createReport(orders, orderId, organisatioStructureId, userId, glossaryOfPurposes);
        log.debug("SetOfSentPurposesResource- orderId:" + orderId + " userId:"+  userId);

        byte[] purposeAccomplishmentReport =
            purposeAccomplishmentReportService.generatePurposeAccomplishmentReport(orderId, userId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("filename", "");

        return new ResponseEntity<>(purposeAccomplishmentReport, headers, HttpStatus.OK);
        }
}
