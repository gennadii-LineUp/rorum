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
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;
import rog.domain.*;
import rog.domain.enumeration.StatusOfSending;
import rog.security.AuthoritiesConstants;
import rog.service.*;
import rog.service.dto.SetOfSentPurposesDTO;
import rog.service.mapper.SetOfSentPurposesMapper;
import rog.service.validation.SetOfSentPurposesDTOValidator;
import rog.web.rest.errors.BadRequestAlertException;
import rog.web.rest.util.HeaderUtil;
import rog.web.rest.util.PaginationUtil;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing Orders.
 */
@RestController
@RequestMapping("/api")
public class OrdersResource {

    private final Logger log = LoggerFactory.getLogger(OrdersResource.class);

    private static final String ENTITY_NAME = "orders";

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private UserService userService;

    @Autowired
    private SetOfSentPurposesService setOfSentPurposeService;

    @Autowired
    private OrganisationStructureService organisationStructureService;

    @Autowired
    private GlossaryOfPurposesService glossaryOfPurposesService;

    @Autowired
    private RisksRegisterReportService risksRegisterReportService;

    @Autowired
    private SetOfSentPurposesDTOValidator setOfSentPurposesDTOValidator;

    @Autowired
    private SetOfSentPurposesMapper setOfSentPurposesMapper;

    /**
     * POST  /orders : Create a new orders.
     *
     * @param orders the orders to create
     * @return the ResponseEntity with status 201 (Created) and with body the new orders, or with status 400 (Bad Request) if the orders has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/orders")
    @Timed
    public ResponseEntity<Orders> createOrders(@RequestBody Orders orders) throws URISyntaxException {
        log.debug("REST request to save Orders : {}", orders);
        if (orders.getId() != null) {
            throw new BadRequestAlertException("A new orders cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Orders result = ordersService.save(orders);
        return ResponseEntity.created(new URI("/api/orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /orders : Updates an existing orders.
     *
     * @param orders the orders to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated orders,
     * or with status 400 (Bad Request) if the orders is not valid,
     * or with status 500 (Internal Server Error) if the orders couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/orders")
    @Timed
    public ResponseEntity<Orders> updateOrders(@RequestBody Orders orders) throws URISyntaxException {
        log.debug("REST request to update Orders : {}", orders);
        if (orders.getId() == null) {
            return createOrders(orders);
        }
        Orders result = ordersService.save(orders);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, orders.getId().toString()))
            .body(result);
    }

    /**
     * GET  /orders : get all the orders.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of orders in body
     */
    @GetMapping("/orders")
    @Timed
    @Secured({AuthoritiesConstants.USER, AuthoritiesConstants.ADMIN})
    public ResponseEntity<List<Orders>> getAllOrders(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Orders");
        Page<Orders> page = ordersService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/orders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /orders/:sspId : get the "sspId" orders.
     *
     * @param id the sspId of the orders to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the orders, or with status 404 (Not Found)
     */
    @GetMapping("/orders/{id}")
    @Timed
    @Secured({AuthoritiesConstants.USER, AuthoritiesConstants.ADMIN})
    public ResponseEntity<Orders> getOrders(@PathVariable Long id) {
        log.debug("REST request to get Orders : {}", id);
        Orders orders = ordersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(orders));
    }

