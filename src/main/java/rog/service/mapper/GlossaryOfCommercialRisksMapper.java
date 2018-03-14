package rog.service.mapper;

import org.springframework.stereotype.Service;
import rog.domain.GlossaryOfCommercialRisks;
import rog.domain.OrganisationStructure;
import rog.service.dto.GlossaryOfCommercialRisksDTO;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GlossaryOfCommercialRisksMapper {

    public GlossaryOfCommercialRisksDTO glossaryOfCommercialRisksDTO(GlossaryOfCommercialRisks glossaryOfCommercialRisks) {
        return new GlossaryOfCommercialRisksDTO(glossaryOfCommercialRisks);
    }

    public Set<GlossaryOfCommercialRisks> glossaryOfCommercialRisks(Set<GlossaryOfCommercialRisksDTO> glossaryOfCommercialRisksDTOS) {
        return glossaryOfCommercialRisksDTOS
            .stream()
            .filter(Objects::nonNull)
            .map(this::glossaryOfCommercialRisk)
            .collect(Collectors.toSet());
    }

    public GlossaryOfCommercialRisks glossaryOfCommercialRisk(GlossaryOfCommercialRisksDTO glossaryOfCommercialRisksDTO) {

        if (Objects.isNull(glossaryOfCommercialRisksDTO)) {
            return null;
        }

        GlossaryOfCommercialRisks glossaryOfCommercialRisks = new GlossaryOfCommercialRisks();
        glossaryOfCommercialRisks.setId(glossaryOfCommercialRisksDTO.getId());
        glossaryOfCommercialRisks.setName(glossaryOfCommercialRisksDTO.getName());
        glossaryOfCommercialRisks.setCompletionDate(glossaryOfCommercialRisksDTO.getCompletionDate());
        glossaryOfCommercialRisks.setImportantTo(glossaryOfCommercialRisksDTO.getImportantTo());
        glossaryOfCommercialRisks.setChecked(glossaryOfCommercialRisksDTO.isChecked());

        OrganisationStructure organisationStructure = new OrganisationStructure();
        organisationStructure.setId(glossaryOfCommercialRisksDTO.getOrganisationId());
        glossaryOfCommercialRisks.setOrganisationStructure(organisationStructure);

        return glossaryOfCommercialRisks;
    }
}
