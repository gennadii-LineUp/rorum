package rog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rog.domain.*;
import rog.repository.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing SetOfSentPurposes.
 */
@Service
public class SetOfSentPurposesService {

    private final Logger log = LoggerFactory.getLogger(SetOfSentPurposesService.class);

    @Autowired
    private SetOfSentPurposesRepository setOfSentPurposesRepository;

    @Autowired
    private OrganisationStructureRepository organisationStructureRepository;

    @Autowired
    private MeasureUnitsPurposesRepository measureUnitsPurposesRepository;

    @Autowired
    private RisksPurposesRepository risksPurposesRepository;

    @Autowired
    private CommercialRisksPurposesRepository commercialRisksPurposesRepository;

    @Autowired
    private FilledCommercialRisksRepository filledCommercialRisksRepository;

    @Autowired
    private FilledRisksRepository filledRisksRepository;

    @Autowired
    private KeyRiskIndicatorPurposesRepository keyRiskIndicatorPurposesRepository;

    @Transactional
    public SetOfSentPurposes create(SetOfSentPurposes setOfSentPurpose){

        setOfSentPurposesRepository.save(setOfSentPurpose);

        setOfSentPurpose.getGlossaryOfPurposes().forEach(glossaryOfPurposes -> {
            saveMeasureUnitsPurpose(setOfSentPurpose, glossaryOfPurposes);
            saveRisksPurpose(setOfSentPurpose, glossaryOfPurposes);
            saveKeyRiskIndicatorPurpose(setOfSentPurpose, glossaryOfPurposes);
        });

        saveCommercialRisksPurpose(setOfSentPurpose);

        return setOfSentPurpose;
    }

    @Transactional
    public SetOfSentPurposes update(SetOfSentPurposes setOfSentPurpose){

        Set<FilledCommercialRisks> filledCommercialRisks = extractHighCommercialRisks(setOfSentPurpose);
        Set<FilledRisks> filledRisks = extractHighRisks(setOfSentPurpose);

        SetOfSentPurposes setOfSentPurposes = setOfSentPurposesRepository.save(setOfSentPurpose);

        setUnsavedHighRisksToFilledRisks(setOfSentPurposes, filledRisks);
        setUnsavedHighCommercialRisksToFilledCommercialRisks(setOfSentPurposes, filledCommercialRisks);

        saveAllHighRisks(setOfSentPurposes);

        return setOfSentPurpose;
    }

