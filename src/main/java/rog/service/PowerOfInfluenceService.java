package rog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rog.domain.PowerOfInfluence;
import rog.repository.PowerOfInfluenceRepository;

import java.util.List;

@Service
public class PowerOfInfluenceService {

    @Autowired
    private PowerOfInfluenceRepository powerOfInfluenceRepository;

    @Transactional(readOnly = true)
    public Integer getMaxValue(){
        return powerOfInfluenceRepository.getMaxValue();
    }

    @Transactional(readOnly = true)
    public List<PowerOfInfluence> getAllByNumber(Integer number){
        return powerOfInfluenceRepository.findAllByNumber(number);
    }

    @Transactional(readOnly = true)
    public List<PowerOfInfluence> getAll(){
        return powerOfInfluenceRepository.findAll();
    }

}
