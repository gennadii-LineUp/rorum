package rog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rog.domain.Probability;
import rog.repository.ProbabilityRepository;

import java.util.List;

@Service
public class ProbabilityService {

    @Autowired
    private ProbabilityRepository probabilityRepository;

    @Transactional(readOnly = true)
    public Integer getMaxValue(){
        return probabilityRepository.getMaxValue();
    }

    @Transactional(readOnly = true)
    public List<Probability> getAllByNumber(Integer number){
        return probabilityRepository.findAllByNumber(number);
    }

    @Transactional(readOnly = true)
    public List<Probability> getAll(){
        return probabilityRepository.findAll();
    }

}
