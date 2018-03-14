package rog.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rog.domain.FilledRisks;
import rog.domain.PowerOfInfluence;
import rog.domain.Probability;
import rog.domain.RisksPurposes;
import rog.service.dto.FilledRisksDTO;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilledRisksMapper {

    @Autowired
    private GlossaryOfRisksMapper glossaryOfRisksMapper;

    @Autowired
    private HighRiskMapper highRiskMapper;

    public FilledRisksDTO filledRisksDTO(FilledRisks filledRisks) {
        return new FilledRisksDTO(filledRisks);
    }

    public Set<FilledRisks> filledRisks(Set<FilledRisksDTO> filledRisks) {
        return filledRisks
            .stream()
            .filter(Objects::nonNull)
            .map(this::filledRisk)
            .collect(Collectors.toSet());
    }

    public FilledRisks filledRisk(FilledRisksDTO filledRisksDTO) {

        if (Objects.isNull(filledRisksDTO)) {
            return null;
        }

        FilledRisks filledRisks = new FilledRisks();
        filledRisks.setId(filledRisksDTO.getId());
        filledRisks.setProbability(filledRisksDTO.getProbability());
        filledRisks.setPowerOfInfluence(filledRisksDTO.getPowerOfInfluence());
        filledRisks.setCreationDate(filledRisksDTO.getCreationDate());
        filledRisks.setNotationConcernRisk(filledRisksDTO.getNotationConcernRisk());
        filledRisks.setReactionOnRisks(filledRisksDTO.getReactionOnRisks());
        filledRisks.setSaved(filledRisksDTO.getSaved());
        filledRisks.setResponsiblePerson(filledRisksDTO.getResponsiblePerson());
        filledRisks.setStateForDay(filledRisksDTO.getStateForDay());
        filledRisks.setStrengthOfControlFunctionPowerOfInfluence(filledRisksDTO.getStrengthOfControlFunctionPowerOfInfluence());
        filledRisks.setStrengthOfControlFunctionProbability(filledRisksDTO.getStrengthOfControlFunctionProbability());

        Set<Probability> probabilities = filledRisksDTO.getProbabilities()
            .stream()
            .map(Probability::new)
            .collect(Collectors.toSet());
        filledRisks.setProbabilities(probabilities);

        Set<PowerOfInfluence> powerOfInfluences = filledRisksDTO.getPowerOfInfluences()
            .stream()
            .map(PowerOfInfluence::new)
            .collect(Collectors.toSet());
        filledRisks.setPowerOfInfluences(powerOfInfluences);

        filledRisks.setGlossaryOfRisks(glossaryOfRisksMapper
            .glossaryOfRisks(filledRisksDTO.getGlossaryOfRisksDTO()));

        RisksPurposes risksPurposes = new RisksPurposes();
        risksPurposes.setId(filledRisksDTO.getRisksPurposesId());
        filledRisks.setRisksPurposes(risksPurposes);

        filledRisks.setHighRisk(highRiskMapper.highRisk(filledRisksDTO.getHighRiskDTO()));

        return filledRisks;
    }
}
