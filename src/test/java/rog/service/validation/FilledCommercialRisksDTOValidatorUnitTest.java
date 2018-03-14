package rog.service.validation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rog.RorumApp;
import rog.service.ProbabilityService;
import rog.service.dto.FilledCommercialRisksDTO;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RorumApp.class)
public class FilledCommercialRisksDTOValidatorUnitTest {

    @Autowired
    private Validator filledCommercialRisksDTOValidator;

    private FilledCommercialRisksDTO filledCommercialRisksDTO;

    private Map<String, String> expectedErrors;

    @Autowired
    private ProbabilityService probabilityService;

    @Before
    public void setup(){
        filledCommercialRisksDTO = new FilledCommercialRisksDTO();
        filledCommercialRisksDTO.setProbability(probabilityService.getMaxValue() + 1);
        filledCommercialRisksDTO.setPowerOfInfluence(-1);

        expectedErrors = new HashMap<>();
        expectedErrors.put("probability", "probability.max");
        expectedErrors.put("powerOfInfluence", "powerOfInfluence.min");
        expectedErrors.put("strengthOfControlFunctionProbability", "strengthOfControlFunctionProbability.required");
        expectedErrors.put("strengthOfControlFunctionPowerOfInfluence", "strengthOfControlFunctionPowerOfInfluence.required");
        expectedErrors.put("probabilities", "probabilities.required");
        expectedErrors.put("powerOfInfluences", "powerOfInfluences.required");
        expectedErrors.put("responsiblePerson", "responsiblePerson.required");
        expectedErrors.put("stateForDay", "stateForDay.required");
        expectedErrors.put("notationConcernRisks", "notationConcernRisks.required");
    }

    @Test
    public void validateTest(){
        filledCommercialRisksDTOValidator.validate(filledCommercialRisksDTO);

        Map<String, String> errors = filledCommercialRisksDTO.getErrors();

        assertThat(expectedErrors).containsAllEntriesOf(errors);
    }
}
