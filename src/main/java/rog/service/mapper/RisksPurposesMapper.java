package rog.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rog.domain.FilledRisks;
import rog.domain.GlossaryOfPurposes;
import rog.domain.RisksPurposes;
import rog.domain.SetOfSentPurposes;
import rog.service.dto.FilledRisksDTO;
import rog.service.dto.RisksPurposesDTO;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RisksPurposesMapper {

    @Autowired
    private FilledRisksMapper filledRisksMapper;

    public Set<RisksPurposes> risksPurposeDTOs(Set<RisksPurposesDTO> risksPurposesDTOS) {
        return risksPurposesDTOS
            .stream()
            .filter(Objects::nonNull)
            .map(this::risksPurpose)
            .collect(Collectors.toSet());
    }

    public RisksPurposes risksPurpose(RisksPurposesDTO risksPurposesDTO) {

        if (Objects.isNull(risksPurposesDTO)) {
            return null;
        }

        Set<FilledRisksDTO> filledRisksDTOS = risksPurposesDTO.getFilledRisksDTOS();
        Set<FilledRisks> filledRisks = filledRisksMapper.filledRisks(filledRisksDTOS);

        RisksPurposes risksPurposes = new RisksPurposes();
        risksPurposes.setFilledRisks(filledRisks);
        risksPurposes.setId(risksPurposesDTO.getId());

        SetOfSentPurposes setOfSentPurposes = new SetOfSentPurposes();
        setOfSentPurposes.setId(risksPurposesDTO.getSetOfSentPurposesId());
        risksPurposes.setSetOfSentPurposes(setOfSentPurposes);

        GlossaryOfPurposes glossaryOfPurposes = new GlossaryOfPurposes();
        glossaryOfPurposes.setId(risksPurposesDTO.getGlossaryOfPurposesId());
        risksPurposes.setGlossaryOfPurposes(glossaryOfPurposes);

        return risksPurposes;
    }
}
