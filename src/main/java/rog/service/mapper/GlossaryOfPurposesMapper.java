package rog.service.mapper;

import org.springframework.stereotype.Service;
import rog.domain.GlossaryOfPurposes;
import rog.service.dto.GlossaryOfPurposesDTO;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GlossaryOfPurposesMapper {

    public GlossaryOfPurposesDTO glossaryOfPurposesDTO(GlossaryOfPurposes glossaryOfPurposes) {
        return new GlossaryOfPurposesDTO(glossaryOfPurposes);
    }

    public Set<GlossaryOfPurposes> glossaryOfPurposesDTOs(Set<GlossaryOfPurposesDTO> glossaryOfPurposesDTOS) {
        return glossaryOfPurposesDTOS
            .stream()
            .filter(Objects::nonNull)
            .map(this::glossaryOfPurposes)
            .collect(Collectors.toSet());
    }

    public GlossaryOfPurposes glossaryOfPurposes(GlossaryOfPurposesDTO glossaryOfPurposesDTO) {

        if (Objects.isNull(glossaryOfPurposesDTO)) {
            return null;
        }

        GlossaryOfPurposes glossaryOfPurposes = new GlossaryOfPurposes();
        glossaryOfPurposes.setId(glossaryOfPurposesDTO.getId());
        glossaryOfPurposes.setName(glossaryOfPurposesDTO.getName());
        glossaryOfPurposes.setChecked(glossaryOfPurposesDTO.getChecked());
        glossaryOfPurposes.setParentId(glossaryOfPurposesDTO.getParentId());
        glossaryOfPurposes.setAssignmentToCell(glossaryOfPurposesDTO.getAssignmentToCell());
        glossaryOfPurposes.setSumUp(glossaryOfPurposesDTO.getSumUp());

        return glossaryOfPurposes;
    }
}
