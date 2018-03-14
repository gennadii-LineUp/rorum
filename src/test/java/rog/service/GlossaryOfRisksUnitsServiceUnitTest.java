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
import rog.domain.GlossaryOfRisks;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RorumApp.class)
@Transactional
public class GlossaryOfRisksUnitsServiceUnitTest {

    @Autowired
    private GlossaryOfRisksService glossaryOfRisksService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private static final String USER_LOGIN = "user1B";
    private static final String USER_PASSWORD = "user";

    private static final Long RISK_ID = 121L;

    @Before
    public void setup() {
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(USER_LOGIN, USER_PASSWORD);
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    public void getAllOfCurrentOrganisationByGlossaryOfPurposesIdTest() {
        GlossaryOfRisks glossaryOfRisks = glossaryOfRisksService.findOne(RISK_ID);
        List<GlossaryOfRisks> savedGlossaryOfRisks =
            glossaryOfRisksService.getAllOfCurrentOrganisationByGlossaryOfPurposesId(glossaryOfRisks.getGlossaryOfPurposes().getId());
        assertThat(glossaryOfRisks).isIn(savedGlossaryOfRisks);
    }
}
