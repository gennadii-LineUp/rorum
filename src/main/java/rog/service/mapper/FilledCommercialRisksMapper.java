package rog.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rog.domain.CommercialRisksPurposes;
import rog.domain.FilledCommercialRisks;
import rog.domain.PowerOfInfluence;
import rog.domain.Probability;
import rog.service.dto.FilledCommercialRisksDTO;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilledCommercialRisksMapper {

    @Autowired
    private GlossaryOfCommercialRisksMapper glossaryOfCommercialRisksMapper;

    @Autowired
    private HighRiskMapper highRiskMapper;

    public FilledCommercialRisksDTO filledCommercialRisksDTO(FilledCommercialRisks filledCommercialRisks) {
        return new FilledCommercialRisksDTO(filledCommercialRisks);
    }

    public Set<FilledCommercialRisks> filledCommercialRisks(Set<FilledCommercialRisksDTO> filledCommercialRisksDTOS) {
        return filledCommercialRisksDTOS
            .stream()
            .filter(Objects::nonNull)
            .map(this::filledCommercialRisk)
            .collect(Collectors.toSet());
    }

    public FilledCommercialRisks filledCommercialRisk(FilledCommercialRisksDTO filledCommercialRisksDTO) {

        if (Objects.isNull(filledCommercialRisksDTO)) {
            return null;
        }

        FilledCommercialRisks filledCommercialRisks = new FilledCommercialRisks();
        filledCommercialRisks.setId(filledCommercialRisksDTO.getId());
        filledCommercialRisks.setProbability(filledCommercialRisksDTO.getProbability());
        filledCommercialRisks.setPowerOfInfluence(filledCommercialRisksDTO.getPowerOfInfluence());
        filledCommercialRisks.setCreationDate(filledCommercialRisksDTO.getCreationDate());
        filledCommercialRisks.setNotationConcernRisk(filledCommercialRisksDTO.getNotationConcernRisk());
        filledCommercialRisks.setReactionOnRisks(filledCommercialRisksDTO.getReactionOnRisks());
        filledCommercialRisks.setSaved(filledCommercialRisksDTO.getSaved());
        filledCommercialRisks.setResponsiblePerson(filledCommercialRisksDTO.getResponsiblePerson());
        filledCommercialRisks.setStateForDay(filledCommercialRisksDTO.getStateForDay());
        filledCommercialRisks.setStrengthOfControlFunctionPowerOfInfluence(filledCommercialRisksDTO.getStrengthOfControlFunctionPowerOfInfluence());
        filledCommercialRisks.setStrengthOfControlFunctionProbability(filledCommercialRisksDTO.getStrengthOfControlFunctionProbability());

        Set<Probability> probabilities = filledCommercialRisksDTO.getProbabilities()
            .stream()
            .map(Probability::new)
            .collect(Collectors.toSet());
        filledCommercialRisks.setProbabilities(probabilities);

        Set<PowerOfInfluence> powerOfInfluences = filledCommercialRisksDTO.getPowerOfInfluences()
            .stream()
            .map(PowerOfInfluence::new)
            .collect(Collectors.toSet());
        filledCommercialRisks.setPowerOfInfluences(powerOfInfluences);

        filledCommercialRisks.setGlossaryOfCommercialRisks(
            glossaryOfCommercialRisksMapper.glossaryOfCommercialRisk(
                filledCommercialRisksDTO.getGlossaryOfCommercialRisksDTO()));

        CommercialRisksPurposes commercialRisksPurposes = new CommercialRisksPurposes();
        commercialRisksPurposes.setId(filledCommercialRisksDTO.getCommercialRiskPurposesId());
        filledCommercialRisks.setCommercialRisksPurposes(commercialRisksPurposes);

        filledCommercialRisks.setHighCommercialRisk(
            highRiskMapper.highCommercialRisk(filledCommercialRisksDTO.getHighRiskDTO()));

        return filledCommercialRisks;
    }
}
