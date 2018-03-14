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
import rog.domain.DecisionForRisk;
import rog.repository.DecisionForRiskRepository;
import rog.service.DecisionForRiskService;
import rog.web.rest.errors.ExceptionTranslator;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static rog.web.rest.TestUtil.createFormattingConversionService;

/**
 * Test class for the DecisionForRiskResource REST controller.
 *
 * @see DecisionForRiskResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RorumApp.class)
public class DecisionForRiskResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DecisionForRiskRepository decisionForRiskRepository;

    @Autowired
    private DecisionForRiskService decisionForRiskService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDecisionForRiskMockMvc;

    private DecisionForRisk decisionForRisk;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DecisionForRiskResource decisionForRiskResource = new DecisionForRiskResource();
        this.restDecisionForRiskMockMvc = MockMvcBuilders.standaloneSetup(decisionForRiskResource)
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
    public static DecisionForRisk createEntity(EntityManager em) {
        DecisionForRisk decisionForRisk = new DecisionForRisk()
            .name(DEFAULT_NAME);
        return decisionForRisk;
    }

    @Before
    public void initTest() {
        decisionForRisk = createEntity(em);
    }

    @Test
    @Transactional
    public void createDecisionForRisk() throws Exception {
        int databaseSizeBeforeCreate = decisionForRiskRepository.findAll().size();

        // Create the DecisionForRisk
        restDecisionForRiskMockMvc.perform(post("/api/decision-for-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(decisionForRisk)))
            .andExpect(status().isCreated());

        // Validate the DecisionForRisk in the database
        List<DecisionForRisk> decisionForRiskList = decisionForRiskRepository.findAll();
        assertThat(decisionForRiskList).hasSize(databaseSizeBeforeCreate + 1);
        DecisionForRisk testDecisionForRisk = decisionForRiskList.get(decisionForRiskList.size() - 1);
        assertThat(testDecisionForRisk.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createDecisionForRiskWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = decisionForRiskRepository.findAll().size();

        // Create the DecisionForRisk with an existing ID
        decisionForRisk.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDecisionForRiskMockMvc.perform(post("/api/decision-for-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(decisionForRisk)))
            .andExpect(status().isBadRequest());

        // Validate the DecisionForRisk in the database
        List<DecisionForRisk> decisionForRiskList = decisionForRiskRepository.findAll();
        assertThat(decisionForRiskList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = decisionForRiskRepository.findAll().size();
        // set the field null
        decisionForRisk.setName(null);

        // Create the DecisionForRisk, which fails.

        restDecisionForRiskMockMvc.perform(post("/api/decision-for-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(decisionForRisk)))
            .andExpect(status().isBadRequest());

        List<DecisionForRisk> decisionForRiskList = decisionForRiskRepository.findAll();
        assertThat(decisionForRiskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDecisionForRisks() throws Exception {
        // Initialize the database
        decisionForRiskRepository.saveAndFlush(decisionForRisk);

        // Get all the decisionForRiskList
        restDecisionForRiskMockMvc.perform(get("/api/decision-for-risks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(decisionForRisk.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getDecisionForRisk() throws Exception {
        // Initialize the database
        decisionForRiskRepository.saveAndFlush(decisionForRisk);

        // Get the decisionForRisk
        restDecisionForRiskMockMvc.perform(get("/api/decision-for-risks/{id}", decisionForRisk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(decisionForRisk.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDecisionForRisk() throws Exception {
        // Get the decisionForRisk
        restDecisionForRiskMockMvc.perform(get("/api/decision-for-risks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDecisionForRisk() throws Exception {
        // Initialize the database
        decisionForRiskService.save(decisionForRisk);

        int databaseSizeBeforeUpdate = decisionForRiskRepository.findAll().size();

        // Update the decisionForRisk
        DecisionForRisk updatedDecisionForRisk = decisionForRiskRepository.findOne(decisionForRisk.getId());
        updatedDecisionForRisk
            .name(UPDATED_NAME);

        restDecisionForRiskMockMvc.perform(put("/api/decision-for-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDecisionForRisk)))
            .andExpect(status().isOk());

        // Validate the DecisionForRisk in the database
        List<DecisionForRisk> decisionForRiskList = decisionForRiskRepository.findAll();
        assertThat(decisionForRiskList).hasSize(databaseSizeBeforeUpdate);
        DecisionForRisk testDecisionForRisk = decisionForRiskList.get(decisionForRiskList.size() - 1);
        assertThat(testDecisionForRisk.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDecisionForRisk() throws Exception {
        int databaseSizeBeforeUpdate = decisionForRiskRepository.findAll().size();

        // Create the DecisionForRisk

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDecisionForRiskMockMvc.perform(put("/api/decision-for-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(decisionForRisk)))
            .andExpect(status().isCreated());

        // Validate the DecisionForRisk in the database
        List<DecisionForRisk> decisionForRiskList = decisionForRiskRepository.findAll();
        assertThat(decisionForRiskList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDecisionForRisk() throws Exception {
        // Initialize the database
        decisionForRiskService.save(decisionForRisk);

        int databaseSizeBeforeDelete = decisionForRiskRepository.findAll().size();

        // Get the decisionForRisk
        restDecisionForRiskMockMvc.perform(delete("/api/decision-for-risks/{id}", decisionForRisk.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DecisionForRisk> decisionForRiskList = decisionForRiskRepository.findAll();
        assertThat(decisionForRiskList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DecisionForRisk.class);
        DecisionForRisk decisionForRisk1 = new DecisionForRisk();
        decisionForRisk1.setId(1L);
        DecisionForRisk decisionForRisk2 = new DecisionForRisk();
        decisionForRisk2.setId(decisionForRisk1.getId());
        assertThat(decisionForRisk1).isEqualTo(decisionForRisk2);
        decisionForRisk2.setId(2L);
        assertThat(decisionForRisk1).isNotEqualTo(decisionForRisk2);
        decisionForRisk1.setId(null);
        assertThat(decisionForRisk1).isNotEqualTo(decisionForRisk2);
    }
}
