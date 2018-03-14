package rog.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rog.domain.PercentagesOfCalculatedValues;
import rog.repository.PercentagesOfCalculatedValuesRepository;
import rog.security.AuthoritiesConstants;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PercentagesOfCalculatedValuesResource {

    private final Logger log = LoggerFactory.getLogger(PercentagesOfCalculatedValuesResource.class);

    @Autowired
    private PercentagesOfCalculatedValuesRepository percentagesOfCalculatedValuesRepository;

    @GetMapping("/percentages-of-calculated-values")
    @Timed
    @Secured({AuthoritiesConstants.USER, AuthoritiesConstants.ADMIN})
    public ResponseEntity<List<PercentagesOfCalculatedValues>> getAllPercentagesOfCalculatedValues() {
        log.debug("REST request to get a list of PercentagesOfCalculatedValues");
        List<PercentagesOfCalculatedValues> list = percentagesOfCalculatedValuesRepository.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
