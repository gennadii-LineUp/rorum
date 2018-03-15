package rog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rog.domain.GlossaryOfControlMechanisms;
import rog.repository.GlossaryOfControlMechanismsRepository;

import java.util.List;

@Service
public class GlossaryOfControlMechanismsService {
    @Autowired
    private GlossaryOfControlMechanismsRepository glossaryOfControlMechanismsRepository;

    public List<GlossaryOfControlMechanisms> getAllControlMechanisms(){
        return glossaryOfControlMechanismsRepository.findAll();
    }
}
