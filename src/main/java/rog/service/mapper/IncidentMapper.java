package rog.service.mapper;

import org.springframework.stereotype.Service;
import rog.domain.*;
import rog.service.dto.IncidentDTO;

import java.util.Objects;

@Service
public class IncidentMapper {

    public IncidentDTO incidentDTO(Incident incident){
        return new IncidentDTO(incident);
    }

    public Incident incident(IncidentDTO incidentDTO){

        if(Objects.isNull(incidentDTO)){
            return null;
        }

        Incident incident = new Incident();

        incident.setId(incidentDTO.getId());
        incident.setDescription(incidentDTO.getDescription());
        incident.setDescriptionOfPlannedActivities(incidentDTO.getDescriptionOfPlannedActivities());
        incident.setDescriptionOfReaction(incidentDTO.getDescriptionOfReaction());

        SetOfSentPurposes setOfSentPurposes = new SetOfSentPurposes();
        setOfSentPurposes.setId(incidentDTO.getSetOfSentPurposesId());
        incident.setSetOfSentPurposes(setOfSentPurposes);

        Long glossaryOfPurposesId = incidentDTO.getGlossaryOfPurposesId();
        if(glossaryOfPurposesId != null) {
            GlossaryOfPurposes glossaryOfPurposes = new GlossaryOfPurposes();
            glossaryOfPurposes.setId(glossaryOfPurposesId);
            incident.setGlossaryOfPurposes(glossaryOfPurposes);
        }

        Long filledRisksId = incidentDTO.getFilledRisksId();
        if(filledRisksId != null) {
            FilledRisks filledRisks = new FilledRisks();
            filledRisks.setId(filledRisksId);
            incident.setFilledRisks(filledRisks);
        }

        Long filledCommercialRisksId = incidentDTO.getFilledCommercialRisksId();
        if(filledCommercialRisksId != null) {
            FilledCommercialRisks filledCommercialRisks = new FilledCommercialRisks();
            filledCommercialRisks.setId(filledCommercialRisksId);
            incident.setFilledCommercialRisks(filledCommercialRisks);
        }

        return incident;
    }
}