    /**
     *  Get all the setOfSentPurposes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SetOfSentPurposes> findAll(Pageable pageable) {
        log.debug("Request to get all SetOfSentPurposes");
        return setOfSentPurposesRepository.findAll(pageable);
    }

    /**
     *  Get one setOfSentPurposes by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public SetOfSentPurposes findOne(Long id) {
        log.debug("Request to get SetOfSentPurposes : {}", id);
        return setOfSentPurposesRepository.findOneWithEagerRelationships(id);
    }

    /**
     *  Delete the  setOfSentPurposes by id.
     *
     *  @param id the id of the entity
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete SetOfSentPurposes : {}", id);
        setOfSentPurposesRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public List<SetOfSentPurposes> getAllByOrganisationOfAdminIdAndOrderId(Long organisationId, Long orderId){
        List<Long> organisationIds = getListIdsOfPossibleSenders(organisationId);

        if(organisationIds.isEmpty()){
            return Collections.emptyList();
        }

        return setOfSentPurposesRepository.getAllByOrganisationIdsAndOrderId(organisationIds, orderId);
    }

    private List<Long> getListIdsOfPossibleSenders(Long organisationId) {
        return organisationStructureRepository.getListOfPossibleSendersOrganisationId(organisationId)
            .stream()
            .map(OrganisationStructure::getId)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SetOfSentPurposes> getAllByParentedOrganisationsAndOrderId(Long organisationId, Long orderId){
        List<Long> idsOfParentedOrganisations = getListIdsOfParentedOrganisations(organisationId);

        if(idsOfParentedOrganisations.isEmpty()){
            return Collections.emptyList();
        }

        return setOfSentPurposesRepository.getAllByOrganisationIdsAndOrderId(idsOfParentedOrganisations, orderId);
    }

    private List<Long> getListIdsOfParentedOrganisations(Long organisationId) {
        return organisationStructureRepository.getListOfParentedOrganisations(organisationId)
            .stream()
            .map(OrganisationStructure::getId)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SetOfSentPurposes> getAllBySupervisingOrganisationsAndOrderId(Long organisationId, Long orderId){
        List<Long> idsOfSupervisingOrganisations = getListIdsOfSupervisingOrganisations(organisationId);

        if(idsOfSupervisingOrganisations.isEmpty()){
            return Collections.emptyList();
        }

        return setOfSentPurposesRepository.getAllByOrganisationIdsAndOrderId(idsOfSupervisingOrganisations, orderId);
    }

    private List<Long> getListIdsOfSupervisingOrganisations(Long organisationId){
        return organisationStructureRepository.getListOfSupervisingOrganisations(organisationId)
            .stream()
            .map(OrganisationStructure::getId)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SetOfSentPurposes getOneByOrganisationIdAndOrderId(Long organisationId, Long orderId){
        return setOfSentPurposesRepository.getOneSavedByOrganisationAndOrderIds(organisationId, orderId)
            .orElse(setOfSentPurposesRepository.getOneUnsavedByOrganisationAndOrderIds(organisationId, orderId));
    }

    @Transactional(readOnly = true)
    public boolean isExistMeasureUnits(Long setOfSentPurposesId){
        return setOfSentPurposesRepository.isExistMeasureUnits(setOfSentPurposesId);
    }

    @Transactional(readOnly = true)
    public boolean isExistRisks(Long setOfSentPurposesId){
        return setOfSentPurposesRepository.isExistRisks(setOfSentPurposesId);
    }

    @Transactional(readOnly = true)
    public boolean isExistCommercialRisks(Long setOfSentPurposesId){
        return setOfSentPurposesRepository.isExistCommercialRisks(setOfSentPurposesId);
    }

    @Transactional(readOnly = true)
    public boolean isExistRiskKeyIndicator(Long setOfSentPurposesId){
        return setOfSentPurposesRepository.isExistRiskKeyIndicator(setOfSentPurposesId);
    }

    private void saveMeasureUnitsPurpose(SetOfSentPurposes setOfSentPurposes, GlossaryOfPurposes glossaryOfPurposes){
        MeasureUnitsPurposes measureUnitsPurposes = new MeasureUnitsPurposes();
        measureUnitsPurposes.setGlossaryOfPurposes(glossaryOfPurposes);
        measureUnitsPurposes.setSetOfSentPurposes(setOfSentPurposes);
        measureUnitsPurposesRepository.save(measureUnitsPurposes);
    }

    private void saveRisksPurpose(SetOfSentPurposes setOfSentPurposes, GlossaryOfPurposes glossaryOfPurposes){
        RisksPurposes risksPurposes = new RisksPurposes();
        risksPurposes.setGlossaryOfPurposes(glossaryOfPurposes);
        risksPurposes.setSetOfSentPurposes(setOfSentPurposes);
        risksPurposesRepository.save(risksPurposes);
    }

    private void saveKeyRiskIndicatorPurpose(SetOfSentPurposes setOfSentPurposes, GlossaryOfPurposes glossaryOfPurposes){
        KeyRiskIndicatorPurposes keyRiskIndicatorPurposes = new KeyRiskIndicatorPurposes();
        keyRiskIndicatorPurposes.setGlossaryOfPurposes(glossaryOfPurposes);
        keyRiskIndicatorPurposes.setSetOfSentPurposes(setOfSentPurposes);
        keyRiskIndicatorPurposesRepository.save(keyRiskIndicatorPurposes);
    }

    private void saveCommercialRisksPurpose(SetOfSentPurposes setOfSentPurposes){
        CommercialRisksPurposes commercialRisksPurposes = new CommercialRisksPurposes();
        commercialRisksPurposes.setSetOfSentPurposes(setOfSentPurposes);
        commercialRisksPurposesRepository.save(commercialRisksPurposes);
    }

    private Set<FilledRisks> extractHighRisks(SetOfSentPurposes setOfSentPurposes){
        Set<FilledRisks> filledRisks = new HashSet<>();

        setOfSentPurposes.getFilledRisks()
            .forEach(fr -> {
                filledRisks.add((FilledRisks) fr.clone());
                fr.setHighRisk(null);
            });
        return filledRisks;
    }

    private Set<FilledCommercialRisks> extractHighCommercialRisks(SetOfSentPurposes setOfSentPurposes){
        Set<FilledCommercialRisks> filledCommercialRisks = new HashSet<>();

        setOfSentPurposes.getFilledCommercialRisks()
            .forEach(fcr -> {
                filledCommercialRisks.add((FilledCommercialRisks) fcr.clone());
                fcr.setHighCommercialRisk(null);
            });
        return filledCommercialRisks;
    }

    private void setUnsavedHighRisksToFilledRisks(SetOfSentPurposes setOfSentPurposes, Set<FilledRisks> filledRisks){
        for (FilledRisks fr: filledRisks) {
            Optional<FilledRisks> filledRisks1 =
                setOfSentPurposes.getFilledRisks()
                    .stream()
                    .filter(f -> f.equals(fr))
                    .findFirst();

            filledRisks1.ifPresent(filledRisks2 -> filledRisks2.setHighRisk(fr.getHighRisk()));
        }
    }

    private void setUnsavedHighCommercialRisksToFilledCommercialRisks(SetOfSentPurposes setOfSentPurposes, Set<FilledCommercialRisks> filledCommercialRisks){
        for (FilledCommercialRisks fcr: filledCommercialRisks) {
            Optional<FilledCommercialRisks> filledCommercialRisks1 =
                setOfSentPurposes.getFilledCommercialRisks()
                    .stream()
                    .filter(f -> f.equals(fcr))
                    .findFirst();

            filledCommercialRisks1.ifPresent(filledCommercialRisks2 ->
                filledCommercialRisks2.setHighCommercialRisk(fcr.getHighCommercialRisk()));
        }
    }

    private void saveAllHighRisks(SetOfSentPurposes setOfSentPurposes){
        saveHighRisks(setOfSentPurposes);
        saveHighCommercialRisks(setOfSentPurposes);
    }

    private void saveHighRisks(SetOfSentPurposes setOfSentPurposes){
        setOfSentPurposes.getFilledRisks()
            .stream()
            .filter(fr -> fr.getHighRisk() != null)
            .forEach(fr -> {
                fr.getHighRisk().setFilledRisks(fr);
                filledRisksRepository.save(fr);
            });
    }

    private void saveHighCommercialRisks(SetOfSentPurposes setOfSentPurposes){
        setOfSentPurposes.getFilledCommercialRisks()
            .stream()
            .filter(fcr -> fcr.getHighCommercialRisk() != null)
            .forEach(fcr -> {
                fcr.getHighCommercialRisk().setFilledCommercialRisks(fcr);
                filledCommercialRisksRepository.save(fcr);
            });
    }
}
