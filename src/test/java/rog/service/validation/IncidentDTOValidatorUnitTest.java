package rog.service.validation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rog.RorumApp;
import rog.service.dto.IncidentDTO;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RorumApp.class)
public class IncidentDTOValidatorUnitTest {

    @Autowired
    private Validator incidentDTOValidator;

    private Map<String, String> expectedErrors;

    @Before
    public void setup(){
        expectedErrors = new HashMap<>();
        expectedErrors.put("description", "description.required");
        expectedErrors.put("descriptionOfReaction", "descriptionOfReaction.required");
        expectedErrors.put("descriptionOfPlannedActivities", "descriptionOfPlannedActivities.required");
    }

    @Test
    public void validateTest(){
        IncidentDTO incidentDTO = new IncidentDTO();

        incidentDTOValidator.validate(incidentDTO);
        Map<String, String> errors = incidentDTO.getErrors();

        assertThat(expectedErrors).containsAllEntriesOf(errors);
    }
}
