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
import rog.domain.PowerOfInfluence;
import rog.security.AuthoritiesConstants;
import rog.service.PowerOfInfluenceService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PowerOfInfluenceResource {

    private final Logger log = LoggerFactory.getLogger(PowerOfInfluenceResource.class);

    @Autowired
    private PowerOfInfluenceService powerOfInfluenceService;

    @GetMapping("/power-of-influence")
    @Timed
    @Secured({AuthoritiesConstants.USER, AuthoritiesConstants.ADMIN})
    public ResponseEntity<List<PowerOfInfluence>> getAllPowerOfInfluence() {
        log.debug("REST request to get a list of PowerOfInfluence");
        List<PowerOfInfluence> list = powerOfInfluenceService.getAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/power-of-influence/{number}")
    @Timed
    @Secured({AuthoritiesConstants.USER, AuthoritiesConstants.ADMIN})
    public ResponseEntity<List<PowerOfInfluence>> getAllByNumber(@PathVariable("number") Integer number) {
        log.debug("REST request to get Probability by number: {}", number);

        List<PowerOfInfluence> list = powerOfInfluenceService.getAllByNumber(number);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(list));
    }

}
