package rog.service.validation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rog.RorumApp;
import rog.domain.enumeration.StatusOfSending;
import rog.service.dto.SetOfSentPurposesDTO;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RorumApp.class)
public class SetOfSentPurposesDTOValidatorUnitTest {

    @Autowired
    private Validator setOfSentPurposesDTOValidator;

    private SetOfSentPurposesDTO setOfSentPurposesDTO;

    @Before
    public void setup(){
        setOfSentPurposesDTO = new SetOfSentPurposesDTO();
        setOfSentPurposesDTO.setStatusOfSending(StatusOfSending.UNCHECKED_PURPOSES);
    }

    @Test
    public void validateTest(){
        setOfSentPurposesDTOValidator.validate(setOfSentPurposesDTO);
        Map<String, String> errors = setOfSentPurposesDTO.getErrors();
        assertThat(errors).containsOnlyKeys("purposes");
    }
}
