package rog.service.mapper;

import org.springframework.stereotype.Service;
import rog.domain.GlossaryOfPurposes;
import rog.domain.GlossaryOfRisks;
import rog.domain.OrganisationStructure;
import rog.service.dto.GlossaryOfRisksDTO;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GlossaryOfRisksMapper {

    public GlossaryOfRisksDTO glossaryOfRisksDTO(GlossaryOfRisks glossaryOfRisks) {
        return new GlossaryOfRisksDTO(glossaryOfRisks);
    }

    public Set<GlossaryOfRisks> glossaryOfRisks(Set<GlossaryOfRisksDTO> glossaryOfRisksDTOS) {
        return glossaryOfRisksDTOS
            .stream()
            .filter(Objects::nonNull)
            .map(this::glossaryOfRisks)
            .collect(Collectors.toSet());
    }

    public GlossaryOfRisks glossaryOfRisks(GlossaryOfRisksDTO glossaryOfRisksDTO) {

        if (Objects.isNull(glossaryOfRisksDTO)) {
            return null;
        }

        GlossaryOfRisks glossaryOfRisks = new GlossaryOfRisks();
        glossaryOfRisks.setId(glossaryOfRisksDTO.getId());
        glossaryOfRisks.setName(glossaryOfRisksDTO.getName());
        glossaryOfRisks.setCompletionDate(glossaryOfRisksDTO.getCompletionDate());
        glossaryOfRisks.setImportantTo(glossaryOfRisksDTO.getImportantTo());
        glossaryOfRisks.setChecked(glossaryOfRisksDTO.isChecked());

        OrganisationStructure organisationStructure = new OrganisationStructure();
        organisationStructure.setId(glossaryOfRisksDTO.getOrganisationId());
        glossaryOfRisks.setOrganisationStructure(organisationStructure);

        GlossaryOfPurposes glossaryOfPurposes = new GlossaryOfPurposes();
        glossaryOfPurposes.setId(glossaryOfRisksDTO.getPurposeId());
        glossaryOfRisks.setGlossaryOfPurposes(glossaryOfPurposes);

        return glossaryOfRisks;
    }
}
