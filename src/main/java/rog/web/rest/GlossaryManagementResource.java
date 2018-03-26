package rog.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rog.domain.*;
import rog.service.GlossaryManagementService;
import rog.service.Tree.TreeNode;

import java.util.List;


@RestController
@RequestMapping("/api")
public class GlossaryManagementResource {

    private final Logger log = LoggerFactory.getLogger(GlossaryManagementResource.class);

    @Autowired
    private GlossaryManagementService glossaryManagementService;

    @GetMapping("/glossary-management")
    @Timed
    public ResponseEntity<String> getString() {
        log.debug("REST request to get a page of Orders");

        return new ResponseEntity<>("Hello", HttpStatus.OK);
    }

    @GetMapping("/glossary-management/processes")
    @Timed
    public ResponseEntity<List<GlossaryOfProcesses>> getAllProcesses() {
        log.debug("REST request to get a page of Orders");

        return new ResponseEntity<>(glossaryManagementService.getAllProcesses() , HttpStatus.OK);
    }
    @GetMapping("/glossary-management/purposes")
    @Timed
    public ResponseEntity<List<GlossaryOfPurposes>> getAllPurposes() {
        log.debug("REST request to get a page of Orders");

        return new ResponseEntity<>(glossaryManagementService.getAllPurposes() , HttpStatus.OK);
    }
    @GetMapping("/glossary-management/measures")
    @Timed
    public ResponseEntity<List<GlossaryOfMeasureUnits>> getAllMeasures() {
        log.debug("REST request to get a page of Orders");

        return new ResponseEntity<>(glossaryManagementService.getAllMeasureUnits() , HttpStatus.OK);
    }
    @GetMapping("/glossary-management/measures/user")
    @Timed
    public ResponseEntity<List<GlossaryOfMeasureUnits>> getAllUserMeasures() {
        log.debug("REST request to get a page of Orders");

        return new ResponseEntity<>(glossaryManagementService.getAllUserMeasureUnits() , HttpStatus.OK);
    }
    @GetMapping("/glossary-management/purposes/user")
    @Timed
    public ResponseEntity<List<GlossaryOfPurposes>> getAllAssignmentToCellOfCurrentOrganisation() {
        log.debug("REST request to get purposes");

        return new ResponseEntity<>(glossaryManagementService.getAllAssignmentToCellOfCurrentOrganisation() , HttpStatus.OK);
    }


    @GetMapping("/glossary-management/purposes/tree")
    @Timed
    public ResponseEntity<String> getAllPurposesTree() {
        log.debug("REST request to get tree of purposes");
        TreeNode tree = glossaryManagementService.buildPurposesTree();
//        log.debug(glossaryManagementService.buildPurposesTree().)
        log.debug(tree.toString());
//        JsonTypeInfo.Id
        return new ResponseEntity<>("hello" , HttpStatus.OK);
    }

    @GetMapping("/glossary-management/commercial-risks")
    @Timed
    public ResponseEntity<List<GlossaryOfCommercialRisks>> getAllCommercialRisks() {
        log.debug("REST request to get all corruption risks");

        return new ResponseEntity<>(glossaryManagementService.getAllCommercialRisks() , HttpStatus.OK);
    }
    @GetMapping("/glossary-management/commercial-risks/user")
    @Timed
    public ResponseEntity<List<GlossaryOfCommercialRisks>> getAllUserCommercialRisks() {
        log.debug("REST request to get all corruption risks for certain user");

        return new ResponseEntity<>(glossaryManagementService.getAllUserCommercialRisks() , HttpStatus.OK);
    }

    @GetMapping("/glossary-management/KRI")
    @Timed
    public ResponseEntity<List<GlossaryOfKeyRiskIndicators>> getAllKRI() {
        log.debug("REST request to get all KRIs");

        return new ResponseEntity<>(glossaryManagementService.getAllKRI() , HttpStatus.OK);
    }

    @GetMapping("/glossary-management/KRI/user")
    @Timed
    public ResponseEntity<List<GlossaryOfKeyRiskIndicators>> getAllUserKRI() {
        log.debug("REST request to get all KRIs");

        return new ResponseEntity<>(glossaryManagementService.getAllUserKRI() , HttpStatus.OK);
    }

    @GetMapping("/glossary-management/risks")
    @Timed
    public ResponseEntity<List<GlossaryOfRisks>> getAllRisks() {
        log.debug("REST request to get all KRIs");

        return new ResponseEntity<>(glossaryManagementService.getAllRisks() , HttpStatus.OK);
    }
    @GetMapping("/glossary-management/risks/user")
    @Timed
    public ResponseEntity<List<GlossaryOfRisks>> getAllUserRisks() {
        log.debug("REST request to get all KRIs");

        return new ResponseEntity<>(glossaryManagementService.getAllUserRisks() , HttpStatus.OK);
    }

    @GetMapping("/glossary-management/risks/current")
    @Timed
    public ResponseEntity<List<GlossaryOfRisks>> getAllCurrentRisks() {
        log.debug("REST request to get all KRIs");

        return new ResponseEntity<>(glossaryManagementService.getAllCurrentRisks() , HttpStatus.OK);
    }

    @GetMapping("/glossary-management/control-mechanisms")
    @Timed
    public ResponseEntity<List<GlossaryOfControlMechanisms>> getAllControlMechanisms() {
        log.debug("REST request to get all KRIs");

        return new ResponseEntity<>(glossaryManagementService.getAllControlMechanisms() , HttpStatus.OK);
    }
}
