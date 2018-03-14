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
import rog.domain.GlossaryOfRisks;
import rog.repository.GlossaryOfRisksRepository;
import rog.service.GlossaryOfRisksService;
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
 * Test class for the GlossaryOfRisksResource REST controller.
 *
 * @see GlossaryOfRisksResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RorumApp.class)
public class GlossaryOfRisksResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_COMPLETION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COMPLETION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_IMPORTANT_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_IMPORTANT_TO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private GlossaryOfRisksRepository glossaryOfRisksRepository;

    @Autowired
    private GlossaryOfRisksService glossaryOfRisksService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGlossaryOfRisksMockMvc;

    private GlossaryOfRisks glossaryOfRisks;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GlossaryOfRisksResource glossaryOfRisksResource = new GlossaryOfRisksResource();
        this.restGlossaryOfRisksMockMvc = MockMvcBuilders.standaloneSetup(glossaryOfRisksResource)
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
    public static GlossaryOfRisks createEntity(EntityManager em) {
        GlossaryOfRisks glossaryOfRisks = new GlossaryOfRisks()
            .name(DEFAULT_NAME)
            .completionDate(DEFAULT_COMPLETION_DATE)
            .creationDate(DEFAULT_CREATION_DATE)
            .importantTo(DEFAULT_IMPORTANT_TO);
        return glossaryOfRisks;
    }

    @Before
    public void initTest() {
        glossaryOfRisks = createEntity(em);
    }

    @Test
    @Transactional
    public void createGlossaryOfRisks() throws Exception {
        int databaseSizeBeforeCreate = glossaryOfRisksRepository.findAll().size();

        // Create the GlossaryOfRisks
        restGlossaryOfRisksMockMvc.perform(post("/api/glossary-of-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(glossaryOfRisks)))
            .andExpect(status().isCreated());

        // Validate the GlossaryOfRisks in the database
        List<GlossaryOfRisks> glossaryOfRisksList = glossaryOfRisksRepository.findAll();
        assertThat(glossaryOfRisksList).hasSize(databaseSizeBeforeCreate + 1);
        GlossaryOfRisks testGlossaryOfRisks = glossaryOfRisksList.get(glossaryOfRisksList.size() - 1);
        assertThat(testGlossaryOfRisks.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGlossaryOfRisks.getCompletionDate()).isEqualTo(DEFAULT_COMPLETION_DATE);
        assertThat(testGlossaryOfRisks.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testGlossaryOfRisks.getImportantTo()).isEqualTo(DEFAULT_IMPORTANT_TO);
    }

    @Test
    @Transactional
    public void createGlossaryOfRisksWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = glossaryOfRisksRepository.findAll().size();

        // Create the GlossaryOfRisks with an existing ID
        glossaryOfRisks.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGlossaryOfRisksMockMvc.perform(post("/api/glossary-of-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(glossaryOfRisks)))
            .andExpect(status().isBadRequest());

        // Validate the GlossaryOfRisks in the database
        List<GlossaryOfRisks> glossaryOfRisksList = glossaryOfRisksRepository.findAll();
        assertThat(glossaryOfRisksList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = glossaryOfRisksRepository.findAll().size();
        // set the field null
        glossaryOfRisks.setName(null);

        // Create the GlossaryOfRisks, which fails.

        restGlossaryOfRisksMockMvc.perform(post("/api/glossary-of-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(glossaryOfRisks)))
            .andExpect(status().isBadRequest());

        List<GlossaryOfRisks> glossaryOfRisksList = glossaryOfRisksRepository.findAll();
        assertThat(glossaryOfRisksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGlossaryOfRisks() throws Exception {
        // Initialize the database
        glossaryOfRisksRepository.saveAndFlush(glossaryOfRisks);

        // Get all the glossaryOfRisksList
        restGlossaryOfRisksMockMvc.perform(get("/api/glossary-of-risks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(glossaryOfRisks.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].completionDate").value(hasItem(DEFAULT_COMPLETION_DATE.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].importantTo").value(hasItem(DEFAULT_IMPORTANT_TO.toString())));
    }

    @Test
    @Transactional
    public void getGlossaryOfRisks() throws Exception {
        // Initialize the database
        glossaryOfRisksRepository.saveAndFlush(glossaryOfRisks);

        // Get the glossaryOfRisks
        restGlossaryOfRisksMockMvc.perform(get("/api/glossary-of-risks/{id}", glossaryOfRisks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(glossaryOfRisks.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.completionDate").value(DEFAULT_COMPLETION_DATE.toString()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.importantTo").value(DEFAULT_IMPORTANT_TO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGlossaryOfRisks() throws Exception {
        // Get the glossaryOfRisks
        restGlossaryOfRisksMockMvc.perform(get("/api/glossary-of-risks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGlossaryOfRisks() throws Exception {
        // Initialize the database
        glossaryOfRisksService.save(glossaryOfRisks);

        int databaseSizeBeforeUpdate = glossaryOfRisksRepository.findAll().size();

        // Update the glossaryOfRisks
        GlossaryOfRisks updatedGlossaryOfRisks = glossaryOfRisksRepository.findOne(glossaryOfRisks.getId());
        updatedGlossaryOfRisks
            .name(UPDATED_NAME)
            .completionDate(UPDATED_COMPLETION_DATE)
            .creationDate(UPDATED_CREATION_DATE)
            .importantTo(UPDATED_IMPORTANT_TO);

        restGlossaryOfRisksMockMvc.perform(put("/api/glossary-of-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGlossaryOfRisks)))
            .andExpect(status().isOk());

        // Validate the GlossaryOfRisks in the database
        List<GlossaryOfRisks> glossaryOfRisksList = glossaryOfRisksRepository.findAll();
        assertThat(glossaryOfRisksList).hasSize(databaseSizeBeforeUpdate);
        GlossaryOfRisks testGlossaryOfRisks = glossaryOfRisksList.get(glossaryOfRisksList.size() - 1);
        assertThat(testGlossaryOfRisks.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGlossaryOfRisks.getCompletionDate()).isEqualTo(UPDATED_COMPLETION_DATE);
        assertThat(testGlossaryOfRisks.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testGlossaryOfRisks.getImportantTo()).isEqualTo(UPDATED_IMPORTANT_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingGlossaryOfRisks() throws Exception {
        int databaseSizeBeforeUpdate = glossaryOfRisksRepository.findAll().size();

        // Create the GlossaryOfRisks

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGlossaryOfRisksMockMvc.perform(put("/api/glossary-of-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(glossaryOfRisks)))
            .andExpect(status().isCreated());

        // Validate the GlossaryOfRisks in the database
        List<GlossaryOfRisks> glossaryOfRisksList = glossaryOfRisksRepository.findAll();
        assertThat(glossaryOfRisksList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGlossaryOfRisks() throws Exception {
        // Initialize the database
        glossaryOfRisksService.save(glossaryOfRisks);

        int databaseSizeBeforeDelete = glossaryOfRisksRepository.findAll().size();

        // Get the glossaryOfRisks
        restGlossaryOfRisksMockMvc.perform(delete("/api/glossary-of-risks/{id}", glossaryOfRisks.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GlossaryOfRisks> glossaryOfRisksList = glossaryOfRisksRepository.findAll();
        assertThat(glossaryOfRisksList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GlossaryOfRisks.class);
        GlossaryOfRisks glossaryOfRisks1 = new GlossaryOfRisks();
        glossaryOfRisks1.setId(1L);
        GlossaryOfRisks glossaryOfRisks2 = new GlossaryOfRisks();
        glossaryOfRisks2.setId(glossaryOfRisks1.getId());
        assertThat(glossaryOfRisks1).isEqualTo(glossaryOfRisks2);
        glossaryOfRisks2.setId(2L);
        assertThat(glossaryOfRisks1).isNotEqualTo(glossaryOfRisks2);
        glossaryOfRisks1.setId(null);
        assertThat(glossaryOfRisks1).isNotEqualTo(glossaryOfRisks2);
    }
}
