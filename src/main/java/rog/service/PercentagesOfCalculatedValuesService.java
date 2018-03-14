package rog.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import rog.domain.PercentagesOfCalculatedValues;
import rog.repository.PercentagesOfCalculatedValuesRepository;

@Service
public class PercentagesOfCalculatedValuesService {

    private final Logger log = LoggerFactory.getLogger(PercentagesOfCalculatedValuesService.class);
    @Autowired
    private PercentagesOfCalculatedValuesRepository percentagesOfCalculatedValuesRepository;



    @Transactional(readOnly = true)
    public List<PercentagesOfCalculatedValues> getAllPercentagesOfCalculatedValues() {
        log.debug("REST request to get a list of PercentagesOfCalculatedValues");
        return percentagesOfCalculatedValuesRepository.findAll();
    }
}
