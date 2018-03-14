package rog.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rog.domain.FilledKeyRiskIndicator;
import rog.domain.GlossaryOfPurposes;
import rog.domain.KeyRiskIndicatorPurposes;
import rog.domain.SetOfSentPurposes;
import rog.service.dto.FilledKeyRiskIndicatorDTO;
import rog.service.dto.KeyRiskIndicatorPurposesDTO;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class KeyRiskIndicatorPurposesMapper {

    @Autowired
    private FilledKeyRiskIndicatorMapper filledKeyRiskIndicatorMapper;

    public Set<KeyRiskIndicatorPurposes> keyRiskIndicatorPurposesDTOsToKeyRiskIndicatorPurposes(Set<KeyRiskIndicatorPurposesDTO> keyRiskIndicatorPurposesDTOS) {
        return keyRiskIndicatorPurposesDTOS.stream()
            .filter(Objects::nonNull)
            .map(this::keyRiskIndicatorPurposesDTOToKeyRiskIndicatorPurposes)
            .collect(Collectors.toSet());
    }

    public KeyRiskIndicatorPurposes keyRiskIndicatorPurposesDTOToKeyRiskIndicatorPurposes(KeyRiskIndicatorPurposesDTO keyRiskIndicatorPurposesDTO){

        if (Objects.isNull(keyRiskIndicatorPurposesDTO)) {
            return null;
        }

        Set<FilledKeyRiskIndicatorDTO> filledMeasureUnitsDTOS = keyRiskIndicatorPurposesDTO.getFilledKeyRiskIndicatorDTOS();

        Set<FilledKeyRiskIndicator> filledKeyRiskIndicators =
            filledKeyRiskIndicatorMapper.filledKeyRiskIndicators(filledMeasureUnitsDTOS);

        KeyRiskIndicatorPurposes keyRiskIndicatorPurposes = new KeyRiskIndicatorPurposes();
        keyRiskIndicatorPurposes.setFilledKeyRiskIndicators(filledKeyRiskIndicators);

        keyRiskIndicatorPurposes.setId(keyRiskIndicatorPurposesDTO.getId());

        SetOfSentPurposes setOfSentPurposes = new SetOfSentPurposes();
        setOfSentPurposes.setId(keyRiskIndicatorPurposesDTO.getSetOfSentPurposesId());
        keyRiskIndicatorPurposes.setSetOfSentPurposes(setOfSentPurposes);

        GlossaryOfPurposes glossaryOfPurposes = new GlossaryOfPurposes();
        glossaryOfPurposes.setId(keyRiskIndicatorPurposesDTO.getGlossaryOfPurposesId());
        keyRiskIndicatorPurposes.setGlossaryOfPurposes(glossaryOfPurposes);

        return keyRiskIndicatorPurposes;
    }

}
