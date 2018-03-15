package rog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rog.domain.GlossaryOfMeasureUnits;
import rog.domain.GlossaryOfProcesses;
import rog.domain.GlossaryOfPurposes;

import java.util.List;

@Service
public class GlossaryManagementService {

    @Autowired
    private GlossaryOfPurposesService glossaryOfPurposesService;

    @Autowired
    private GlossaryOfMeasureUnitsService glossaryOfMeasureUnitsService;

    @Autowired
    private GlossaryOfRisksService glossaryOfRisksService;

    @Autowired
    private GlossaryOfKeyRiskIndicatorsService glossaryOfKeyRiskIndicatorsService;

    @Autowired
    private GlossaryOfCommercialRisksService glossaryOfCommercialRisksService;

    @Autowired
    private GlossaryOfProcessesService glossaryOfProcessesService;


    public List<GlossaryOfPurposes> getAllPurposes(){
        return glossaryOfPurposesService.findAll();
    }
    public List<GlossaryOfMeasureUnits> getAllMeasureUnits(){
        return glossaryOfMeasureUnitsService.findAll();
    }
    public List<GlossaryOfProcesses> getAllProcesses(){
        return glossaryOfProcessesService.findAll();
    }

}