    /**
     * DELETE  /orders/:sspId : delete the "sspId" orders.
     *
     * @param id the sspId of the orders to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrders(@PathVariable Long id) {
        log.debug("REST request to delete Orders : {}", id);
        ordersService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/orders/{ordersId}/purposes")
    @Timed
    @Secured({AuthoritiesConstants.USER, AuthoritiesConstants.ADMIN})
    public ResponseEntity<List<GlossaryOfPurposes>> getListOfPurposesByOrganisationForUser(@PathVariable("ordersId") Long orderId) {
        log.debug("REST request to get list of Purposes : {}", orderId);

        List<GlossaryOfPurposes> list = glossaryOfPurposesService.getAllAssignmentToCellOfCurrentOrganisation();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(list));
    }

    @GetMapping("/orders/{ordersId}/set_of_sent_purposes/{sspId}/purposes")
    @Timed
    @Secured({AuthoritiesConstants.USER, AuthoritiesConstants.ADMIN})
    public ResponseEntity<List<GlossaryOfPurposes>> getListOfPurposesByOrganisationForAdmin(@PathVariable("ordersId") Long orderId,
                                                                                            @PathVariable("sspId") Long sspId) {
        log.debug("REST request to get list of Purposes : {}", orderId);

        List<GlossaryOfPurposes> list = glossaryOfPurposesService.getAllBySetOfSentPurposes(sspId);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(list));
    }

    @PostMapping("/orders/{ordersId}/set_of_sent_purposes")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<SetOfSentPurposesDTO> createSetOfSentPurpose(@PathVariable("ordersId") Long orderId,
                                                                       @RequestBody SetOfSentPurposesDTO setOfSentPurposesDTO) {
        log.debug("REST request to save SetOfSentPurposes : {}" + setOfSentPurposesDTO);

        SetOfSentPurposes newSetOfSentPurposes = setOfSentPurposesMapper.setOfSentPurposes(setOfSentPurposesDTO);

        if(!setOfSentPurposesDTOValidator.isValidSetOfSentPurposes(setOfSentPurposesDTO)){
            return ResponseEntity.badRequest().body(setOfSentPurposesDTO);
        }

        Orders order = new Orders();
        order.setId(orderId);
        newSetOfSentPurposes.setOrders(order);
        newSetOfSentPurposes.setUser(userService.getCurrentUser());
        newSetOfSentPurposes = setOfSentPurposeService.create(newSetOfSentPurposes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, newSetOfSentPurposes.getId().toString()))
            .body(setOfSentPurposesMapper.setOfSentPurposesDTO(newSetOfSentPurposes));
    }

    @PutMapping("/orders/{ordersId}/set_of_sent_purposes")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER})
    public ResponseEntity<SetOfSentPurposesDTO> updatePlan(@PathVariable("ordersId") Long orderId,
                                                           @RequestBody SetOfSentPurposesDTO setOfSentPurposesDTO) {
        log.debug("REST request to update SetOfSentPurposes : {}" + setOfSentPurposesDTO);

        if (setOfSentPurposesDTO.getId() == null) {
            return createSetOfSentPurpose(orderId, setOfSentPurposesDTO);
        }

        if(setOfSentPurposesDTOValidator.isValidWithFields(setOfSentPurposesDTO)) {
            return ResponseEntity.badRequest().body(setOfSentPurposesDTO);
        }

        SetOfSentPurposes newSetOfSentPurposes = setOfSentPurposesMapper.setOfSentPurposes(setOfSentPurposesDTO);
        SetOfSentPurposes savedSetOfSentPurposes = setOfSentPurposeService.findOne(newSetOfSentPurposes.getId());

        setParametersForPlan(savedSetOfSentPurposes, newSetOfSentPurposes);

        newSetOfSentPurposes = setOfSentPurposeService.update(savedSetOfSentPurposes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, setOfSentPurposesDTO.getId().toString()))
            .body(setOfSentPurposesMapper.setOfSentPurposesDTO(newSetOfSentPurposes));
    }

    private void setParametersForPlan(SetOfSentPurposes savedSetOfSentPurposes, SetOfSentPurposes newSetOfSentPurposes){
        StatusOfSending statusOfSending = newSetOfSentPurposes.getStatusOfSending();
        savedSetOfSentPurposes.setStatusOfSending(statusOfSending);

        if(statusOfSending.isRejectedPlan()) {
            savedSetOfSentPurposes.setNotation(newSetOfSentPurposes.getNotation());
        }

        setUserToFilledFields(newSetOfSentPurposes);
        addNewFilledMeasureUnitsToSetOfSentPurposes(savedSetOfSentPurposes, newSetOfSentPurposes);
        addNewFilledRisksToSetOfSentPurposes(savedSetOfSentPurposes, newSetOfSentPurposes);
        addNewFilledCommercialRisksToSetOfSentPurposes(savedSetOfSentPurposes, newSetOfSentPurposes);
        //addNewFilledKeyRiskIndicatorsToSetOfSentPurposes(savedSetOfSentPurposes, newSetOfSentPurposes);
    }

    @GetMapping("/orders/{ordersId}/set_of_sent_purposes/user")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<SetOfSentPurposesDTO> getSetOfSentPurposeForUser(@PathVariable("ordersId") Long orderId) {
        log.debug("REST request to get SetOfSentPurposes for user: {}");

        Long organisationId = organisationStructureService.getOneByCurrentUser().getId();

        SetOfSentPurposes setOfSentPurposes = setOfSentPurposeService.
            getOneByOrganisationIdAndOrderId(organisationId, orderId);

        SetOfSentPurposesDTO setOfSentPurposesDTO = setOfSentPurposesMapper.setOfSentPurposesDTO(setOfSentPurposes);
        return ResponseUtil.wrapOrNotFound(Optional.of(setOfSentPurposesDTO));
    }


    @GetMapping("/orders/{ordersId}/set_of_sent_purposes/local_admin")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<List<SetOfSentPurposes>> getListOfSetOfSentPurposeForLocalAdmin(@PathVariable("ordersId") Long orderId) {
        log.debug("REST request to get list of SetOfSentPurposes fot local admin: {}");

        Long organisationId = organisationStructureService.getOneByCurrentUser().getId();

        List<SetOfSentPurposes> list = setOfSentPurposeService.
            getAllByOrganisationOfAdminIdAndOrderId(organisationId, orderId);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(list));
    }

    @GetMapping("/orders/{ordersId}/set_of_sent_purposes/local_admin/parented")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<List<SetOfSentPurposes>> getListOfSetOfSentPurposeForLocalAdminOfParentOrganisation(@PathVariable("ordersId") Long orderId) {
        log.debug("REST request to get list of SetOfSentPurposes of parented organisation for local admin: {}");

        Long organisationId = organisationStructureService.getOneByCurrentUser().getId();

        List<SetOfSentPurposes> list = setOfSentPurposeService.
            getAllByParentedOrganisationsAndOrderId(organisationId, orderId);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(list));
    }

    @GetMapping("/orders/{ordersId}/set_of_sent_purposes/local_admin/supervisored")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<List<SetOfSentPurposes>> getListOfSetOfSentPurposeForLocalAdminOfSupervisorOrganisation(@PathVariable("ordersId") Long orderId) {
        log.debug("REST request to get list of SetOfSentPurposes of supervising organisation for local admin: {}");

        Long organisationId = organisationStructureService.getOneByCurrentUser().getId();

        List<SetOfSentPurposes> list = setOfSentPurposeService.
            getAllBySupervisingOrganisationsAndOrderId(organisationId, orderId);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(list));
    }

    @GetMapping("/orders/{ordersId}/set_of_sent_purposes/{sspId}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<SetOfSentPurposesDTO> getSetOfSentPurpose(@PathVariable("ordersId") Long orderId,
                                                                    @PathVariable("sspId") Long sspId) {
        log.debug("REST request to get SetOfSentPurposes by id and order: {}", orderId);

        SetOfSentPurposes setOfSentPurpose = setOfSentPurposeService.findOne(sspId);
        SetOfSentPurposesDTO setOfSentPurposesDTO = setOfSentPurposesMapper.setOfSentPurposesDTO(setOfSentPurpose);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(setOfSentPurposesDTO));
    }

    @GetMapping("/orders/{orderId}/excel/risks-register-raport/")
    @Timed
    public byte[] getSetOfSentPurposes(@PathVariable Long orderId) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, DRException {

        User user = userService.getCurrentUser();
        return risksRegisterReportService.generateRiskRegisterReport(orderId);
    }

    private void addNewFilledMeasureUnitsToSetOfSentPurposes(SetOfSentPurposes savedSetOfSentPurposes, SetOfSentPurposes newSetOfSentPurposes){
        Set<MeasureUnitsPurposes> savedMeasureUnitsPurposes = savedSetOfSentPurposes.getMeasureUnitsPurposes();
        Set<MeasureUnitsPurposes> newMeasureUnitsPurposes = newSetOfSentPurposes.getMeasureUnitsPurposes();

        for (MeasureUnitsPurposes listOfNewMeasureUnitsPurpose : newMeasureUnitsPurposes) {

            MeasureUnitsPurposes measureUnitsPurposes = savedMeasureUnitsPurposes.stream()
                .filter(mup -> mup.getId().equals(listOfNewMeasureUnitsPurpose.getId()))
                .findFirst().get();

            listOfNewMeasureUnitsPurpose.getFilledMeasureUnits().addAll(measureUnitsPurposes.getFilledMeasureUnits());
        }
        savedSetOfSentPurposes.setMeasureUnitsPurposes(newMeasureUnitsPurposes);
    }

    private void addNewFilledRisksToSetOfSentPurposes(SetOfSentPurposes savedSetOfSentPurposes, SetOfSentPurposes newSetOfSentPurposes){
        Set<RisksPurposes> savedRisksPurposes = savedSetOfSentPurposes.getRisksPurposes();
        Set<RisksPurposes> newRisksPurposes = newSetOfSentPurposes.getRisksPurposes();

        for (RisksPurposes listOfNewRisksPurpose : newRisksPurposes) {

            RisksPurposes risksPurposes = savedRisksPurposes.stream()
                .filter(rp -> rp.getId().equals(listOfNewRisksPurpose.getId()))
                .findFirst().get();

            listOfNewRisksPurpose.getFilledRisks().addAll(risksPurposes.getFilledRisks());
        }
        savedSetOfSentPurposes.setRisksPurposes(newRisksPurposes);
    }

    private void addNewFilledKeyRiskIndicatorsToSetOfSentPurposes(SetOfSentPurposes savedSetOfSentPurposes, SetOfSentPurposes newSetOfSentPurposes){
        Set<KeyRiskIndicatorPurposes> savedKeyRiskIndicatorsPurposes = savedSetOfSentPurposes.getKeyRiskIndicatorPurposes();
        Set<KeyRiskIndicatorPurposes> newKeyRiskIndicatorsPurposes = newSetOfSentPurposes.getKeyRiskIndicatorPurposes();

        for (KeyRiskIndicatorPurposes listOfNewRisksPurpose : newKeyRiskIndicatorsPurposes) {

            KeyRiskIndicatorPurposes keyRiskIndicatorPurposes = savedKeyRiskIndicatorsPurposes.stream()
                .filter(rp -> rp.getId().equals(listOfNewRisksPurpose.getId()))
                .findFirst().get();

            listOfNewRisksPurpose.getFilledKeyRiskIndicators().addAll(keyRiskIndicatorPurposes.getFilledKeyRiskIndicators());
        }
        savedSetOfSentPurposes.setKeyRiskIndicatorPurposes(newKeyRiskIndicatorsPurposes);
    }

    private void addNewFilledCommercialRisksToSetOfSentPurposes(SetOfSentPurposes savedSetOfSentPurposes, SetOfSentPurposes newSetOfSentPurposes) {
        CommercialRisksPurposes savedCommercialRisksPurposes = savedSetOfSentPurposes.getCommercialRisksPurposes();
        CommercialRisksPurposes newCommercialRisksPurposes = newSetOfSentPurposes.getCommercialRisksPurposes();

        if(newCommercialRisksPurposes != null && savedCommercialRisksPurposes != null) {
            savedCommercialRisksPurposes.getFilledCommercialRisks().
                addAll(newCommercialRisksPurposes.getFilledCommercialRisks());
        }
    }

    private void setUserToFilledFields(SetOfSentPurposes setOfSentPurposes){
        User user = userService.getCurrentUser();
        setUserToFilledMeasureUnits(setOfSentPurposes, user);
        setUserToFilledRisks(setOfSentPurposes, user);
        setUserToFilledCommercialRisks(setOfSentPurposes, user);
        setUserToFilledKeyRiskIndicators(setOfSentPurposes, user);
    }

    private void setUserToFilledMeasureUnits(SetOfSentPurposes setOfSentPurposes, User user){
        setOfSentPurposes.getFilledMeasureUnits().forEach(item -> item.setUser(user));
    }

    private void setUserToFilledRisks(SetOfSentPurposes setOfSentPurposes, User user){
        setOfSentPurposes.getFilledRisks().forEach(item -> item.setUser(user));
    }

    private void setUserToFilledKeyRiskIndicators(SetOfSentPurposes setOfSentPurposes, User user){
        setOfSentPurposes.getFilledKeyRiskIndicators().forEach(item -> item.setUser(user));
    }

    private void setUserToFilledCommercialRisks(SetOfSentPurposes setOfSentPurposes, User user){
        setOfSentPurposes.getFilledCommercialRisks().forEach(item -> item.setUser(user));
    }

}
