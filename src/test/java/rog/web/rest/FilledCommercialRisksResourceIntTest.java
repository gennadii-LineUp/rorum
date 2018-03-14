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
import rog.domain.FilledCommercialRisks;
import rog.repository.FilledCommercialRisksRepository;
import rog.service.FilledCommercialRisksService;
import rog.web.rest.errors.ExceptionTranslator;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static rog.web.rest.TestUtil.createFormattingConversionService;

/**
 * Test class for the FilledCommercialRisksResource REST controller.
 *
 * @see FilledCommercialRisksResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RorumApp.class)
public class FilledCommercialRisksResourceIntTest {

    private static final Integer DEFAULT_PROBABILITY = 1;
    private static final Integer UPDATED_PROBABILITY = 2;

    private static final Integer DEFAULT_POWER_OF_INFLUENCE = 1;
    private static final Integer UPDATED_POWER_OF_INFLUENCE = 2;

    private static final Integer DEFAULT_CALCULATED_VALUE = 1;
    private static final Integer UPDATED_CALCULATED_VALUE = 2;

    @Autowired
    private FilledCommercialRisksRepository filledCommercialRisksRepository;

    @Autowired
    private FilledCommercialRisksService filledCommercialRisksService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFilledCommercialRisksMockMvc;

    private FilledCommercialRisks filledCommercialRisks;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FilledCommercialRisksResource filledCommercialRisksResource = new FilledCommercialRisksResource();
        this.restFilledCommercialRisksMockMvc = MockMvcBuilders.standaloneSetup(filledCommercialRisksResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FilledCommercialRisks createEntity(EntityManager em) {
        FilledCommercialRisks filledCommercialRisks = new FilledCommercialRisks()
            .probability(DEFAULT_PROBABILITY)
            .powerOfInfluence(DEFAULT_POWER_OF_INFLUENCE);
        return filledCommercialRisks;
    }

    @Before
    public void initTest() {
        filledCommercialRisks = createEntity(em);
    }

    @Test
    @Transactional
    public void createFilledCommercialRisks() throws Exception {
        int databaseSizeBeforeCreate = filledCommercialRisksRepository.findAll().size();

        // Create the FilledCommercialRisks
        restFilledCommercialRisksMockMvc.perform(post("/api/filled-commercial-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filledCommercialRisks)))
            .andExpect(status().isCreated());

        // Validate the FilledCommercialRisks in the database
        List<FilledCommercialRisks> filledCommercialRisksList = filledCommercialRisksRepository.findAll();
        assertThat(filledCommercialRisksList).hasSize(databaseSizeBeforeCreate + 1);
        FilledCommercialRisks testFilledCommercialRisks = filledCommercialRisksList.get(filledCommercialRisksList.size() - 1);
        assertThat(testFilledCommercialRisks.getProbability()).isEqualTo(DEFAULT_PROBABILITY);
        assertThat(testFilledCommercialRisks.getPowerOfInfluence()).isEqualTo(DEFAULT_POWER_OF_INFLUENCE);
    }

    @Test
    @Transactional
    public void createFilledCommercialRisksWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = filledCommercialRisksRepository.findAll().size();

        // Create the FilledCommercialRisks with an existing ID
        filledCommercialRisks.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFilledCommercialRisksMockMvc.perform(post("/api/filled-commercial-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filledCommercialRisks)))
            .andExpect(status().isBadRequest());

        // Validate the FilledCommercialRisks in the database
        List<FilledCommercialRisks> filledCommercialRisksList = filledCommercialRisksRepository.findAll();
        assertThat(filledCommercialRisksList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkProbabilityIsRequired() throws Exception {
        int databaseSizeBeforeTest = filledCommercialRisksRepository.findAll().size();
        // set the field null
        filledCommercialRisks.setProbability(null);

        // Create the FilledCommercialRisks, which fails.

        restFilledCommercialRisksMockMvc.perform(post("/api/filled-commercial-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filledCommercialRisks)))
            .andExpect(status().isBadRequest());

        List<FilledCommercialRisks> filledCommercialRisksList = filledCommercialRisksRepository.findAll();
        assertThat(filledCommercialRisksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPowerOfInfluenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = filledCommercialRisksRepository.findAll().size();
        // set the field null
        filledCommercialRisks.setPowerOfInfluence(null);

        // Create the FilledCommercialRisks, which fails.

        restFilledCommercialRisksMockMvc.perform(post("/api/filled-commercial-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filledCommercialRisks)))
            .andExpect(status().isBadRequest());

        List<FilledCommercialRisks> filledCommercialRisksList = filledCommercialRisksRepository.findAll();
        assertThat(filledCommercialRisksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFilledCommercialRisks() throws Exception {
        // Initialize the database
        filledCommercialRisksRepository.saveAndFlush(filledCommercialRisks);

        // Get all the filledCommercialRisksList
        restFilledCommercialRisksMockMvc.perform(get("/api/filled-commercial-risks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(filledCommercialRisks.getId().intValue())))
            .andExpect(jsonPath("$.[*].probability").value(hasItem(DEFAULT_PROBABILITY)))
            .andExpect(jsonPath("$.[*].powerOfInfluence").value(hasItem(DEFAULT_POWER_OF_INFLUENCE)))
            .andExpect(jsonPath("$.[*].calculatedValue").value(hasItem(DEFAULT_CALCULATED_VALUE)));
    }

    @Test
    @Transactional
    public void getFilledCommercialRisks() throws Exception {
        // Initialize the database
        filledCommercialRisksRepository.saveAndFlush(filledCommercialRisks);

        // Get the filledCommercialRisks
        restFilledCommercialRisksMockMvc.perform(get("/api/filled-commercial-risks/{id}", filledCommercialRisks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(filledCommercialRisks.getId().intValue()))
            .andExpect(jsonPath("$.probability").value(DEFAULT_PROBABILITY))
            .andExpect(jsonPath("$.powerOfInfluence").value(DEFAULT_POWER_OF_INFLUENCE))
            .andExpect(jsonPath("$.calculatedValue").value(DEFAULT_CALCULATED_VALUE));
    }

    @Test
    @Transactional
    public void getNonExistingFilledCommercialRisks() throws Exception {
        // Get the filledCommercialRisks
        restFilledCommercialRisksMockMvc.perform(get("/api/filled-commercial-risks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFilledCommercialRisks() throws Exception {
        // Initialize the database
        filledCommercialRisksService.save(filledCommercialRisks);

        int databaseSizeBeforeUpdate = filledCommercialRisksRepository.findAll().size();

        // Update the filledCommercialRisks
        FilledCommercialRisks updatedFilledCommercialRisks = filledCommercialRisksRepository.findOne(filledCommercialRisks.getId());
        updatedFilledCommercialRisks
            .probability(UPDATED_PROBABILITY)
            .powerOfInfluence(UPDATED_POWER_OF_INFLUENCE);

        restFilledCommercialRisksMockMvc.perform(put("/api/filled-commercial-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFilledCommercialRisks)))
            .andExpect(status().isOk());

        // Validate the FilledCommercialRisks in the database
        List<FilledCommercialRisks> filledCommercialRisksList = filledCommercialRisksRepository.findAll();
        assertThat(filledCommercialRisksList).hasSize(databaseSizeBeforeUpdate);
        FilledCommercialRisks testFilledCommercialRisks = filledCommercialRisksList.get(filledCommercialRisksList.size() - 1);
        assertThat(testFilledCommercialRisks.getProbability()).isEqualTo(UPDATED_PROBABILITY);
        assertThat(testFilledCommercialRisks.getPowerOfInfluence()).isEqualTo(UPDATED_POWER_OF_INFLUENCE);
    }

    @Test
    @Transactional
    public void updateNonExistingFilledCommercialRisks() throws Exception {
        int databaseSizeBeforeUpdate = filledCommercialRisksRepository.findAll().size();

        // Create the FilledCommercialRisks

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFilledCommercialRisksMockMvc.perform(put("/api/filled-commercial-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filledCommercialRisks)))
            .andExpect(status().isCreated());

        // Validate the FilledCommercialRisks in the database
        List<FilledCommercialRisks> filledCommercialRisksList = filledCommercialRisksRepository.findAll();
        assertThat(filledCommercialRisksList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFilledCommercialRisks() throws Exception {
        // Initialize the database
        filledCommercialRisksService.save(filledCommercialRisks);

        int databaseSizeBeforeDelete = filledCommercialRisksRepository.findAll().size();

        // Get the filledCommercialRisks
        restFilledCommercialRisksMockMvc.perform(delete("/api/filled-commercial-risks/{id}", filledCommercialRisks.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FilledCommercialRisks> filledCommercialRisksList = filledCommercialRisksRepository.findAll();
        assertThat(filledCommercialRisksList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FilledCommercialRisks.class);
        FilledCommercialRisks filledCommercialRisks1 = new FilledCommercialRisks();
        filledCommercialRisks1.setId(1L);
        FilledCommercialRisks filledCommercialRisks2 = new FilledCommercialRisks();
        filledCommercialRisks2.setId(filledCommercialRisks1.getId());
        assertThat(filledCommercialRisks1).isEqualTo(filledCommercialRisks2);
        filledCommercialRisks2.setId(2L);
        assertThat(filledCommercialRisks1).isNotEqualTo(filledCommercialRisks2);
        filledCommercialRisks1.setId(null);
        assertThat(filledCommercialRisks1).isNotEqualTo(filledCommercialRisks2);
    }
}
