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
import rog.domain.GlossaryOfCommercialRisks;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RorumApp.class)
@Transactional
public class GlossaryOfCommercialRisksServiceUnitTest {

    @Autowired
    private GlossaryOfCommercialRisksService glossaryOfCommercialRisksService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private static final String USER_LOGIN = "globalAdmin";
    private static final String USER_PASSWORD = "admin";

    private static final Long COMMERCIAL_RISK_ID_1 = 1L;
    private static final Long COMMERCIAL_RISK_ID_2 = 3L;

    @Before
    public void setup() {
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(USER_LOGIN, USER_PASSWORD);
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    public void findByUserIsCurrentUserTest() {
        GlossaryOfCommercialRisks glossaryOfCommercialRisks =
            glossaryOfCommercialRisksService.findOne(COMMERCIAL_RISK_ID_1);
        List<GlossaryOfCommercialRisks> savedGlossaryOfCommercialRisks =
            glossaryOfCommercialRisksService.findByUserIsCurrentUser();
        assertThat(glossaryOfCommercialRisks).isIn(savedGlossaryOfCommercialRisks);
    }

    @Test
    public void getAllOfCurrentOrganisationTest(){
        GlossaryOfCommercialRisks glossaryOfCommercialRisks =
            glossaryOfCommercialRisksService.findOne(COMMERCIAL_RISK_ID_2);
        List<GlossaryOfCommercialRisks> savedGlossaryOfCommercialRisks =
            glossaryOfCommercialRisksService.getAllOfCurrentOrganisation();
        assertThat(glossaryOfCommercialRisks).isIn(savedGlossaryOfCommercialRisks);
    }
}
