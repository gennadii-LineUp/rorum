package rog.web.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import com.codahale.metrics.annotation.Timed;

import net.sf.dynamicreports.report.exception.DRException;
import rog.domain.GlossaryOfProcesses;
import rog.domain.Orders;
import rog.domain.OrganisationStructure;
import rog.domain.User;
import rog.service.GlossaryOfProcessesService;
import rog.service.OrdersService;
import rog.service.OrganisationStructureService;
import rog.service.ReportService;
import rog.service.UserService;

@RestController
@RequestMapping("/api")
public class ReportResource {

    private final Logger log = LoggerFactory.getLogger(ReportResource.class);

    @Autowired
    private OrdersService ordersService;
    
    @Autowired
    private UserService userService;

    @Autowired
    private ReportService reportService;
    
    @Autowired
    private OrganisationStructureService organisationStructureService; 
    
    @Autowired
    private GlossaryOfProcessesService glossaryOfProcessesService; 

    @GetMapping("/reporting/{orderId}/excel/purpose-accomplishement/{userId}")
    @Timed
    public ResponseEntity<byte[]> getPurposeAccomplishmentReport(@PathVariable Long orderId) throws IOException, DRException {
        log.debug("REST request to get PurposeAccomplishmentReport by orderId : {}", orderId);

        Long userId = userService.getCurrentUser().getId();
        return new ResponseEntity<>(reportService.generatePurposeAccomplishmentReport(orderId, userId), HttpStatus.OK);
    }
    
    @GetMapping("/reporting")
    @Timed
    public ResponseEntity<List<Orders>> getAllOrders() {
        log.debug("REST request to get a page of Orders");
        
        return new ResponseEntity<>(ordersService.findAll(), HttpStatus.OK);
    }
   
    @GetMapping("/reporting/{orderId}")
    @Timed
    public ResponseEntity<Orders> getOrder(@PathVariable Long orderId) {
        log.debug("REST request to get a page of Orders");
        log.debug("order: " + ordersService.findOne(orderId));
        return new ResponseEntity<>(ordersService.findOne(orderId), HttpStatus.OK);
    }
    
    @GetMapping("/reporting/{orderId}/processes")
    @Timed
    public ResponseEntity<List<GlossaryOfProcesses>> getAllProcesses() {
        log.debug("REST request to get all processes");
        return new ResponseEntity<>(glossaryOfProcessesService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/reporting/organisationStructures")
    @Timed
    public ResponseEntity<List<OrganisationStructure>> getAllParentedOrSupervisoredOrganisationStructures() {
        log.debug("REST request to get all processes");
        return new ResponseEntity<>(organisationStructureService.getAllParentedOrSupervisoredOrganisationStructures(), HttpStatus.OK);
    }
    
    @GetMapping("/reporting/{orderId}/excel/risks-register-raport/")
    @Timed
    public byte[] getSetOfSentPurposes(@PathVariable Long orderId) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, DRException {
        return reportService.generateRiskRegisterReport(orderId);
    }
    
    @GetMapping("/reporting/{orderId}/{organisationStructureId}/excel/risks-register-raport/")
    @Timed
    public byte[] generateRisksChangesReport(@PathVariable Long orderId, @PathVariable Long organisationStructureId) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, DRException {

        
        return reportService.generateRisksChangesReport(orderId, organisationStructureId);
    }
    
    @GetMapping("/reporting/{orderId}/excel/risks-report/")
    @Timed
    public byte[] generateRiskReport(@PathVariable Long orderId) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, DRException {
        log.debug("ReportResource + orderId: " + orderId);
    	return reportService.generateRiskReport(orderId);
    }
    
    @GetMapping("/reporting/{orderId}/excel/risks-register-admin/")
    @Timed
    public byte[] generateRiskRegisterAdmin(@PathVariable Long orderId) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, DRException {
        log.debug("ReportResource + orderId: " + orderId);
    	return reportService.generateRiskRegisterAdmin(orderId);
    }
    
    @GetMapping("/reporting/{orderId}/word/risks-report/")
    @Timed
    public byte[] generateRiskReportDoc(@PathVariable Long orderId) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, DRException {
        return reportService.generateRiskReportDoc(orderId);
    }
    
    @GetMapping("/reporting/{orderId}/{organisationStructureId}/excel/purpose-accomplishement/")
    @Timed
    public byte[] generatePurposeAccomplishmentReportAdmin(@PathVariable Long orderId, @PathVariable Long organisationStructureId) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, DRException {
    	return reportService.generatePurposeAccomplishmentReportAdmin(orderId, organisationStructureId);
    }
}
