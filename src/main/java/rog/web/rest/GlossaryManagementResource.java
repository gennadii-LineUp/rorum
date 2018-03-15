package rog.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rog.domain.GlossaryOfMeasureUnits;
import rog.domain.GlossaryOfProcesses;
import rog.domain.GlossaryOfPurposes;
import rog.service.GlossaryManagementService;

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

}
