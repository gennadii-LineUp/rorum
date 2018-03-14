package rog.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rog.domain.*;
import rog.service.dto.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SetOfSentPurposesMapper {

    @Autowired
    private MeasureUnitsPurposeMapper measureUnitsPurposeMapper;

    @Autowired
    private RisksPurposesMapper risksPurposesMapper;

    @Autowired
    private CommercialRisksPurposesMapper commercialRisksPurposesMapper;

    @Autowired
    private KeyRiskIndicatorPurposesMapper keyRiskIndicatorPurposesMapper;

    public SetOfSentPurposesDTO setOfSentPurposesDTO(SetOfSentPurposes setOfSentPurposes) {
        return new SetOfSentPurposesDTO(setOfSentPurposes);
    }

    public List<SetOfSentPurposesDTO> setOfSentPurposesDTOs(List<SetOfSentPurposes> setOfSentPurposes) {
        return setOfSentPurposes
            .stream()
            .filter(Objects::nonNull)
            .map(this::setOfSentPurposesDTO)
            .collect(Collectors.toList());
    }

    public SetOfSentPurposes setOfSentPurposes(SetOfSentPurposesDTO setOfSentPurposesDTO) {

        if (Objects.isNull(setOfSentPurposesDTO)) {
            return null;
        }

        SetOfSentPurposes setOfSentPurposes = new SetOfSentPurposes();

        Set<GlossaryOfPurposes> purposes = setOfSentPurposesDTO.getIds()
            .stream()
            .map(GlossaryOfPurposes::new)
            .collect(Collectors.toSet());

        setOfSentPurposes.setGlossaryOfPurposes(purposes);
        setOfSentPurposes.setId(setOfSentPurposesDTO.getId());
        setOfSentPurposes.setNotation(setOfSentPurposesDTO.getNotation());
        setOfSentPurposes.setStatusOfSending(setOfSentPurposesDTO.getStatusOfSending());

        Set<MeasureUnitsPurposesDTO> measureUnitsPurposesDTO = setOfSentPurposesDTO.getMeasureUnitsPurposesDTOS();

        Set<MeasureUnitsPurposes> measureUnitsPurposes = measureUnitsPurposeMapper
            .measureUnitsPurposeDTOsToMeasureUnitsPurposes(measureUnitsPurposesDTO);
        setOfSentPurposes.setMeasureUnitsPurposes(measureUnitsPurposes);

        Set<RisksPurposesDTO> risksPurposesDTO = setOfSentPurposesDTO.getRisksPurposesDTOS();

        Set<RisksPurposes> risksPurposes = risksPurposesMapper.risksPurposeDTOs(risksPurposesDTO);
        setOfSentPurposes.setRisksPurposes(risksPurposes);

        CommercialRisksPurposesDTO commercialRisksPurposesDTO = setOfSentPurposesDTO.getCommercialRisksPurposesDTO();

        CommercialRisksPurposes commercialRisksPurposes = commercialRisksPurposesMapper
            .commercialRisksPurpose(commercialRisksPurposesDTO);

        setOfSentPurposes.setCommercialRisksPurposes(commercialRisksPurposes);

        Set<KeyRiskIndicatorPurposesDTO> keyRiskIndicatorPurposesDTOS = setOfSentPurposesDTO.getKeyRiskIndicatorPurposesDTOS();

        Set<KeyRiskIndicatorPurposes> keyRiskIndicatorPurposes =
            keyRiskIndicatorPurposesMapper.keyRiskIndicatorPurposesDTOsToKeyRiskIndicatorPurposes(keyRiskIndicatorPurposesDTOS);
        setOfSentPurposes.setKeyRiskIndicatorPurposes(keyRiskIndicatorPurposes);

        return setOfSentPurposes;
    }

    public Set<FilledCommercialRisksDTO> getFilledCommercialRiskDTOs(SetOfSentPurposesDTO setOfSentPurposesDTO){
        CommercialRisksPurposesDTO commercialRisksPurposesDTO = setOfSentPurposesDTO.getCommercialRisksPurposesDTO();
        return commercialRisksPurposesDTO != null ?
            commercialRisksPurposesDTO.getFilledCommercialRisksDTOS() : Collections.emptySet();
    }

    public Set<FilledKeyRiskIndicatorDTO> getFilledKeyRiskIndicatorDTOs(SetOfSentPurposesDTO setOfSentPurposesDTO){
        return setOfSentPurposesDTO.getKeyRiskIndicatorPurposesDTOS()
            .stream()
            .flatMap(krip -> krip.getFilledKeyRiskIndicatorDTOS().stream())
            .collect(Collectors.toSet());
    }

    public Set<FilledMeasureUnitsDTO> getFilledMeasureUnitsDTOs(SetOfSentPurposesDTO setOfSentPurposesDTO) {
        return setOfSentPurposesDTO.getMeasureUnitsPurposesDTOS()
            .stream()
            .flatMap(mup -> mup.getFilledMeasureUnitsDTOS().stream())
            .collect(Collectors.toSet());
    }

    public Set<FilledRisksDTO> getFilledRisksDTOs(SetOfSentPurposesDTO setOfSentPurposesDTO){
        return setOfSentPurposesDTO.getRisksPurposesDTOS()
            .stream()
            .flatMap(rp -> rp.getFilledRisksDTOS().stream())
            .collect(Collectors.toSet());
    }
}
