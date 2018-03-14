package rog.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import rog.RorumApp;
import rog.domain.GlossaryOfMeasureUnits;
import rog.domain.enumeration.FrequencyOfGatheringData;
import rog.domain.enumeration.UnitsOfMeasurement;
import rog.service.dto.GlossaryOfMeasureUnitsDTO;
import rog.service.dto.GlossaryOfRisksDTO;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RorumApp.class)
public class GlossaryOfPurposesResourceUnitTest {

    private MockMvc restGlossaryOfPurposesMockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AuthenticationManager authenticationManager;

    private static final String USER_LOGIN = "user1B";
    private static final String USER_PASSWORD = "user";

    private static final Long PURPOSE_ID = 1L;
    private static final Long INVALID_PURPOSE_ID = -1L;

    private static final String NAME = "name";
    private static final LocalDate DATE = LocalDate.now();
    private static final FrequencyOfGatheringData FREQUENCY_OF_GATHERING_DATA = FrequencyOfGatheringData.DAY;
    private static final UnitsOfMeasurement UNITS_OF_MEASUREMENT = UnitsOfMeasurement.KM;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        this.restGlossaryOfPurposesMockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void getGlossaryOfMeasureUnitsByInvalidPurposeIdTest() throws Exception {
        login(USER_LOGIN, USER_PASSWORD);

        restGlossaryOfPurposesMockMvc
            .perform(get("/api/glossary-of-purposes/{purposeId}/glossary-of-measure-units", INVALID_PURPOSE_ID))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void getGlossaryOfRisksByInvalidPurposeIdTest() throws Exception {
        login(USER_LOGIN, USER_PASSWORD);

        restGlossaryOfPurposesMockMvc
            .perform(get("/api/glossary-of-purposes/{purposeId}/glossary-of-risks", INVALID_PURPOSE_ID))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void createGlossaryOfMeasureUnitsForUnauthorizedTest() throws Exception {

        restGlossaryOfPurposesMockMvc
            .perform(post("/api/glossary-of-purposes/{purposeId}/glossary-of-measure-units", INVALID_PURPOSE_ID)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(new GlossaryOfMeasureUnitsDTO())))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void createGlossaryOfRisksForUnauthorizedTest() throws Exception {
        restGlossaryOfPurposesMockMvc
            .perform(post("/api/glossary-of-purposes/{purposeId}/glossary-of-risks", INVALID_PURPOSE_ID)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(new GlossaryOfRisksDTO())))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void createGlossaryOfMeasureUnitsTest() throws Exception {
        login(USER_LOGIN, USER_PASSWORD);

        GlossaryOfMeasureUnitsDTO glossaryOfMeasureUnitsDTO = new GlossaryOfMeasureUnitsDTO();
        glossaryOfMeasureUnitsDTO.setUnitsOfMeasurement(UNITS_OF_MEASUREMENT);
        glossaryOfMeasureUnitsDTO.setFrequencyOfGatheringData(FREQUENCY_OF_GATHERING_DATA);
        glossaryOfMeasureUnitsDTO.setChecked(Boolean.FALSE);
        glossaryOfMeasureUnitsDTO.setName(NAME);

        restGlossaryOfPurposesMockMvc
            .perform(post("/api/glossary-of-purposes/{purposeId}/glossary-of-measure-units", PURPOSE_ID)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(glossaryOfMeasureUnitsDTO)))
            .andExpect(status().isOk());
    }

    @Test
    public void createGlossaryOfRisksTest() throws Exception {
        login(USER_LOGIN, USER_PASSWORD);

        GlossaryOfRisksDTO glossaryOfRisksDTO = new GlossaryOfRisksDTO();
        glossaryOfRisksDTO.setName(NAME);
        glossaryOfRisksDTO.setChecked(Boolean.TRUE);
        glossaryOfRisksDTO.setCompletionDate(DATE);
        glossaryOfRisksDTO.setImportantTo(DATE);

        restGlossaryOfPurposesMockMvc
            .perform(post("/api/glossary-of-purposes/{purposeId}/glossary-of-risks", PURPOSE_ID)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(glossaryOfRisksDTO)))
            .andExpect(status().isOk());
    }

    @Test
    public void updateGlossaryOfMeasureUnitsIsForbidden() throws Exception {
        login(USER_LOGIN, USER_PASSWORD);

        restGlossaryOfPurposesMockMvc
            .perform(put("/api/glossary-of-purposes/{purposeId}/glossary-of-measure-units", PURPOSE_ID)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(new GlossaryOfMeasureUnits())))
            .andExpect(status().isForbidden());
    }

    @Test
    public void updateGlossaryORisksIsForbidden() throws Exception {
        login(USER_LOGIN, USER_PASSWORD);

        restGlossaryOfPurposesMockMvc
            .perform(put("/api/glossary-of-purposes/{purposeId}/glossary-of-risks", PURPOSE_ID)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(new GlossaryOfRisksDTO())))
            .andExpect(status().isForbidden());
    }

    private void login(String login, String password){
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(login, password);
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
