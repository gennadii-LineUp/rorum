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
import rog.domain.GlossaryOfProcesses;
import rog.repository.GlossaryOfProcessesRepository;
import rog.service.GlossaryOfProcessesService;
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
 * Test class for the GlossaryOfProcessesResource REST controller.
 *
 * @see GlossaryOfProcessesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RorumApp.class)
public class GlossaryOfProcessesResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_IMPORTANT_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_IMPORTANT_TO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private GlossaryOfProcessesRepository glossaryOfProcessesRepository;

    @Autowired
    private GlossaryOfProcessesService glossaryOfProcessesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGlossaryOfProcessesMockMvc;

    private GlossaryOfProcesses glossaryOfProcesses;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GlossaryOfProcessesResource glossaryOfProcessesResource = new GlossaryOfProcessesResource();
        this.restGlossaryOfProcessesMockMvc = MockMvcBuilders.standaloneSetup(glossaryOfProcessesResource)
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
    public static GlossaryOfProcesses createEntity(EntityManager em) {
        GlossaryOfProcesses glossaryOfProcesses = new GlossaryOfProcesses()
            .name(DEFAULT_NAME)
            .importantTo(DEFAULT_IMPORTANT_TO);
        return glossaryOfProcesses;
    }

    @Before
    public void initTest() {
        glossaryOfProcesses = createEntity(em);
    }

    @Test
    @Transactional
    public void createGlossaryOfProcesses() throws Exception {
        int databaseSizeBeforeCreate = glossaryOfProcessesRepository.findAll().size();

        // Create the GlossaryOfProcesses
        restGlossaryOfProcessesMockMvc.perform(post("/api/glossary-of-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(glossaryOfProcesses)))
            .andExpect(status().isCreated());

        // Validate the GlossaryOfProcesses in the database
        List<GlossaryOfProcesses> glossaryOfProcessesList = glossaryOfProcessesRepository.findAll();
        assertThat(glossaryOfProcessesList).hasSize(databaseSizeBeforeCreate + 1);
        GlossaryOfProcesses testGlossaryOfProcesses = glossaryOfProcessesList.get(glossaryOfProcessesList.size() - 1);
        assertThat(testGlossaryOfProcesses.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGlossaryOfProcesses.getImportantTo()).isEqualTo(DEFAULT_IMPORTANT_TO);
    }

    @Test
    @Transactional
    public void createGlossaryOfProcessesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = glossaryOfProcessesRepository.findAll().size();

        // Create the GlossaryOfProcesses with an existing ID
        glossaryOfProcesses.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGlossaryOfProcessesMockMvc.perform(post("/api/glossary-of-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(glossaryOfProcesses)))
            .andExpect(status().isBadRequest());

        // Validate the GlossaryOfProcesses in the database
        List<GlossaryOfProcesses> glossaryOfProcessesList = glossaryOfProcessesRepository.findAll();
        assertThat(glossaryOfProcessesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = glossaryOfProcessesRepository.findAll().size();
        // set the field null
        glossaryOfProcesses.setName(null);

        // Create the GlossaryOfProcesses, which fails.

        restGlossaryOfProcessesMockMvc.perform(post("/api/glossary-of-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(glossaryOfProcesses)))
            .andExpect(status().isBadRequest());

        List<GlossaryOfProcesses> glossaryOfProcessesList = glossaryOfProcessesRepository.findAll();
        assertThat(glossaryOfProcessesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGlossaryOfProcesses() throws Exception {
        // Initialize the database
        glossaryOfProcessesRepository.saveAndFlush(glossaryOfProcesses);

        // Get all the glossaryOfProcessesList
        restGlossaryOfProcessesMockMvc.perform(get("/api/glossary-of-processes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(glossaryOfProcesses.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].importantTo").value(hasItem(DEFAULT_IMPORTANT_TO.toString())));
    }

    @Test
    @Transactional
    public void getGlossaryOfProcesses() throws Exception {
        // Initialize the database
        glossaryOfProcessesRepository.saveAndFlush(glossaryOfProcesses);

        // Get the glossaryOfProcesses
        restGlossaryOfProcessesMockMvc.perform(get("/api/glossary-of-processes/{id}", glossaryOfProcesses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(glossaryOfProcesses.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.importantTo").value(DEFAULT_IMPORTANT_TO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGlossaryOfProcesses() throws Exception {
        // Get the glossaryOfProcesses
        restGlossaryOfProcessesMockMvc.perform(get("/api/glossary-of-processes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGlossaryOfProcesses() throws Exception {
        // Initialize the database
        glossaryOfProcessesService.save(glossaryOfProcesses);

        int databaseSizeBeforeUpdate = glossaryOfProcessesRepository.findAll().size();

        // Update the glossaryOfProcesses
        GlossaryOfProcesses updatedGlossaryOfProcesses = glossaryOfProcessesRepository.findOne(glossaryOfProcesses.getId());
        updatedGlossaryOfProcesses
            .name(UPDATED_NAME)
            .importantTo(UPDATED_IMPORTANT_TO);

        restGlossaryOfProcessesMockMvc.perform(put("/api/glossary-of-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGlossaryOfProcesses)))
            .andExpect(status().isOk());

        // Validate the GlossaryOfProcesses in the database
        List<GlossaryOfProcesses> glossaryOfProcessesList = glossaryOfProcessesRepository.findAll();
        assertThat(glossaryOfProcessesList).hasSize(databaseSizeBeforeUpdate);
        GlossaryOfProcesses testGlossaryOfProcesses = glossaryOfProcessesList.get(glossaryOfProcessesList.size() - 1);
        assertThat(testGlossaryOfProcesses.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGlossaryOfProcesses.getImportantTo()).isEqualTo(UPDATED_IMPORTANT_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingGlossaryOfProcesses() throws Exception {
        int databaseSizeBeforeUpdate = glossaryOfProcessesRepository.findAll().size();

        // Create the GlossaryOfProcesses

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGlossaryOfProcessesMockMvc.perform(put("/api/glossary-of-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(glossaryOfProcesses)))
            .andExpect(status().isCreated());

        // Validate the GlossaryOfProcesses in the database
        List<GlossaryOfProcesses> glossaryOfProcessesList = glossaryOfProcessesRepository.findAll();
        assertThat(glossaryOfProcessesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGlossaryOfProcesses() throws Exception {
        // Initialize the database
        glossaryOfProcessesService.save(glossaryOfProcesses);

        int databaseSizeBeforeDelete = glossaryOfProcessesRepository.findAll().size();

        // Get the glossaryOfProcesses
        restGlossaryOfProcessesMockMvc.perform(delete("/api/glossary-of-processes/{id}", glossaryOfProcesses.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GlossaryOfProcesses> glossaryOfProcessesList = glossaryOfProcessesRepository.findAll();
        assertThat(glossaryOfProcessesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GlossaryOfProcesses.class);
        GlossaryOfProcesses glossaryOfProcesses1 = new GlossaryOfProcesses();
        glossaryOfProcesses1.setId(1L);
        GlossaryOfProcesses glossaryOfProcesses2 = new GlossaryOfProcesses();
        glossaryOfProcesses2.setId(glossaryOfProcesses1.getId());
        assertThat(glossaryOfProcesses1).isEqualTo(glossaryOfProcesses2);
        glossaryOfProcesses2.setId(2L);
        assertThat(glossaryOfProcesses1).isNotEqualTo(glossaryOfProcesses2);
        glossaryOfProcesses1.setId(null);
        assertThat(glossaryOfProcesses1).isNotEqualTo(glossaryOfProcesses2);
    }
}
