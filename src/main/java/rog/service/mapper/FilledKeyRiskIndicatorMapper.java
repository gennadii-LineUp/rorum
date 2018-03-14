package rog.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rog.domain.FilledKeyRiskIndicator;
import rog.domain.KeyRiskIndicatorPurposes;
import rog.service.dto.FilledKeyRiskIndicatorDTO;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilledKeyRiskIndicatorMapper {

    @Autowired
    private GlossaryOfKeyRiskIndicatorsMapper glossaryOfKeyRiskIndicatorsMapper;

    public FilledKeyRiskIndicatorDTO filledKeyRiskIndicatorDTO(FilledKeyRiskIndicator filledKeyRiskIndicator) {
        return new FilledKeyRiskIndicatorDTO(filledKeyRiskIndicator);
    }

    public Set<FilledKeyRiskIndicator> filledKeyRiskIndicators(Set<FilledKeyRiskIndicatorDTO> filledKeyRiskIndicatorDTOS) {
        return filledKeyRiskIndicatorDTOS
            .stream()
            .filter(Objects::nonNull)
            .map(this::filledKeyRiskIndicator)
            .collect(Collectors.toSet());
    }

    public FilledKeyRiskIndicator filledKeyRiskIndicator(FilledKeyRiskIndicatorDTO filledKeyRiskIndicatorDTO) {

        if (Objects.isNull(filledKeyRiskIndicatorDTO)) {
            return null;
        }

        FilledKeyRiskIndicator filledKeyRiskIndicator = new FilledKeyRiskIndicator();
        filledKeyRiskIndicator.setId(filledKeyRiskIndicatorDTO.getId());
        filledKeyRiskIndicator.setMinCautionaryStep(filledKeyRiskIndicatorDTO.getMinCautionaryStep());
        filledKeyRiskIndicator.setMaxCautionaryStep(filledKeyRiskIndicatorDTO.getMaxCautionaryStep());
        filledKeyRiskIndicator.setFirstCautionaryStep(filledKeyRiskIndicatorDTO.getFirstCautionaryStep());
        filledKeyRiskIndicator.setSecondCautionaryStep(filledKeyRiskIndicatorDTO.getSecondCautionaryStep());
        filledKeyRiskIndicator.setThirdCautionaryStep(filledKeyRiskIndicatorDTO.getThirdCautionaryStep());
        filledKeyRiskIndicator.setFourthCautionaryStep(filledKeyRiskIndicatorDTO.getFourthCautionaryStep());
        filledKeyRiskIndicator.setKriValue(filledKeyRiskIndicatorDTO.getKriValue());
        filledKeyRiskIndicator.setDoesUserRealize(filledKeyRiskIndicatorDTO.getDoesUserRealize());
        filledKeyRiskIndicator.setJm(filledKeyRiskIndicatorDTO.getJm());
        filledKeyRiskIndicator.setSaved(filledKeyRiskIndicatorDTO.getSaved());

        filledKeyRiskIndicator.setGlossaryOfKeyRiskIndicators(glossaryOfKeyRiskIndicatorsMapper
            .glossaryOfKeyRiskIndicators(filledKeyRiskIndicatorDTO.getGlossaryOfKeyRiskIndicatorsDTO()));

        KeyRiskIndicatorPurposes keyRiskIndicatorPurposes = new KeyRiskIndicatorPurposes();
        keyRiskIndicatorPurposes.setId(filledKeyRiskIndicatorDTO.getKeyRiskIndicatorPurposesId());
        filledKeyRiskIndicator.setKeyRiskIndicatorPurposes(keyRiskIndicatorPurposes);

        return filledKeyRiskIndicator;
    }

}
