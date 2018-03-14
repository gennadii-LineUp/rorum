package rog.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import rog.RorumApp;
import rog.domain.RisksPurposes;
import rog.repository.RisksPurposesRepository;
import rog.service.RisksPurposesService;
import rog.web.rest.errors.ExceptionTranslator;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static rog.web.rest.TestUtil.createFormattingConversionService;

/**
 * Test class for the RisksPurposesResource REST controller.
 *
 * @see RisksPurposesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RorumApp.class)
public class RisksPurposesResourceIntTest {

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);

    @Autowired
    private RisksPurposesRepository risksPurposesRepository;

    @Autowired
    private RisksPurposesService risksPurposesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restRisksPurposesMockMvc;

    private RisksPurposes risksPurposes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RisksPurposesResource risksPurposesResource = new RisksPurposesResource();
        this.restRisksPurposesMockMvc = MockMvcBuilders.standaloneSetup(risksPurposesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        risksPurposes = new RisksPurposes();
    }

    @Test
    @Transactional
    public void createRisksPurposes() throws Exception {
        int databaseSizeBeforeCreate = risksPurposesRepository.findAll().size();

        // Create the RisksPurposes
        restRisksPurposesMockMvc.perform(post("/api/risks-purposes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(risksPurposes)))
            .andExpect(status().isCreated());

        // Validate the RisksPurposes in the database
        List<RisksPurposes> risksPurposesList = risksPurposesRepository.findAll();
        assertThat(risksPurposesList).hasSize(databaseSizeBeforeCreate + 1);
    }

    @Test
    @Transactional
    public void createRisksPurposesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = risksPurposesRepository.findAll().size();

        // Create the RisksPurposes with an existing ID
        risksPurposes.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRisksPurposesMockMvc.perform(post("/api/risks-purposes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(risksPurposes)))
            .andExpect(status().isBadRequest());

        // Validate the RisksPurposes in the database
        List<RisksPurposes> risksPurposesList = risksPurposesRepository.findAll();
        assertThat(risksPurposesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRisksPurposes() throws Exception {
        // Initialize the database
        risksPurposesRepository.saveAndFlush(risksPurposes);

        // Get all the risksPurposesList
        restRisksPurposesMockMvc.perform(get("/api/risks-purposes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(risksPurposes.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }

    @Test
    @Transactional
    public void getRisksPurposes() throws Exception {
        // Initialize the database
        risksPurposesRepository.saveAndFlush(risksPurposes);

        // Get the risksPurposes
        restRisksPurposesMockMvc.perform(get("/api/risks-purposes/{id}", risksPurposes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(risksPurposes.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRisksPurposes() throws Exception {
        // Get the risksPurposes
        restRisksPurposesMockMvc.perform(get("/api/risks-purposes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRisksPurposes() throws Exception {
        // Initialize the database
        risksPurposesService.save(risksPurposes);

        int databaseSizeBeforeUpdate = risksPurposesRepository.findAll().size();

        // Update the risksPurposes
        RisksPurposes updatedRisksPurposes = risksPurposesRepository.findOne(risksPurposes.getId());

        restRisksPurposesMockMvc.perform(put("/api/risks-purposes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRisksPurposes)))
            .andExpect(status().isOk());

        // Validate the RisksPurposes in the database
        List<RisksPurposes> risksPurposesList = risksPurposesRepository.findAll();
        assertThat(risksPurposesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void updateNonExistingRisksPurposes() throws Exception {
        int databaseSizeBeforeUpdate = risksPurposesRepository.findAll().size();

        // Create the RisksPurposes

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRisksPurposesMockMvc.perform(put("/api/risks-purposes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(risksPurposes)))
            .andExpect(status().isCreated());

        // Validate the RisksPurposes in the database
        List<RisksPurposes> risksPurposesList = risksPurposesRepository.findAll();
        assertThat(risksPurposesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRisksPurposes() throws Exception {
        // Initialize the database
        risksPurposesService.save(risksPurposes);

        int databaseSizeBeforeDelete = risksPurposesRepository.findAll().size();

        // Get the risksPurposes
        restRisksPurposesMockMvc.perform(delete("/api/risks-purposes/{id}", risksPurposes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RisksPurposes> risksPurposesList = risksPurposesRepository.findAll();
        assertThat(risksPurposesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RisksPurposes.class);
        RisksPurposes risksPurposes1 = new RisksPurposes();
        risksPurposes1.setId(1L);
        RisksPurposes risksPurposes2 = new RisksPurposes();
        risksPurposes2.setId(risksPurposes1.getId());
        assertThat(risksPurposes1).isEqualTo(risksPurposes2);
        risksPurposes2.setId(2L);
        assertThat(risksPurposes1).isNotEqualTo(risksPurposes2);
        risksPurposes1.setId(null);
        assertThat(risksPurposes1).isNotEqualTo(risksPurposes2);
    }
}
