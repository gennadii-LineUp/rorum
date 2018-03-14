package rog.service.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rog.service.PowerOfInfluenceService;
import rog.service.ProbabilityService;
import rog.service.dto.FilledCommercialRisksDTO;

import java.util.Map;
import java.util.Objects;

@Component
public class FilledCommercialRisksDTOValidator implements Validator {

    private final Logger log = LoggerFactory.getLogger(FilledCommercialRisksDTOValidator.class);

    @Autowired
    private ProbabilityService probabilityService;

    @Autowired
    private PowerOfInfluenceService powerOfInfluenceService;

    @Override
    public void validate(Object o) {
        log.debug("Validation of FilledCommercialRisksDTO");

        FilledCommercialRisksDTO filledCommercialRisksDTO = (FilledCommercialRisksDTO) o;

        if(Objects.isNull(filledCommercialRisksDTO)){
            return;
        }

        Map<String, String> errors = filledCommercialRisksDTO.getErrors();

        if(filledCommercialRisksDTO.getProbability() == null){
            errors.put("probability", "probability.required");
        } else if(filledCommercialRisksDTO.getProbability() > probabilityService.getMaxValue()){
            errors.put("probability", "probability.max");
        } else if(filledCommercialRisksDTO.getProbability() < 0){
            errors.put("probability", "probability.min");
        }

        if(filledCommercialRisksDTO.getPowerOfInfluence() == null){
            errors.put("powerOfInfluence", "powerOfInfluence.required");
        }else if(filledCommercialRisksDTO.getPowerOfInfluence() > powerOfInfluenceService.getMaxValue()){
            errors.put("powerOfInfluence", "powerOfInfluence.max");
        } else if(filledCommercialRisksDTO.getPowerOfInfluence() < 0){
            errors.put("powerOfInfluence", "powerOfInfluence.min");
        }

        if(filledCommercialRisksDTO.getStrengthOfControlFunctionProbability() == null){
            errors.put("strengthOfControlFunctionProbability", "strengthOfControlFunctionProbability.required");
        } else if(filledCommercialRisksDTO.getStrengthOfControlFunctionProbability() > probabilityService.getMaxValue()){
            errors.put("strengthOfControlFunctionProbability", "strengthOfControlFunctionProbability.max");
        } else if(filledCommercialRisksDTO.getStrengthOfControlFunctionProbability() < 1){
            errors.put("strengthOfControlFunctionProbability", "strengthOfControlFunctionProbability.min");
        }

        if(filledCommercialRisksDTO.getStrengthOfControlFunctionPowerOfInfluence() == null){
            errors.put("strengthOfControlFunctionPowerOfInfluence", "strengthOfControlFunctionPowerOfInfluence.required");
        }else if(filledCommercialRisksDTO.getStrengthOfControlFunctionPowerOfInfluence() > powerOfInfluenceService.getMaxValue()){
            errors.put("strengthOfControlFunctionPowerOfInfluence", "strengthOfControlFunctionPowerOfInfluence.max");
        } else if(filledCommercialRisksDTO.getStrengthOfControlFunctionPowerOfInfluence() < 1){
            errors.put("strengthOfControlFunctionPowerOfInfluence", "strengthOfControlFunctionPowerOfInfluence.min");
        }

        if(filledCommercialRisksDTO.getProbabilities() == null || filledCommercialRisksDTO.getProbabilities().isEmpty()){
            errors.put("probabilities", "probabilities.required");
        }

        if(filledCommercialRisksDTO.getPowerOfInfluences() == null || filledCommercialRisksDTO.getPowerOfInfluences().isEmpty()){
            errors.put("powerOfInfluences", "powerOfInfluences.required");
        }

        if(filledCommercialRisksDTO.getStateForDay() == null){
            errors.put("stateForDay", "stateForDay.required");
        }

        if(filledCommercialRisksDTO.getResponsiblePerson() == null){
            errors.put("responsiblePerson", "responsiblePerson.required");
        }

        if(filledCommercialRisksDTO.getNotationConcernRisk() == null){
            errors.put("notationConcernRisks", "notationConcernRisks.required");
        }

        if(filledCommercialRisksDTO.getHighRiskDTO() == null &&
            probabilityService.getMaxValue().equals(filledCommercialRisksDTO.getProbability()) &&
            powerOfInfluenceService.getMaxValue().equals(filledCommercialRisksDTO.getPowerOfInfluence())) {
            errors.put("highRisk", "highRisk.required");
        }
    }
}
