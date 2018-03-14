package rog.service.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import rog.service.dto.HighRiskDTO;

import java.util.Map;
import java.util.Objects;

@Component
public class HighRiskDTOValidator implements Validator {

    private final Logger log = LoggerFactory.getLogger(HighRiskDTOValidator.class);

    @Override
    public void validate(Object o) {
        log.debug("Validation of HighRiskDTO");

        HighRiskDTO highRiskDTO = (HighRiskDTO) o;

        if(Objects.isNull(highRiskDTO)){
            return;
        }

        Map<String, String> errors = highRiskDTO.getErrors();

        if(highRiskDTO.getRejectToAccomplishPurpose() == null){
            errors.put("rejectToAccomplishPurpose", "rejectToAccomplishPurpose.required");
        }

        if(highRiskDTO.getPostponePurposeAccomplishment() == null){
            errors.put("postponePurposeAccomplishment", "postponePurposeAccomplishment.required");
        }

        if(highRiskDTO.getRestrictRangeOfPurposeAccomplishment() == null){
            errors.put("restrictRangeOfPurposeAccomplishment", "restrictRangeOfPurposeAccomplishment.required");
        }

        if(highRiskDTO.getCostOfListedPossibilities() == null){
            errors.put("costOfListedPossibilities", "costOfListedPossibilities.required");
        }

        if(highRiskDTO.getProjectedTermDeployStart() == null){
            errors.put("projectedTermDeployStart", "projectedTermDeployStart.required");
        }

        if(highRiskDTO.getProjectedTermDeployFinish() == null){
            errors.put("projectedTermDeployFinish", "projectedTermDeployFinish.required");
        }

        if(highRiskDTO.getProbabilityToReach() == null){
            errors.put("probabilityToReach", "probabilityToReach.required");
        }

        if(highRiskDTO.getPowerOfInfluenceToReach() == null){
            errors.put("powerOfInfluenceToReach", "powerOfInfluenceToReach.required");
        }

        if(highRiskDTO.getAnalyze() == null){
            errors.put("analyze", "analyze.required");
        }

        if(highRiskDTO.getSubstantiationForAnalyze() == null){
            errors.put("substantiationForAnalyze", "substantiationForAnalyze.required");
        }

        if(highRiskDTO.getCommentToHighRiskProcedure() == null){
            errors.put("commentToHighRiskProcedure", "commentToHighRiskProcedure.required");
        }

        if(highRiskDTO.getDecisionForRiskId() == null){
            errors.put("decisionForRisk", "decisionForRisk.required");
        }

        if(highRiskDTO.getPossibilitiesToImproveRisks() == null ||
            highRiskDTO.getPossibilitiesToImproveRisks().isEmpty()){
            errors.put("possibilitiesToImproveRisks", "possibilitiesToImproveRisks.required");
        }
    }
}
