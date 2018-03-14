package rog.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rog.domain.CommercialRisksPurposes;
import rog.domain.FilledCommercialRisks;
import rog.service.dto.CommercialRisksPurposesDTO;
import rog.service.dto.FilledCommercialRisksDTO;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommercialRisksPurposesMapper {

    @Autowired
    private FilledCommercialRisksMapper filledCommercialRisksMapper;

    public Set<CommercialRisksPurposes> commercialRisksPurposes(Set<CommercialRisksPurposesDTO> commercialRisksPurposesDTOS) {
        return commercialRisksPurposesDTOS
            .stream()
            .filter(Objects::nonNull)
            .map(this::commercialRisksPurpose)
            .collect(Collectors.toSet());
    }

    public CommercialRisksPurposes commercialRisksPurpose(CommercialRisksPurposesDTO commercialRisksPurposesDTO) {

        if (Objects.isNull(commercialRisksPurposesDTO)) {
            return null;
        }

        Set<FilledCommercialRisksDTO> filledRisksDTOS = commercialRisksPurposesDTO.getFilledCommercialRisksDTOS();

        Set<FilledCommercialRisks> filledCommercialRisks =
            filledCommercialRisksMapper.filledCommercialRisks(filledRisksDTOS);

        CommercialRisksPurposes commercialRisksPurposes = new CommercialRisksPurposes();
        commercialRisksPurposes.setFilledCommercialRisks(filledCommercialRisks);

        commercialRisksPurposes.setId(commercialRisksPurposesDTO.getId());

        return commercialRisksPurposes;
    }
}
