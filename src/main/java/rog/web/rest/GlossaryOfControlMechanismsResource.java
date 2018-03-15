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
import rog.domain.GlossaryOfControlMechanisms;
import rog.service.GlossaryOfControlMechanismsService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GlossaryOfControlMechanismsResource {

    private final Logger log = LoggerFactory.getLogger(GlossaryManagementResource.class);

    @Autowired
    private GlossaryOfControlMechanismsService glossaryOfControlMechanismsService;

    @GetMapping("/glossary-of-control-mechanisms")
    @Timed
    public ResponseEntity<List<GlossaryOfControlMechanisms>> getAllControlMechanisms() {
        log.debug("REST request to get all Control Mechanisms");

        return new ResponseEntity<>(glossaryOfControlMechanismsService.getAllControlMechanisms() , HttpStatus.OK);
    }

}


