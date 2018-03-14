package rog.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import rog.RorumApp;
import rog.domain.GlossaryOfMeasureUnits;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RorumApp.class)
@Transactional
public class GlossaryOfMeasureUnitsServiceUnitTest {

    @Autowired
    private GlossaryOfMeasureUnitsService glossaryOfMeasureUnitsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private static final String USER_LOGIN = "user1B";
    private static final String USER_PASSWORD = "user";

    private static final Long MEASURE_UNIT_ID = 21L;

    private static final Long PURPOSE_ID = 27L;

    @Before
    public void setup() {
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(USER_LOGIN, USER_PASSWORD);
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    public void getAllOfCurrentOrganisationByGlossaryOfPurposesIdTest() {
        GlossaryOfMeasureUnits glossaryOfMeasureUnits =
            glossaryOfMeasureUnitsService.findOne(MEASURE_UNIT_ID);
        List<GlossaryOfMeasureUnits> savedGlossaryOfMeasureUnits =
            glossaryOfMeasureUnitsService.getAllOfCurrentOrganisationByGlossaryOfPurposesId(PURPOSE_ID);
        assertThat(glossaryOfMeasureUnits).isIn(savedGlossaryOfMeasureUnits);
    }
}
