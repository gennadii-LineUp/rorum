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
import rog.domain.FilledRisks;
import rog.repository.FilledRisksRepository;
import rog.service.FilledRisksService;
import rog.web.rest.errors.ExceptionTranslator;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static rog.web.rest.TestUtil.createFormattingConversionService;

/**
 * Test class for the FilledRisksResource REST controller.
 *
 * @see FilledRisksResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RorumApp.class)
public class FilledRisksResourceIntTest {

    private static final Integer DEFAULT_PROBABILITY = 1;
    private static final Integer UPDATED_PROBABILITY = 2;

    private static final Integer DEFAULT_POWER_OF_INFLUENCE = 1;
    private static final Integer UPDATED_POWER_OF_INFLUENCE = 2;

    private static final Integer DEFAULT_CALCULATED_VALUE = 1;
    private static final Integer UPDATED_CALCULATED_VALUE = 2;

    @Autowired
    private FilledRisksRepository filledRisksRepository;

    @Autowired
    private FilledRisksService filledRisksService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFilledRisksMockMvc;

    private FilledRisks filledRisks;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FilledRisksResource filledRisksResource = new FilledRisksResource();
        this.restFilledRisksMockMvc = MockMvcBuilders.standaloneSetup(filledRisksResource)
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
    public static FilledRisks createEntity(EntityManager em) {
        FilledRisks filledRisks = new FilledRisks()
            .probability(DEFAULT_PROBABILITY)
            .powerOfInfluence(DEFAULT_POWER_OF_INFLUENCE);
        return filledRisks;
    }

    @Before
    public void initTest() {
        filledRisks = createEntity(em);
    }

    @Test
    @Transactional
    public void createFilledRisks() throws Exception {
        int databaseSizeBeforeCreate = filledRisksRepository.findAll().size();

        // Create the FilledRisks
        restFilledRisksMockMvc.perform(post("/api/filled-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filledRisks)))
            .andExpect(status().isCreated());

        // Validate the FilledRisks in the database
        List<FilledRisks> filledRisksList = filledRisksRepository.findAll();
        assertThat(filledRisksList).hasSize(databaseSizeBeforeCreate + 1);
        FilledRisks testFilledRisks = filledRisksList.get(filledRisksList.size() - 1);
        assertThat(testFilledRisks.getProbability()).isEqualTo(DEFAULT_PROBABILITY);
        assertThat(testFilledRisks.getPowerOfInfluence()).isEqualTo(DEFAULT_POWER_OF_INFLUENCE);
    }

    @Test
    @Transactional
    public void createFilledRisksWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = filledRisksRepository.findAll().size();

        // Create the FilledRisks with an existing ID
        filledRisks.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFilledRisksMockMvc.perform(post("/api/filled-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filledRisks)))
            .andExpect(status().isBadRequest());

        // Validate the FilledRisks in the database
        List<FilledRisks> filledRisksList = filledRisksRepository.findAll();
        assertThat(filledRisksList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFilledRisks() throws Exception {
        // Initialize the database
        filledRisksRepository.saveAndFlush(filledRisks);

        // Get all the filledRisksList
        restFilledRisksMockMvc.perform(get("/api/filled-risks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(filledRisks.getId().intValue())))
            .andExpect(jsonPath("$.[*].probability").value(hasItem(DEFAULT_PROBABILITY)))
            .andExpect(jsonPath("$.[*].powerOfInfluence").value(hasItem(DEFAULT_POWER_OF_INFLUENCE)))
            .andExpect(jsonPath("$.[*].calculatedValue").value(hasItem(DEFAULT_CALCULATED_VALUE)));
    }

    @Test
    @Transactional
    public void getFilledRisks() throws Exception {
        // Initialize the database
        filledRisksRepository.saveAndFlush(filledRisks);

        // Get the filledRisks
        restFilledRisksMockMvc.perform(get("/api/filled-risks/{id}", filledRisks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(filledRisks.getId().intValue()))
            .andExpect(jsonPath("$.probability").value(DEFAULT_PROBABILITY))
            .andExpect(jsonPath("$.powerOfInfluence").value(DEFAULT_POWER_OF_INFLUENCE))
            .andExpect(jsonPath("$.calculatedValue").value(DEFAULT_CALCULATED_VALUE));
    }

    @Test
    @Transactional
    public void getNonExistingFilledRisks() throws Exception {
        // Get the filledRisks
        restFilledRisksMockMvc.perform(get("/api/filled-risks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFilledRisks() throws Exception {
        // Initialize the database
        filledRisksService.save(filledRisks);

        int databaseSizeBeforeUpdate = filledRisksRepository.findAll().size();

        // Update the filledRisks
        FilledRisks updatedFilledRisks = filledRisksRepository.findOne(filledRisks.getId());
        updatedFilledRisks
            .probability(UPDATED_PROBABILITY)
            .powerOfInfluence(UPDATED_POWER_OF_INFLUENCE);

        restFilledRisksMockMvc.perform(put("/api/filled-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFilledRisks)))
            .andExpect(status().isOk());

        // Validate the FilledRisks in the database
        List<FilledRisks> filledRisksList = filledRisksRepository.findAll();
        assertThat(filledRisksList).hasSize(databaseSizeBeforeUpdate);
        FilledRisks testFilledRisks = filledRisksList.get(filledRisksList.size() - 1);
        assertThat(testFilledRisks.getProbability()).isEqualTo(UPDATED_PROBABILITY);
        assertThat(testFilledRisks.getPowerOfInfluence()).isEqualTo(UPDATED_POWER_OF_INFLUENCE);
    }

    @Test
    @Transactional
    public void updateNonExistingFilledRisks() throws Exception {
        int databaseSizeBeforeUpdate = filledRisksRepository.findAll().size();

        // Create the FilledRisks

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFilledRisksMockMvc.perform(put("/api/filled-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filledRisks)))
            .andExpect(status().isCreated());

        // Validate the FilledRisks in the database
        List<FilledRisks> filledRisksList = filledRisksRepository.findAll();
        assertThat(filledRisksList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFilledRisks() throws Exception {
        // Initialize the database
        filledRisksService.save(filledRisks);

        int databaseSizeBeforeDelete = filledRisksRepository.findAll().size();

        // Get the filledRisks
        restFilledRisksMockMvc.perform(delete("/api/filled-risks/{id}", filledRisks.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FilledRisks> filledRisksList = filledRisksRepository.findAll();
        assertThat(filledRisksList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FilledRisks.class);
        FilledRisks filledRisks1 = new FilledRisks();
        filledRisks1.setId(1L);
        FilledRisks filledRisks2 = new FilledRisks();
        filledRisks2.setId(filledRisks1.getId());
        assertThat(filledRisks1).isEqualTo(filledRisks2);
        filledRisks2.setId(2L);
        assertThat(filledRisks1).isNotEqualTo(filledRisks2);
        filledRisks1.setId(null);
        assertThat(filledRisks1).isNotEqualTo(filledRisks2);
    }
}
