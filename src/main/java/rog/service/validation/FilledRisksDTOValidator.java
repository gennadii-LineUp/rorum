package rog.service.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rog.service.PowerOfInfluenceService;
import rog.service.ProbabilityService;
import rog.service.dto.FilledRisksDTO;

import java.util.Map;
import java.util.Objects;

@Component
public class FilledRisksDTOValidator implements Validator {

    private final Logger log = LoggerFactory.getLogger(FilledRisksDTOValidator.class);

    @Autowired
    private ProbabilityService probabilityService;

    @Autowired
    private PowerOfInfluenceService powerOfInfluenceService;

    @Override
    public void validate(Object o) {
        log.debug("Validation of FilledRisksDTO");

        FilledRisksDTO filledRisksDTO = (FilledRisksDTO) o;

        if(Objects.isNull(filledRisksDTO)){
            return;
        }

        Map<String, String> errors = filledRisksDTO.getErrors();

        if(filledRisksDTO.getProbability() == null){
            errors.put("probability", "probability.required");
        } else if(filledRisksDTO.getProbability() > probabilityService.getMaxValue()){
            errors.put("probability", "probability.max");
        } else if(filledRisksDTO.getProbability() < 1){
            errors.put("probability", "probability.min");
        }

        if(filledRisksDTO.getPowerOfInfluence() == null){
            errors.put("powerOfInfluence", "powerOfInfluence.required");
        }else if(filledRisksDTO.getPowerOfInfluence() > powerOfInfluenceService.getMaxValue()){
            errors.put("powerOfInfluence", "powerOfInfluence.max");
        } else if(filledRisksDTO.getPowerOfInfluence() < 1){
            errors.put("powerOfInfluence", "powerOfInfluence.min");
        }

        if(filledRisksDTO.getStrengthOfControlFunctionProbability() == null){
            errors.put("strengthOfControlFunctionProbability", "strengthOfControlFunctionProbability.required");
        } else if(filledRisksDTO.getStrengthOfControlFunctionProbability() > probabilityService.getMaxValue()){
            errors.put("strengthOfControlFunctionProbability", "strengthOfControlFunctionProbability.max");
        } else if(filledRisksDTO.getStrengthOfControlFunctionProbability() < 1){
            errors.put("strengthOfControlFunctionProbability", "strengthOfControlFunctionProbability.min");
        }

        if(filledRisksDTO.getStrengthOfControlFunctionPowerOfInfluence() == null){
            errors.put("strengthOfControlFunctionPowerOfInfluence", "strengthOfControlFunctionPowerOfInfluence.required");
        }else if(filledRisksDTO.getStrengthOfControlFunctionPowerOfInfluence() > powerOfInfluenceService.getMaxValue()){
            errors.put("strengthOfControlFunctionPowerOfInfluence", "strengthOfControlFunctionPowerOfInfluence.max");
        } else if(filledRisksDTO.getStrengthOfControlFunctionPowerOfInfluence() < 1){
            errors.put("strengthOfControlFunctionPowerOfInfluence", "strengthOfControlFunctionPowerOfInfluence.min");
        }

        if(filledRisksDTO.getProbabilities() == null || filledRisksDTO.getProbabilities().isEmpty()){
            errors.put("probabilities", "probabilities.required");
        }

        if(filledRisksDTO.getPowerOfInfluences() == null || filledRisksDTO.getPowerOfInfluences().isEmpty()){
            errors.put("powerOfInfluences", "powerOfInfluences.required");
        }

        if(filledRisksDTO.getStateForDay() == null){
            errors.put("stateForDay", "stateForDay.required");
        }

        if(filledRisksDTO.getResponsiblePerson() == null){
            errors.put("responsiblePerson", "responsiblePerson.required");
        }

        if(filledRisksDTO.getNotationConcernRisk() == null){
            errors.put("notationConcernRisks", "notationConcernRisks.required");
        }

        if(filledRisksDTO.getHighRiskDTO() == null &&
                probabilityService.getMaxValue().equals(filledRisksDTO.getProbability()) &&
                powerOfInfluenceService.getMaxValue().equals(filledRisksDTO.getPowerOfInfluence())) {
                errors.put("highRisk", "highRisk.required");
            }
        }
}
