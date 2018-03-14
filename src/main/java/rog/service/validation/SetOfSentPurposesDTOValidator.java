package rog.service.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import rog.domain.enumeration.StatusOfSending;
import rog.security.AuthoritiesConstants;
import rog.security.SecurityUtils;
import rog.service.PowerOfInfluenceService;
import rog.service.ProbabilityService;
import rog.service.SetOfSentPurposesService;
import rog.service.dto.*;
import rog.service.mapper.SetOfSentPurposesMapper;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Component
public class SetOfSentPurposesDTOValidator implements Validator {

    private final Logger log = LoggerFactory.getLogger(SetOfSentPurposesDTOValidator.class);

    @Autowired
    private SetOfSentPurposesService setOfSentPurposeService;

    @Autowired
    @Qualifier("filledMeasureUnitsDTOValidator")
    private Validator filledMeasureUnitsDTOValidator;

    @Autowired
    @Qualifier("filledRisksDTOValidator")
    private Validator filledRisksDTOValidator;

    @Autowired
    @Qualifier("filledCommercialRisksDTOValidator")
    private Validator filledCommercialRisksDTOValidator;

    @Autowired
    @Qualifier("highRiskDTOValidator")
    private Validator highRiskDTOValidator;

    @Autowired
    @Qualifier("filledKeyRiskIndicatorDTOValidator")
    private Validator filledKeyRiskIndicatorDTOValidator;

    @Autowired
    private SetOfSentPurposesMapper setOfSentPurposesMapper;

    @Autowired
    private ProbabilityService probabilityService;

    @Autowired
    private PowerOfInfluenceService powerOfInfluenceService;

