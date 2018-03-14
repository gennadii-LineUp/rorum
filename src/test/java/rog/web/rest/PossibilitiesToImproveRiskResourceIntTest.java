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
import rog.domain.PossibilitiesToImproveRisk;
import rog.repository.PossibilitiesToImproveRiskRepository;
import rog.service.PossibilitiesToImproveRiskService;
import rog.web.rest.errors.ExceptionTranslator;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static rog.web.rest.TestUtil.createFormattingConversionService;

/**
 * Test class for the PossibilitiesToImproveRiskResource REST controller.
 *
 * @see PossibilitiesToImproveRiskResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RorumApp.class)
public class PossibilitiesToImproveRiskResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private PossibilitiesToImproveRiskRepository possibilitiesToImproveRiskRepository;

    @Autowired
    private PossibilitiesToImproveRiskService possibilitiesToImproveRiskService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPossibilitiesToImproveRiskMockMvc;

    private PossibilitiesToImproveRisk possibilitiesToImproveRisk;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PossibilitiesToImproveRiskResource possibilitiesToImproveRiskResource = new PossibilitiesToImproveRiskResource(possibilitiesToImproveRiskService);
        this.restPossibilitiesToImproveRiskMockMvc = MockMvcBuilders.standaloneSetup(possibilitiesToImproveRiskResource)
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
    public static PossibilitiesToImproveRisk createEntity(EntityManager em) {
        PossibilitiesToImproveRisk possibilitiesToImproveRisk = new PossibilitiesToImproveRisk()
            .name(DEFAULT_NAME);
        return possibilitiesToImproveRisk;
    }

    @Before
    public void initTest() {
        possibilitiesToImproveRisk = createEntity(em);
    }

    @Test
    @Transactional
    public void createPossibilitiesToImproveRisk() throws Exception {
        int databaseSizeBeforeCreate = possibilitiesToImproveRiskRepository.findAll().size();

        // Create the PossibilitiesToImproveRisk
        restPossibilitiesToImproveRiskMockMvc.perform(post("/api/possibilities-to-improve-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(possibilitiesToImproveRisk)))
            .andExpect(status().isCreated());

        // Validate the PossibilitiesToImproveRisk in the database
        List<PossibilitiesToImproveRisk> possibilitiesToImproveRiskList = possibilitiesToImproveRiskRepository.findAll();
        assertThat(possibilitiesToImproveRiskList).hasSize(databaseSizeBeforeCreate + 1);
        PossibilitiesToImproveRisk testPossibilitiesToImproveRisk = possibilitiesToImproveRiskList.get(possibilitiesToImproveRiskList.size() - 1);
        assertThat(testPossibilitiesToImproveRisk.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createPossibilitiesToImproveRiskWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = possibilitiesToImproveRiskRepository.findAll().size();

        // Create the PossibilitiesToImproveRisk with an existing ID
        possibilitiesToImproveRisk.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPossibilitiesToImproveRiskMockMvc.perform(post("/api/possibilities-to-improve-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(possibilitiesToImproveRisk)))
            .andExpect(status().isBadRequest());

        // Validate the PossibilitiesToImproveRisk in the database
        List<PossibilitiesToImproveRisk> possibilitiesToImproveRiskList = possibilitiesToImproveRiskRepository.findAll();
        assertThat(possibilitiesToImproveRiskList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = possibilitiesToImproveRiskRepository.findAll().size();
        // set the field null
        possibilitiesToImproveRisk.setName(null);

        // Create the PossibilitiesToImproveRisk, which fails.

        restPossibilitiesToImproveRiskMockMvc.perform(post("/api/possibilities-to-improve-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(possibilitiesToImproveRisk)))
            .andExpect(status().isBadRequest());

        List<PossibilitiesToImproveRisk> possibilitiesToImproveRiskList = possibilitiesToImproveRiskRepository.findAll();
        assertThat(possibilitiesToImproveRiskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPossibilitiesToImproveRisks() throws Exception {
        // Initialize the database
        possibilitiesToImproveRiskRepository.saveAndFlush(possibilitiesToImproveRisk);

        // Get all the possibilitiesToImproveRiskList
        restPossibilitiesToImproveRiskMockMvc.perform(get("/api/possibilities-to-improve-risks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(possibilitiesToImproveRisk.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getPossibilitiesToImproveRisk() throws Exception {
        // Initialize the database
        possibilitiesToImproveRiskRepository.saveAndFlush(possibilitiesToImproveRisk);

        // Get the possibilitiesToImproveRisk
        restPossibilitiesToImproveRiskMockMvc.perform(get("/api/possibilities-to-improve-risks/{id}", possibilitiesToImproveRisk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(possibilitiesToImproveRisk.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPossibilitiesToImproveRisk() throws Exception {
        // Get the possibilitiesToImproveRisk
        restPossibilitiesToImproveRiskMockMvc.perform(get("/api/possibilities-to-improve-risks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePossibilitiesToImproveRisk() throws Exception {
        // Initialize the database
        possibilitiesToImproveRiskService.save(possibilitiesToImproveRisk);

        int databaseSizeBeforeUpdate = possibilitiesToImproveRiskRepository.findAll().size();

        // Update the possibilitiesToImproveRisk
        PossibilitiesToImproveRisk updatedPossibilitiesToImproveRisk = possibilitiesToImproveRiskRepository.findOne(possibilitiesToImproveRisk.getId());
        updatedPossibilitiesToImproveRisk
            .name(UPDATED_NAME);

        restPossibilitiesToImproveRiskMockMvc.perform(put("/api/possibilities-to-improve-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPossibilitiesToImproveRisk)))
            .andExpect(status().isOk());

        // Validate the PossibilitiesToImproveRisk in the database
        List<PossibilitiesToImproveRisk> possibilitiesToImproveRiskList = possibilitiesToImproveRiskRepository.findAll();
        assertThat(possibilitiesToImproveRiskList).hasSize(databaseSizeBeforeUpdate);
        PossibilitiesToImproveRisk testPossibilitiesToImproveRisk = possibilitiesToImproveRiskList.get(possibilitiesToImproveRiskList.size() - 1);
        assertThat(testPossibilitiesToImproveRisk.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPossibilitiesToImproveRisk() throws Exception {
        int databaseSizeBeforeUpdate = possibilitiesToImproveRiskRepository.findAll().size();

        // Create the PossibilitiesToImproveRisk

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPossibilitiesToImproveRiskMockMvc.perform(put("/api/possibilities-to-improve-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(possibilitiesToImproveRisk)))
            .andExpect(status().isCreated());

        // Validate the PossibilitiesToImproveRisk in the database
        List<PossibilitiesToImproveRisk> possibilitiesToImproveRiskList = possibilitiesToImproveRiskRepository.findAll();
        assertThat(possibilitiesToImproveRiskList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePossibilitiesToImproveRisk() throws Exception {
        // Initialize the database
        possibilitiesToImproveRiskService.save(possibilitiesToImproveRisk);

        int databaseSizeBeforeDelete = possibilitiesToImproveRiskRepository.findAll().size();

        // Get the possibilitiesToImproveRisk
        restPossibilitiesToImproveRiskMockMvc.perform(delete("/api/possibilities-to-improve-risks/{id}", possibilitiesToImproveRisk.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PossibilitiesToImproveRisk> possibilitiesToImproveRiskList = possibilitiesToImproveRiskRepository.findAll();
        assertThat(possibilitiesToImproveRiskList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PossibilitiesToImproveRisk.class);
        PossibilitiesToImproveRisk possibilitiesToImproveRisk1 = new PossibilitiesToImproveRisk();
        possibilitiesToImproveRisk1.setId(1L);
        PossibilitiesToImproveRisk possibilitiesToImproveRisk2 = new PossibilitiesToImproveRisk();
        possibilitiesToImproveRisk2.setId(possibilitiesToImproveRisk1.getId());
        assertThat(possibilitiesToImproveRisk1).isEqualTo(possibilitiesToImproveRisk2);
        possibilitiesToImproveRisk2.setId(2L);
        assertThat(possibilitiesToImproveRisk1).isNotEqualTo(possibilitiesToImproveRisk2);
        possibilitiesToImproveRisk1.setId(null);
        assertThat(possibilitiesToImproveRisk1).isNotEqualTo(possibilitiesToImproveRisk2);
    }
}
