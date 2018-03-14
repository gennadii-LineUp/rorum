package rog.service.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import rog.domain.enumeration.PurposeAccomplishmentStatus;
import rog.service.dto.FilledMeasureUnitsDTO;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

@Component
public class FilledMeasureUnitsDTOValidator implements Validator {

    private final Logger log = LoggerFactory.getLogger(FilledMeasureUnitsDTOValidator.class);

    @Override
    public void validate(Object o) {
        log.debug("Validation of FilledMeasureUnitsDTO");

        FilledMeasureUnitsDTO filledMeasureUnitsDTO = (FilledMeasureUnitsDTO) o;

        if(Objects.isNull(filledMeasureUnitsDTO)){
            return;
        }

        Map<String, String> errors = filledMeasureUnitsDTO.getErrors();

        if(filledMeasureUnitsDTO.getBaseValue() == null){
            errors.put("baseValue", "baseValue.required");
        } else if(filledMeasureUnitsDTO.getBaseValue() < 0){
            errors.put("baseValue", "baseValue.min");
        }

        if(filledMeasureUnitsDTO.getActualValue() == null){
            errors.put("actualValue", "actualValue.required");
        } else if(filledMeasureUnitsDTO.getActualValue() < 0){
            errors.put("actualValue", "actualValue.min");
        }

        if(filledMeasureUnitsDTO.getFinalValue() == null){
            errors.put("finalValue", "finalValue.required");
        } else if(filledMeasureUnitsDTO.getFinalValue() < 0){
            errors.put("finalValue", "finalValue.min");
        }

        if(filledMeasureUnitsDTO.getCostOfPurposeRealisation() == null){
            errors.put("costOfPurposeRealisation", "costOfPurposeRealisation.required");
        } else if(filledMeasureUnitsDTO.getCostOfPurposeRealisation() < 0){
            errors.put("costOfPurposeRealisation", "costOfPurposeRealisation.min");
        }

        if(filledMeasureUnitsDTO.getPurposeAccomplishmentStatus() == null){
            errors.put("purposeAccomplishmentStatus", "purposeAccomplishmentStatus.required");
        } else if(!Arrays.asList(PurposeAccomplishmentStatus.values()).contains(filledMeasureUnitsDTO.getPurposeAccomplishmentStatus())){
            errors.put("purposeAccomplishmentStatus", "purposeAccomplishmentStatus.wrong");
        }
    }
}
