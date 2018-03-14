package rog.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rog.domain.Probability;
import rog.security.AuthoritiesConstants;
import rog.service.ProbabilityService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProbabilityResource {

    private final Logger log = LoggerFactory.getLogger(ProbabilityResource.class);

    @Autowired
    private ProbabilityService probabilityService;

    @GetMapping("/probabilities")
    @Timed
    @Secured({AuthoritiesConstants.USER, AuthoritiesConstants.ADMIN})
    public ResponseEntity<List<Probability>> getAllProbabilities() {
        log.debug("REST request to get a list of Probability");
        List<Probability> list = probabilityService.getAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/probabilities/{number}")
    @Timed
    @Secured({AuthoritiesConstants.USER, AuthoritiesConstants.ADMIN})
    public ResponseEntity<List<Probability>> getAllByNumber(@PathVariable("number") Integer number) {
        log.debug("REST request to get Probability by number: {}", number);

        List<Probability> list = probabilityService.getAllByNumber(number);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(list));
    }

}
