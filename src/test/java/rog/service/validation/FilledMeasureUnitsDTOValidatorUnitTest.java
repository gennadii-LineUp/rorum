package rog.service.validation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rog.RorumApp;
import rog.service.dto.FilledMeasureUnitsDTO;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RorumApp.class)
public class FilledMeasureUnitsDTOValidatorUnitTest {

    @Autowired
    private Validator filledMeasureUnitsDTOValidator;

    private FilledMeasureUnitsDTO filledMeasureUnitsDTO;

    private Map<String, String> expectedErrors;

    @Before
    public void setup(){
        filledMeasureUnitsDTO = new FilledMeasureUnitsDTO();
        expectedErrors = new HashMap<>();
        expectedErrors.put("baseValue", "baseValue.required");
        expectedErrors.put("actualValue", "actualValue.required");
        expectedErrors.put("finalValue", "finalValue.required");
        expectedErrors.put("costOfPurposeRealisation", "costOfPurposeRealisation.required");
        expectedErrors.put("purposeAccomplishmentStatus", "purposeAccomplishmentStatus.required");
    }

    @Test
    public void validateTest(){
        filledMeasureUnitsDTOValidator.validate(filledMeasureUnitsDTO);

        Map<String, String> errors = filledMeasureUnitsDTO.getErrors();

        assertThat(expectedErrors).containsAllEntriesOf(errors);
    }
}
