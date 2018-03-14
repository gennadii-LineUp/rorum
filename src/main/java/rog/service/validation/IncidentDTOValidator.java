package rog.service.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import rog.service.dto.IncidentDTO;

import java.util.Map;
import java.util.Objects;

@Service
public class IncidentDTOValidator implements Validator {

    private final Logger log = LoggerFactory.getLogger(IncidentDTOValidator.class);

    @Override
    public void validate(Object o) {
        log.debug("Validation of IncidentDTO");

        IncidentDTO incidentDTO = (IncidentDTO) o;

        if(Objects.isNull(incidentDTO)){
            return;
        }

        Map<String, String> errors = incidentDTO.getErrors();

        if(incidentDTO.getDescription() == null){
            errors.put("description", "description.required");
        }

        if(incidentDTO.getDescriptionOfReaction() == null){
            errors.put("descriptionOfReaction", "descriptionOfReaction.required");
        }

        if(incidentDTO.getDescriptionOfPlannedActivities() == null){
            errors.put("descriptionOfPlannedActivities", "descriptionOfPlannedActivities.required");
        }
    }
}
