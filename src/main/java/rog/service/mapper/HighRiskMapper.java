package rog.service.mapper;

import org.springframework.stereotype.Service;
import rog.domain.*;
import rog.service.dto.HighRiskDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HighRiskMapper {

    public HighRiskDTO highRiskDTO(HighRisk highRisk){
        return new HighRiskDTO(highRisk);
    }

    public HighRiskDTO highRiskDTO(HighCommercialRisk highCommercialRisk){
        return new HighRiskDTO(highCommercialRisk);
    }

    public List<HighRiskDTO> highRisks(List<HighRisk> highRisks) {
        return highRisks
            .stream()
            .filter(Objects::nonNull)
            .map(this::highRiskDTO)
            .collect(Collectors.toList());
    }

    public List<HighRiskDTO> highCommercialRisks(List<HighCommercialRisk> highCommercialRisks) {
        return highCommercialRisks
            .stream()
            .filter(Objects::nonNull)
            .map(this::highRiskDTO)
            .collect(Collectors.toList());
    }

    public HighRisk highRisk(HighRiskDTO highRiskDTO) {

        if (Objects.isNull(highRiskDTO)) {
            return null;
        }

        HighRisk highRisk = new HighRisk();
        highRisk.setId(highRiskDTO.getId());
        highRisk.setRejectToAccomplishPurpose(highRiskDTO.getRejectToAccomplishPurpose());
        highRisk.setPostponePurposeAccomplishment(highRiskDTO.getPostponePurposeAccomplishment());
        highRisk.setRestrictRangeOfPurposeAccomplishment(highRiskDTO.getRestrictRangeOfPurposeAccomplishment());
        Double costOfListedPossibilities = highRiskDTO.getCostOfListedPossibilities();

       if(costOfListedPossibilities != null) {
            highRisk.setCostOfListedPossibilities(BigDecimal.valueOf(costOfListedPossibilities));
        }

        highRisk.setProjectedTermDeployStart(highRiskDTO.getProjectedTermDeployStart());
        highRisk.setProjectedTermDeployFinish(highRiskDTO.getProjectedTermDeployFinish());
        highRisk.setProbabilityToReach(highRiskDTO.getProbabilityToReach());
        highRisk.setPowerOfInfluenceToReach(highRiskDTO.getPowerOfInfluenceToReach());
        highRisk.setAnalyze(highRiskDTO.getAnalyze());
        highRisk.setSubstantiationForAnalyze(highRiskDTO.getSubstantiationForAnalyze());
        highRisk.setCommentToHighRiskProcedure(highRiskDTO.getCommentToHighRiskProcedure());

        Long decisionForRiskId = highRiskDTO.getDecisionForRiskId();
        if(decisionForRiskId != null) {
            DecisionForRisk decisionForRisk = new DecisionForRisk();
            decisionForRisk.setId(decisionForRiskId);
            highRisk.setDecisionForRisk(decisionForRisk);
        }

        FilledRisks filledRisks = new FilledRisks();
        filledRisks.setId(highRiskDTO.getFilledRiskId());
        highRisk.setFilledRisks(filledRisks);

        Set<PossibilitiesToImproveRisk> possibilitiesToImproveRisks =
            highRiskDTO.getPossibilitiesToImproveRisks()
                .stream()
                .map(PossibilitiesToImproveRisk::new)
                .collect(Collectors.toSet());

        highRisk.setPossibilitiesToImproveRisks(possibilitiesToImproveRisks);

        return highRisk;
    }

    public HighCommercialRisk highCommercialRisk(HighRiskDTO highRiskDTO) {

        if (Objects.isNull(highRiskDTO)) {
            return null;
        }

        HighCommercialRisk highCommercialRisk = new HighCommercialRisk();
        highCommercialRisk.setId(highRiskDTO.getId());
        highCommercialRisk.setRejectToAccomplishPurpose(highRiskDTO.getRejectToAccomplishPurpose());
        highCommercialRisk.setPostponePurposeAccomplishment(highRiskDTO.getPostponePurposeAccomplishment());
        highCommercialRisk.setRestrictRangeOfPurposeAccomplishment(highRiskDTO.getRestrictRangeOfPurposeAccomplishment());

        Double costOfListedPossibilities = highRiskDTO.getCostOfListedPossibilities();
        if(costOfListedPossibilities != null) {
            highCommercialRisk.setCostOfListedPossibilities(BigDecimal.valueOf(costOfListedPossibilities));
        }

        highCommercialRisk.setProjectedTermDeployStart(highRiskDTO.getProjectedTermDeployStart());
        highCommercialRisk.setProjectedTermDeployFinish(highRiskDTO.getProjectedTermDeployFinish());
        highCommercialRisk.setProbabilityToReach(highRiskDTO.getProbabilityToReach());
        highCommercialRisk.setPowerOfInfluenceToReach(highRiskDTO.getPowerOfInfluenceToReach());
        highCommercialRisk.setAnalyze(highRiskDTO.getAnalyze());
        highCommercialRisk.setSubstantiationForAnalyze(highRiskDTO.getSubstantiationForAnalyze());
        highCommercialRisk.setCommentToHighRiskProcedure(highRiskDTO.getCommentToHighRiskProcedure());

        Long decisionForRiskId = highRiskDTO.getDecisionForRiskId();
        if(decisionForRiskId != null) {
            DecisionForRisk decisionForRisk = new DecisionForRisk();
            decisionForRisk.setId(decisionForRiskId);
            highCommercialRisk.setDecisionForRisk(decisionForRisk);
        }
        FilledCommercialRisks filledCommercialRisks = new FilledCommercialRisks();
        filledCommercialRisks.setId(highRiskDTO.getFilledRiskId());
        highCommercialRisk.setFilledCommercialRisks(filledCommercialRisks);

        Set<PossibilitiesToImproveRisk> possibilitiesToImproveRisks =
            highRiskDTO.getPossibilitiesToImproveRisks()
                .stream()
                .map(PossibilitiesToImproveRisk::new)
                .collect(Collectors.toSet());

        highCommercialRisk.setPossibilitiesToImproveRisks(possibilitiesToImproveRisks);

        return highCommercialRisk;
    }


}
