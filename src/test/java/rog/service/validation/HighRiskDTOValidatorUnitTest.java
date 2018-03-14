package rog.service.validation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rog.RorumApp;
import rog.service.dto.HighRiskDTO;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RorumApp.class)
public class HighRiskDTOValidatorUnitTest {

    @Autowired
    private Validator highRiskDTOValidator;

    private Map<String, String> expectedErrors;

    @Before
    public void setup(){
        expectedErrors = new HashMap<>();
        expectedErrors.put("rejectToAccomplishPurpose", "rejectToAccomplishPurpose.required");
        expectedErrors.put("postponePurposeAccomplishment", "postponePurposeAccomplishment.required");
        expectedErrors.put("restrictRangeOfPurposeAccomplishment", "restrictRangeOfPurposeAccomplishment.required");
        expectedErrors.put("costOfListedPossibilities", "costOfListedPossibilities.required");
        expectedErrors.put("projectedTermDeployStart", "projectedTermDeployStart.required");
        expectedErrors.put("projectedTermDeployFinish", "projectedTermDeployFinish.required");
        expectedErrors.put("probabilityToReach", "probabilityToReach.required");
        expectedErrors.put("powerOfInfluenceToReach", "powerOfInfluenceToReach.required");
        expectedErrors.put("analyze", "analyze.required");
        expectedErrors.put("substantiationForAnalyze", "substantiationForAnalyze.required");
        expectedErrors.put("commentToHighRiskProcedure", "commentToHighRiskProcedure.required");
        expectedErrors.put("decisionForRisk", "decisionForRisk.required");
        expectedErrors.put("possibilitiesToImproveRisks", "possibilitiesToImproveRisks.required");
    }

    @Test
    public void validateTest(){
        HighRiskDTO highRiskDTO = new HighRiskDTO();

        highRiskDTOValidator.validate(highRiskDTO);
        Map<String, String> errors = highRiskDTO.getErrors();

        assertThat(expectedErrors).containsAllEntriesOf(errors);
    }
}
