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
import rog.domain.GlossaryOfCommercialRisks;
import rog.repository.GlossaryOfCommercialRisksRepository;
import rog.service.GlossaryOfCommercialRisksService;
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
 * Test class for the GlossaryOfCommercialRisksResource REST controller.
 *
 * @see GlossaryOfCommercialRisksResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RorumApp.class)
public class GlossaryOfCommercialRisksResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_COMPLETION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COMPLETION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_IMPORTANT_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_IMPORTANT_TO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private GlossaryOfCommercialRisksRepository glossaryOfCommercialRisksRepository;

    @Autowired
    private GlossaryOfCommercialRisksService glossaryOfCommercialRisksService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGlossaryOfCommercialRisksMockMvc;

    private GlossaryOfCommercialRisks glossaryOfCommercialRisks;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GlossaryOfCommercialRisksResource glossaryOfCommercialRisksResource = new GlossaryOfCommercialRisksResource();
        this.restGlossaryOfCommercialRisksMockMvc = MockMvcBuilders.standaloneSetup(glossaryOfCommercialRisksResource)
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
    public static GlossaryOfCommercialRisks createEntity(EntityManager em) {
        GlossaryOfCommercialRisks glossaryOfCommercialRisks = new GlossaryOfCommercialRisks()
            .name(DEFAULT_NAME)
            .completionDate(DEFAULT_COMPLETION_DATE)
            .creationDate(DEFAULT_CREATION_DATE)
            .importantTo(DEFAULT_IMPORTANT_TO);
        return glossaryOfCommercialRisks;
    }

    @Before
    public void initTest() {
        glossaryOfCommercialRisks = createEntity(em);
    }

    @Test
    @Transactional
    public void createGlossaryOfCommercialRisks() throws Exception {
        int databaseSizeBeforeCreate = glossaryOfCommercialRisksRepository.findAll().size();

        // Create the GlossaryOfCommercialRisks
        restGlossaryOfCommercialRisksMockMvc.perform(post("/api/glossary-of-commercial-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(glossaryOfCommercialRisks)))
            .andExpect(status().isCreated());

        // Validate the GlossaryOfCommercialRisks in the database
        List<GlossaryOfCommercialRisks> glossaryOfCommercialRisksList = glossaryOfCommercialRisksRepository.findAll();
        assertThat(glossaryOfCommercialRisksList).hasSize(databaseSizeBeforeCreate + 1);
        GlossaryOfCommercialRisks testGlossaryOfCommercialRisks = glossaryOfCommercialRisksList.get(glossaryOfCommercialRisksList.size() - 1);
        assertThat(testGlossaryOfCommercialRisks.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGlossaryOfCommercialRisks.getCompletionDate()).isEqualTo(DEFAULT_COMPLETION_DATE);
        assertThat(testGlossaryOfCommercialRisks.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testGlossaryOfCommercialRisks.getImportantTo()).isEqualTo(DEFAULT_IMPORTANT_TO);
    }

    @Test
    @Transactional
    public void createGlossaryOfCommercialRisksWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = glossaryOfCommercialRisksRepository.findAll().size();

        // Create the GlossaryOfCommercialRisks with an existing ID
        glossaryOfCommercialRisks.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGlossaryOfCommercialRisksMockMvc.perform(post("/api/glossary-of-commercial-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(glossaryOfCommercialRisks)))
            .andExpect(status().isBadRequest());

        // Validate the GlossaryOfCommercialRisks in the database
        List<GlossaryOfCommercialRisks> glossaryOfCommercialRisksList = glossaryOfCommercialRisksRepository.findAll();
        assertThat(glossaryOfCommercialRisksList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = glossaryOfCommercialRisksRepository.findAll().size();
        // set the field null
        glossaryOfCommercialRisks.setName(null);

        // Create the GlossaryOfCommercialRisks, which fails.

        restGlossaryOfCommercialRisksMockMvc.perform(post("/api/glossary-of-commercial-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(glossaryOfCommercialRisks)))
            .andExpect(status().isBadRequest());

        List<GlossaryOfCommercialRisks> glossaryOfCommercialRisksList = glossaryOfCommercialRisksRepository.findAll();
        assertThat(glossaryOfCommercialRisksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGlossaryOfCommercialRisks() throws Exception {
        // Initialize the database
        glossaryOfCommercialRisksRepository.saveAndFlush(glossaryOfCommercialRisks);

        // Get all the glossaryOfCommercialRisksList
        restGlossaryOfCommercialRisksMockMvc.perform(get("/api/glossary-of-commercial-risks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(glossaryOfCommercialRisks.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].completionDate").value(hasItem(DEFAULT_COMPLETION_DATE.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].importantTo").value(hasItem(DEFAULT_IMPORTANT_TO.toString())));
    }

    @Test
    @Transactional
    public void getGlossaryOfCommercialRisks() throws Exception {
        // Initialize the database
        glossaryOfCommercialRisksRepository.saveAndFlush(glossaryOfCommercialRisks);

        // Get the glossaryOfCommercialRisks
        restGlossaryOfCommercialRisksMockMvc.perform(get("/api/glossary-of-commercial-risks/{id}", glossaryOfCommercialRisks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(glossaryOfCommercialRisks.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.completionDate").value(DEFAULT_COMPLETION_DATE.toString()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.importantTo").value(DEFAULT_IMPORTANT_TO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGlossaryOfCommercialRisks() throws Exception {
        // Get the glossaryOfCommercialRisks
        restGlossaryOfCommercialRisksMockMvc.perform(get("/api/glossary-of-commercial-risks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGlossaryOfCommercialRisks() throws Exception {
        // Initialize the database
        glossaryOfCommercialRisksService.save(glossaryOfCommercialRisks);

        int databaseSizeBeforeUpdate = glossaryOfCommercialRisksRepository.findAll().size();

        // Update the glossaryOfCommercialRisks
        GlossaryOfCommercialRisks updatedGlossaryOfCommercialRisks = glossaryOfCommercialRisksRepository.findOne(glossaryOfCommercialRisks.getId());
        updatedGlossaryOfCommercialRisks
            .name(UPDATED_NAME)
            .completionDate(UPDATED_COMPLETION_DATE)
            .creationDate(UPDATED_CREATION_DATE)
            .importantTo(UPDATED_IMPORTANT_TO);

        restGlossaryOfCommercialRisksMockMvc.perform(put("/api/glossary-of-commercial-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGlossaryOfCommercialRisks)))
            .andExpect(status().isOk());

        // Validate the GlossaryOfCommercialRisks in the database
        List<GlossaryOfCommercialRisks> glossaryOfCommercialRisksList = glossaryOfCommercialRisksRepository.findAll();
        assertThat(glossaryOfCommercialRisksList).hasSize(databaseSizeBeforeUpdate);
        GlossaryOfCommercialRisks testGlossaryOfCommercialRisks = glossaryOfCommercialRisksList.get(glossaryOfCommercialRisksList.size() - 1);
        assertThat(testGlossaryOfCommercialRisks.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGlossaryOfCommercialRisks.getCompletionDate()).isEqualTo(UPDATED_COMPLETION_DATE);
        assertThat(testGlossaryOfCommercialRisks.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testGlossaryOfCommercialRisks.getImportantTo()).isEqualTo(UPDATED_IMPORTANT_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingGlossaryOfCommercialRisks() throws Exception {
        int databaseSizeBeforeUpdate = glossaryOfCommercialRisksRepository.findAll().size();

        // Create the GlossaryOfCommercialRisks

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGlossaryOfCommercialRisksMockMvc.perform(put("/api/glossary-of-commercial-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(glossaryOfCommercialRisks)))
            .andExpect(status().isCreated());

        // Validate the GlossaryOfCommercialRisks in the database
        List<GlossaryOfCommercialRisks> glossaryOfCommercialRisksList = glossaryOfCommercialRisksRepository.findAll();
        assertThat(glossaryOfCommercialRisksList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGlossaryOfCommercialRisks() throws Exception {
        // Initialize the database
        glossaryOfCommercialRisksService.save(glossaryOfCommercialRisks);

        int databaseSizeBeforeDelete = glossaryOfCommercialRisksRepository.findAll().size();

        // Get the glossaryOfCommercialRisks
        restGlossaryOfCommercialRisksMockMvc.perform(delete("/api/glossary-of-commercial-risks/{id}", glossaryOfCommercialRisks.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GlossaryOfCommercialRisks> glossaryOfCommercialRisksList = glossaryOfCommercialRisksRepository.findAll();
        assertThat(glossaryOfCommercialRisksList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GlossaryOfCommercialRisks.class);
        GlossaryOfCommercialRisks glossaryOfCommercialRisks1 = new GlossaryOfCommercialRisks();
        glossaryOfCommercialRisks1.setId(1L);
        GlossaryOfCommercialRisks glossaryOfCommercialRisks2 = new GlossaryOfCommercialRisks();
        glossaryOfCommercialRisks2.setId(glossaryOfCommercialRisks1.getId());
        assertThat(glossaryOfCommercialRisks1).isEqualTo(glossaryOfCommercialRisks2);
        glossaryOfCommercialRisks2.setId(2L);
        assertThat(glossaryOfCommercialRisks1).isNotEqualTo(glossaryOfCommercialRisks2);
        glossaryOfCommercialRisks1.setId(null);
        assertThat(glossaryOfCommercialRisks1).isNotEqualTo(glossaryOfCommercialRisks2);
    }
}