    @Override
    public void validate(Object o) {
        log.debug("Validation of SetOfSentPurposesDTO");

        SetOfSentPurposesDTO setOfSentPurposesDTO = (SetOfSentPurposesDTO) o;

        if(Objects.isNull(setOfSentPurposesDTO)){
            return;
        }

        Map<String, String> errors = setOfSentPurposesDTO.getErrors();

        StatusOfSending statusOfSending = setOfSentPurposesDTO.getStatusOfSending();

        if(statusOfSending.isConfirmedPurposes() && setOfSentPurposesDTO.getIds().isEmpty()){
            errors.put("purposes", "purposes.required");
        }

        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            statusOfSending.isRejectedPlan() &&
            Objects.isNull(setOfSentPurposesDTO.getNotation())){
            errors.put("notation", "notation.required");
        }
    }

    public boolean isValidSetOfSentPurposes(SetOfSentPurposesDTO setOfSentPurposesDTO){
        validate(setOfSentPurposesDTO);
        return Objects.isNull(setOfSentPurposesDTO.getErrors()) || setOfSentPurposesDTO.getErrors().isEmpty();
    }

    public boolean isValidWithFields(SetOfSentPurposesDTO setOfSentPurposesDTO){
        return !(isValidSetOfSentPurposes(setOfSentPurposesDTO) &&
            isValidFilledFields(setOfSentPurposesDTO) &&
            isValidHighRisks(setOfSentPurposesDTO));

    }

    private boolean isValidFilledFields(SetOfSentPurposesDTO setOfSentPurposesDTO){
        if(!setOfSentPurposesDTO.getStatusOfSending().isUncheckedPlan()){
            return true;
        }

        boolean isValidFilledMeasureUnits = isValidFilledMeasureUnits(setOfSentPurposesDTO);
        boolean isValidFilledRisks = isValidFilledRisks(setOfSentPurposesDTO);
        boolean isValidFilledCommercialRisks = isValidFilledCommercialRisks(setOfSentPurposesDTO);
        //boolean isValidFilledKeyRiskIndicator = isValidFilledKeyRiskIndicator(setOfSentPurposesDTO);
        boolean isValidFilledKeyRiskIndicator = true;

        return isValidFilledMeasureUnits && isValidFilledRisks &&
            isValidFilledCommercialRisks && isValidFilledKeyRiskIndicator;
    }

    private boolean isValidFilledMeasureUnits(SetOfSentPurposesDTO setOfSentPurposesDTO){
        Set<FilledMeasureUnitsDTO> filledMeasureUnitsDTOs =
            setOfSentPurposesMapper.getFilledMeasureUnitsDTOs(setOfSentPurposesDTO);

        if(areFilledMeasureUnitsEmpty(setOfSentPurposesDTO, filledMeasureUnitsDTOs)){
            return false;
        }

        filledMeasureUnitsDTOs.forEach(fmuDTO -> filledMeasureUnitsDTOValidator.validate(fmuDTO));
        return this.areExistErrorsInFilledMeasureUnitDTOs(filledMeasureUnitsDTOs);
    }

    private boolean areExistErrorsInFilledMeasureUnitDTOs(Set<FilledMeasureUnitsDTO> filledMeasureUnitsDTOs){
        return filledMeasureUnitsDTOs
            .stream()
            .map(FilledMeasureUnitsDTO::getErrors)
            .allMatch(Map::isEmpty);
    }

    private boolean areFilledMeasureUnitsEmpty(SetOfSentPurposesDTO setOfSentPurposesDTO, Set<FilledMeasureUnitsDTO> filledMeasureUnitsDTOs){
        Long setOfSentPurposesId = setOfSentPurposesDTO.getId();
        return setOfSentPurposeService.isExistMeasureUnits(setOfSentPurposesId) && filledMeasureUnitsDTOs.isEmpty();
    }

    private boolean isValidFilledRisks(SetOfSentPurposesDTO setOfSentPurposesDTO){
        Set<FilledRisksDTO> filledRisksDTOs = setOfSentPurposesMapper.getFilledRisksDTOs(setOfSentPurposesDTO);

        if(areFilledRisksEmpty(setOfSentPurposesDTO, filledRisksDTOs)){
            return false;
        }

        filledRisksDTOs.forEach(frDTO -> filledRisksDTOValidator.validate(frDTO));
        return areExistErrorsInFilledRiskDTOs(filledRisksDTOs);
    }

    private boolean areExistErrorsInFilledRiskDTOs(Set<FilledRisksDTO> filledRisksDTOs){
        return filledRisksDTOs
            .stream()
            .map(FilledRisksDTO::getErrors)
            .allMatch(Map::isEmpty);
    }

    private boolean areFilledRisksEmpty(SetOfSentPurposesDTO setOfSentPurposesDTO, Set<FilledRisksDTO> filledRisksDTOs){
        Long setOfSentPurposesId = setOfSentPurposesDTO.getId();
        return setOfSentPurposeService.isExistRisks(setOfSentPurposesId) && filledRisksDTOs.isEmpty();
    }

    private boolean isValidFilledKeyRiskIndicator(SetOfSentPurposesDTO setOfSentPurposesDTO){
        Set<FilledKeyRiskIndicatorDTO> filledKeyRiskIndicatorDTOs =
            setOfSentPurposesMapper.getFilledKeyRiskIndicatorDTOs(setOfSentPurposesDTO);

        if(areFilledKeyRiskIndicatorsEmpty(setOfSentPurposesDTO, filledKeyRiskIndicatorDTOs)){
            return false;
        }

        filledKeyRiskIndicatorDTOs.forEach(frDTO -> filledKeyRiskIndicatorDTOValidator.validate(frDTO));
        return areExistErrorsInFilledKeyRiskIndicatorDTO(filledKeyRiskIndicatorDTOs);
    }

    private boolean areExistErrorsInFilledKeyRiskIndicatorDTO(Set<FilledKeyRiskIndicatorDTO> filledKeyRiskIndicatorDTOs){
        return filledKeyRiskIndicatorDTOs
            .stream()
            .map(FilledKeyRiskIndicatorDTO::getErrors)
            .allMatch(Map::isEmpty);
    }

    private boolean areFilledKeyRiskIndicatorsEmpty(SetOfSentPurposesDTO setOfSentPurposesDTO, Set<FilledKeyRiskIndicatorDTO> filledKeyRiskIndicatorDTOs){
        Long setOfSentPurposesId = setOfSentPurposesDTO.getId();
        return setOfSentPurposeService.isExistRiskKeyIndicator(setOfSentPurposesId) && filledKeyRiskIndicatorDTOs.isEmpty();
    }

    private boolean isValidFilledCommercialRisks(SetOfSentPurposesDTO setOfSentPurposesDTO){
        Set<FilledCommercialRisksDTO> filledCommercialRisksDTOs =
            setOfSentPurposesMapper.getFilledCommercialRiskDTOs(setOfSentPurposesDTO);

        if(areFilledCommercialRisksEmpty(setOfSentPurposesDTO, filledCommercialRisksDTOs)){
            return false;
        }

        filledCommercialRisksDTOs.forEach(fcrDTO -> filledCommercialRisksDTOValidator.validate(fcrDTO));
        return areExistErrorsInFilledCommercialRisksDTOs(filledCommercialRisksDTOs);
    }

    private boolean areExistErrorsInFilledCommercialRisksDTOs(Set<FilledCommercialRisksDTO> filledCommercialRisksDTOs){
        return filledCommercialRisksDTOs
            .stream()
            .map(FilledCommercialRisksDTO::getErrors)
            .allMatch(Map::isEmpty);
    }

    private boolean areFilledCommercialRisksEmpty(SetOfSentPurposesDTO setOfSentPurposesDTO, Set<FilledCommercialRisksDTO> filledCommercialRisksDTOs){
        Long setOfSentPurposesId = setOfSentPurposesDTO.getId();
        return setOfSentPurposeService.isExistCommercialRisks(setOfSentPurposesId) && filledCommercialRisksDTOs.isEmpty();
    }

    private boolean isValidHighRisks(SetOfSentPurposesDTO setOfSentPurposesDTO) {
        if(!setOfSentPurposesDTO.getStatusOfSending().isUncheckedPlan()){
            return true;
        }

        Integer maxPossibleValue = probabilityService.getMaxValue() * powerOfInfluenceService.getMaxValue();

        boolean isValidHighRisks = isValidHighRisk(setOfSentPurposesDTO, maxPossibleValue);
        boolean isValidHighCommercialRisks = isValidHighCommercialRisk(setOfSentPurposesDTO, maxPossibleValue);
        return isValidHighRisks && isValidHighCommercialRisks;
    }

    private boolean isValidHighRisk(SetOfSentPurposesDTO setOfSentPurposesDTO, Integer maxPossibleValue){
        Set<FilledRisksDTO> filledRisksDTOs = setOfSentPurposesMapper.getFilledRisksDTOs(setOfSentPurposesDTO);

        validateHighRiskDTOs(filledRisksDTOs, maxPossibleValue);

        return areExistErrorsInHighRisk(filledRisksDTOs);
    }

    private boolean areExistErrorsInHighRisk(Set<FilledRisksDTO> filledRisksDTOs){
        return filledRisksDTOs.stream()
            .map(FilledRisksDTO::getHighRiskDTO)
            .filter(Objects::nonNull)
            .map(HighRiskDTO::getErrors)
            .allMatch(Map::isEmpty);
    }


    private void validateHighRiskDTOs(Set<FilledRisksDTO> filledRisksDTOS, Integer maxPossibleValue){
        filledRisksDTOS.stream()
            .filter(Objects::nonNull)
            .filter(v -> (v.getProbability() * v.getPowerOfInfluence() == maxPossibleValue))
            .forEach(fr -> highRiskDTOValidator.validate(fr.getHighRiskDTO()));
    }

    private boolean isValidHighCommercialRisk(SetOfSentPurposesDTO setOfSentPurposesDTO, Integer maxPossibleValue){
        Set<FilledCommercialRisksDTO> filledCommercialRisksDTOs =
            setOfSentPurposesMapper.getFilledCommercialRiskDTOs(setOfSentPurposesDTO);

        validateHighCommercialRiskDTOs(filledCommercialRisksDTOs, maxPossibleValue);

        return areExistErrorsInHighCommercialRisk(filledCommercialRisksDTOs);
    }

    private boolean areExistErrorsInHighCommercialRisk(Set<FilledCommercialRisksDTO> filledCommercialRisksDTOs){
        return filledCommercialRisksDTOs.stream()
            .map(FilledCommercialRisksDTO::getHighRiskDTO)
            .filter(Objects::nonNull)
            .map(HighRiskDTO::getErrors)
            .allMatch(Map::isEmpty);
    }

    private void validateHighCommercialRiskDTOs(Set<FilledCommercialRisksDTO> filledCommercialRisksDTOS, Integer maxPossibleValue){
        filledCommercialRisksDTOS.stream()
            .filter(Objects::nonNull)
            .filter(v -> (v.getProbability() * v.getPowerOfInfluence() == maxPossibleValue))
            .forEach(fcr -> highRiskDTOValidator.validate(fcr.getHighRiskDTO()));
    }

}
