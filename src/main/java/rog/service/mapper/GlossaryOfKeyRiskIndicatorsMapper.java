package rog.service.mapper;

import org.springframework.stereotype.Service;
import rog.domain.GlossaryOfKeyRiskIndicators;
import rog.domain.GlossaryOfPurposes;
import rog.service.dto.GlossaryOfKeyRiskIndicatorsDTO;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GlossaryOfKeyRiskIndicatorsMapper {

    public GlossaryOfKeyRiskIndicatorsDTO glossaryOfKeyRiskIndicatorsDTO(GlossaryOfKeyRiskIndicators glossaryOfKeyRiskIndicators) {
        return new GlossaryOfKeyRiskIndicatorsDTO(glossaryOfKeyRiskIndicators);
    }

    public Set<GlossaryOfKeyRiskIndicators> glossaryOfKeyRiskIndicatorsDTOs(Set<GlossaryOfKeyRiskIndicatorsDTO> glossaryOfKeyRiskIndicatorsDTOS) {
        return glossaryOfKeyRiskIndicatorsDTOS
            .stream()
            .filter(Objects::nonNull)
            .map(this::glossaryOfKeyRiskIndicators)
            .collect(Collectors.toSet());
    }

    public GlossaryOfKeyRiskIndicators glossaryOfKeyRiskIndicators(GlossaryOfKeyRiskIndicatorsDTO glossaryOfKeyRiskIndicatorsDTO) {

        if (Objects.isNull(glossaryOfKeyRiskIndicatorsDTO)) {
            return null;
        }

        GlossaryOfKeyRiskIndicators glossaryOfKeyRiskIndicators = new GlossaryOfKeyRiskIndicators();
        glossaryOfKeyRiskIndicators.setId(glossaryOfKeyRiskIndicatorsDTO.getId());
        glossaryOfKeyRiskIndicators.setName(glossaryOfKeyRiskIndicatorsDTO.getName());
        glossaryOfKeyRiskIndicators.setImportantTo(glossaryOfKeyRiskIndicatorsDTO.getImportantTo());

        GlossaryOfPurposes glossaryOfPurposes = new GlossaryOfPurposes();
        glossaryOfPurposes.setId(glossaryOfKeyRiskIndicatorsDTO.getPurposeId());
        glossaryOfKeyRiskIndicators.setGlossaryOfPurposes(glossaryOfPurposes);

        return glossaryOfKeyRiskIndicators;
    }

}
