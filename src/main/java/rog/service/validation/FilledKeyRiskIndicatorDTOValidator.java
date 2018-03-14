package rog.service.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import rog.service.dto.FilledKeyRiskIndicatorDTO;

import java.util.Map;
import java.util.Objects;

@Service
public class FilledKeyRiskIndicatorDTOValidator implements Validator {

    private final Logger log = LoggerFactory.getLogger(FilledKeyRiskIndicatorDTOValidator.class);

    @Override
    public void validate(Object o) {
        log.debug("Validation of FilledKeyRiskIndicatorDTO");

        FilledKeyRiskIndicatorDTO filledKeyRiskIndicatorDTO = (FilledKeyRiskIndicatorDTO) o;

        if(Objects.isNull(filledKeyRiskIndicatorDTO)){
            return;
        }

        Map<String, String> errors = filledKeyRiskIndicatorDTO.getErrors();

        if(filledKeyRiskIndicatorDTO.getMinCautionaryStep() == null){
            errors.put("minCautionaryStep", "minCautionaryStep.required");
        } else if(filledKeyRiskIndicatorDTO.getMinCautionaryStep() < -100000){
            errors.put("minCautionaryStep", "minCautionaryStep.min");
        }

        if(filledKeyRiskIndicatorDTO.getFirstCautionaryStep() == null){
            errors.put("firstCautionaryStep", "firstCautionaryStep.required");
        } else if(filledKeyRiskIndicatorDTO.getFirstCautionaryStep() < -100000){
            errors.put("firstCautionaryStep", "firstCautionaryStep.min");
        } else if(filledKeyRiskIndicatorDTO.getFirstCautionaryStep() > 100000){
            errors.put("firstCautionaryStep", "firstCautionaryStep.max");
        }

        if(filledKeyRiskIndicatorDTO.getSecondCautionaryStep() == null){
            errors.put("secondCautionaryStep", "secondCautionaryStep.required");
        } else if(filledKeyRiskIndicatorDTO.getSecondCautionaryStep() < -100000){
            errors.put("secondCautionaryStep", "secondCautionaryStep.min");
        } else if(filledKeyRiskIndicatorDTO.getSecondCautionaryStep() > 100000){
            errors.put("secondCautionaryStep", "secondCautionaryStep.max");
        }

        if(filledKeyRiskIndicatorDTO.getThirdCautionaryStep() == null){
            errors.put("thirdCautionaryStep", "thirdCautionaryStep.required");
        } else if(filledKeyRiskIndicatorDTO.getSecondCautionaryStep() < -100000){
            errors.put("thirdCautionaryStep", "thirdCautionaryStep.min");
        } else if(filledKeyRiskIndicatorDTO.getSecondCautionaryStep() > 100000){
            errors.put("thirdCautionaryStep", "thirdCautionaryStep.max");
        }

        if(filledKeyRiskIndicatorDTO.getFourthCautionaryStep() == null){
            errors.put("fourthCautionaryStep", "fourthCautionaryStep.required");
        } else if(filledKeyRiskIndicatorDTO.getFourthCautionaryStep() < -100000){
            errors.put("fourthCautionaryStep", "fourthCautionaryStep.min");
        } else if(filledKeyRiskIndicatorDTO.getFourthCautionaryStep() > 100000){
            errors.put("fourthCautionaryStep", "fourthCautionaryStep.max");
        }

        if(filledKeyRiskIndicatorDTO.getMaxCautionaryStep() == null){
            errors.put("maxCautionaryStep", "maxCautionaryStep.required");
        }  else if(filledKeyRiskIndicatorDTO.getFourthCautionaryStep() > 100000){
            errors.put("maxCautionaryStep", "maxCautionaryStep.max");
        }

        if(filledKeyRiskIndicatorDTO.getKriValue() == null){
            errors.put("kriValue", "kriValue.required");
        }
    }
}
