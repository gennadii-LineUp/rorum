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
import rog.domain.CommercialRisksPurposes;
import rog.domain.enumeration.StatusOfSending;
import rog.repository.CommercialRisksPurposesRepository;
import rog.service.CommercialRisksPurposesService;
import rog.web.rest.errors.ExceptionTranslator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static rog.web.rest.TestUtil.createFormattingConversionService;
/**
 * Test class for the CommercialRisksPurposesResource REST controller.
 *
 * @see CommercialRisksPurposesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RorumApp.class)
public class CommercialRisksPurposesResourceIntTest {

    private static final StatusOfSending DEFAULT_STATUS_OF_SENDING = StatusOfSending.CONFIRMED_PURPOSES;
    private static final StatusOfSending UPDATED_STATUS_OF_SENDING = StatusOfSending.REJECTED_PURPOSES;

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NOTATION = "AAAAAAAAAA";
    private static final String UPDATED_NOTATION = "BBBBBBBBBB";

    @Autowired
    private CommercialRisksPurposesRepository commercialRisksPurposesRepository;

    @Autowired
    private CommercialRisksPurposesService commercialRisksPurposesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommercialRisksPurposesMockMvc;

    private CommercialRisksPurposes commercialRisksPurposes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommercialRisksPurposesResource commercialRisksPurposesResource = new CommercialRisksPurposesResource();
        this.restCommercialRisksPurposesMockMvc = MockMvcBuilders.standaloneSetup(commercialRisksPurposesResource)
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
    public static CommercialRisksPurposes createEntity(EntityManager em) {
        CommercialRisksPurposes commercialRisksPurposes = new CommercialRisksPurposes();
        return commercialRisksPurposes;
    }

    @Before
    public void initTest() {
        commercialRisksPurposes = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommercialRisksPurposes() throws Exception {
        int databaseSizeBeforeCreate = commercialRisksPurposesRepository.findAll().size();

        // Create the CommercialRisksPurposes
        restCommercialRisksPurposesMockMvc.perform(post("/api/commercial-risks-purposes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialRisksPurposes)))
            .andExpect(status().isCreated());

        // Validate the CommercialRisksPurposes in the database
        List<CommercialRisksPurposes> commercialRisksPurposesList = commercialRisksPurposesRepository.findAll();
        assertThat(commercialRisksPurposesList).hasSize(databaseSizeBeforeCreate + 1);
    }

    @Test
    @Transactional
    public void createCommercialRisksPurposesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commercialRisksPurposesRepository.findAll().size();

        // Create the CommercialRisksPurposes with an existing ID
        commercialRisksPurposes.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommercialRisksPurposesMockMvc.perform(post("/api/commercial-risks-purposes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialRisksPurposes)))
            .andExpect(status().isBadRequest());

        // Validate the CommercialRisksPurposes in the database
        List<CommercialRisksPurposes> commercialRisksPurposesList = commercialRisksPurposesRepository.findAll();
        assertThat(commercialRisksPurposesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialRisksPurposesRepository.findAll().size();
        // set the field null
        // Create the CommercialRisksPurposes, which fails.

        restCommercialRisksPurposesMockMvc.perform(post("/api/commercial-risks-purposes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialRisksPurposes)))
            .andExpect(status().isBadRequest());

        List<CommercialRisksPurposes> commercialRisksPurposesList = commercialRisksPurposesRepository.findAll();
        assertThat(commercialRisksPurposesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommercialRisksPurposes() throws Exception {
        // Initialize the database
        commercialRisksPurposesRepository.saveAndFlush(commercialRisksPurposes);

        // Get all the commercialRisksPurposesList
        restCommercialRisksPurposesMockMvc.perform(get("/api/commercial-risks-purposes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialRisksPurposes.getId().intValue())))
            .andExpect(jsonPath("$.[*].statusOfSending").value(hasItem(DEFAULT_STATUS_OF_SENDING.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].notation").value(hasItem(DEFAULT_NOTATION.toString())));
    }

    @Test
    @Transactional
    public void getCommercialRisksPurposes() throws Exception {
        // Initialize the database
        commercialRisksPurposesRepository.saveAndFlush(commercialRisksPurposes);

        // Get the commercialRisksPurposes
        restCommercialRisksPurposesMockMvc.perform(get("/api/commercial-risks-purposes/{id}", commercialRisksPurposes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commercialRisksPurposes.getId().intValue()))
            .andExpect(jsonPath("$.statusOfSending").value(DEFAULT_STATUS_OF_SENDING.toString()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.notation").value(DEFAULT_NOTATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCommercialRisksPurposes() throws Exception {
        // Get the commercialRisksPurposes
        restCommercialRisksPurposesMockMvc.perform(get("/api/commercial-risks-purposes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommercialRisksPurposes() throws Exception {
        // Initialize the database
        commercialRisksPurposesService.save(commercialRisksPurposes);

        int databaseSizeBeforeUpdate = commercialRisksPurposesRepository.findAll().size();

        // Update the commercialRisksPurposes
        CommercialRisksPurposes updatedCommercialRisksPurposes = commercialRisksPurposesRepository.findOne(commercialRisksPurposes.getId());

        restCommercialRisksPurposesMockMvc.perform(put("/api/commercial-risks-purposes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommercialRisksPurposes)))
            .andExpect(status().isOk());

        // Validate the CommercialRisksPurposes in the database
        List<CommercialRisksPurposes> commercialRisksPurposesList = commercialRisksPurposesRepository.findAll();
        assertThat(commercialRisksPurposesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void updateNonExistingCommercialRisksPurposes() throws Exception {
        int databaseSizeBeforeUpdate = commercialRisksPurposesRepository.findAll().size();

        // Create the CommercialRisksPurposes

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCommercialRisksPurposesMockMvc.perform(put("/api/commercial-risks-purposes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialRisksPurposes)))
            .andExpect(status().isCreated());

        // Validate the CommercialRisksPurposes in the database
        List<CommercialRisksPurposes> commercialRisksPurposesList = commercialRisksPurposesRepository.findAll();
        assertThat(commercialRisksPurposesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCommercialRisksPurposes() throws Exception {
        // Initialize the database
        commercialRisksPurposesService.save(commercialRisksPurposes);

        int databaseSizeBeforeDelete = commercialRisksPurposesRepository.findAll().size();

        // Get the commercialRisksPurposes
        restCommercialRisksPurposesMockMvc.perform(delete("/api/commercial-risks-purposes/{id}", commercialRisksPurposes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommercialRisksPurposes> commercialRisksPurposesList = commercialRisksPurposesRepository.findAll();
        assertThat(commercialRisksPurposesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommercialRisksPurposes.class);
        CommercialRisksPurposes commercialRisksPurposes1 = new CommercialRisksPurposes();
        commercialRisksPurposes1.setId(1L);
        CommercialRisksPurposes commercialRisksPurposes2 = new CommercialRisksPurposes();
        commercialRisksPurposes2.setId(commercialRisksPurposes1.getId());
        assertThat(commercialRisksPurposes1).isEqualTo(commercialRisksPurposes2);
        commercialRisksPurposes2.setId(2L);
        assertThat(commercialRisksPurposes1).isNotEqualTo(commercialRisksPurposes2);
        commercialRisksPurposes1.setId(null);
        assertThat(commercialRisksPurposes1).isNotEqualTo(commercialRisksPurposes2);
    }
}
