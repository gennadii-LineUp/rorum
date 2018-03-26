package rog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rog.domain.*;
import rog.service.Tree.TreeNode;

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

    @Autowired
    private GlossaryOfControlMechanismsService glossaryOfControlMechanismsService;


    private final Logger log = LoggerFactory.getLogger(GlossaryManagementService.class);

    //#region "All Data from glossaries"
    public List<GlossaryOfPurposes> getAllPurposes(){
        return glossaryOfPurposesService.findAll();
    }
    public List<GlossaryOfMeasureUnits> getAllMeasureUnits(){
        return glossaryOfMeasureUnitsService.findAll();
    }
    public List<GlossaryOfProcesses> getAllProcesses() { return glossaryOfProcessesService.findAll();}
    public List<GlossaryOfKeyRiskIndicators> getAllKRI() {return glossaryOfKeyRiskIndicatorsService.findAll();}
    public List<GlossaryOfCommercialRisks> getAllCommercialRisks() {return glossaryOfCommercialRisksService.findAll(); }
    public List<GlossaryOfRisks> getAllRisks() { return glossaryOfRisksService.findAll(); }
    public List<GlossaryOfControlMechanisms> getAllControlMechanisms() {return glossaryOfControlMechanismsService.getAllControlMechanisms();}
    //#endregion "All Data from glossaries"

    //#region "All current glossaries"
    public List<GlossaryOfProcesses> getAllCurrentProcesses(){ return glossaryOfProcessesService.findAllByImportantToIsGreaterThan(); }
    public List<GlossaryOfRisks> getAllCurrentRisks() {return glossaryOfRisksService.findAllByImportantToIsGreaterThan();}
    //#endregion "All current glossaries"

    public List<GlossaryOfPurposes> getAllAssignmentToCellOfCurrentOrganisation() { return glossaryOfPurposesService.getAllAssignmentToCellOfCurrentOrganisation(); }
    public List<GlossaryOfCommercialRisks> getAllUserCommercialRisks() { return glossaryOfCommercialRisksService.getAllOfCurrentOrganisation(); }
    public List<GlossaryOfKeyRiskIndicators> getAllUserKRI() { return glossaryOfKeyRiskIndicatorsService.getAllUserKRI(); }
    public List<GlossaryOfRisks> getAllUserRisks() { return glossaryOfRisksService.getAllUserRisks(); }
    public List<GlossaryOfMeasureUnits> getAllUserMeasureUnits(){
        return glossaryOfMeasureUnitsService.findAllUserMeasureUnits();
    }
    public TreeNode<String> buildPurposesTree() {

        List<GlossaryOfPurposes> firstLevel = glossaryOfPurposesService.findAllByNullParentId();
//        log.debug(firstLevel.toString());
        TreeNode<String> root = new TreeNode<String>("root");
        {
            for (GlossaryOfPurposes gop: firstLevel) {
                TreeNode<String> nodeg = root.addChild(gop.getName());
            }
            TreeNode<String> node0 = root.addChild("node0");
            TreeNode<String> node1 = root.addChild("node1");
            TreeNode<String> node2 = root.addChild("node2");
            {
                TreeNode<String> node20 = node2.addChild(null);
                TreeNode<String> node21 = node2.addChild("node21");
                {
                    TreeNode<String> node210 = node20.addChild("node210");
                }
            }
        }
//        log.debug(root);
//        log.debug(root);
        return root;

//
    }
}
