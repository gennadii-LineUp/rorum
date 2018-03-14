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
import rog.domain.GlossaryOfPurposes;
import rog.domain.enumeration.AssignmentToCell;
import rog.repository.GlossaryOfPurposesRepository;
import rog.service.GlossaryOfMeasureUnitsService;
import rog.service.GlossaryOfPurposesService;
import rog.service.GlossaryOfRisksService;
import rog.service.UserService;
import rog.web.rest.errors.ExceptionTranslator;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static rog.web.rest.TestUtil.createFormattingConversionService;
/**
 * Test class for the GlossaryOfPurposesResource REST controller.
 *
 * @see GlossaryOfPurposesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RorumApp.class)
public class GlossaryOfPurposesResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_PARENT_ID = 1L;
    private static final Long UPDATED_PARENT_ID = 2L;

    private static final Boolean DEFAULT_SUM_UP = false;
    private static final Boolean UPDATED_SUM_UP = true;

    private static final AssignmentToCell DEFAULT_ASSIGNMENT_TO_CELL = AssignmentToCell.JEDNOSTKAWYKONAWCZA;
    private static final AssignmentToCell UPDATED_ASSIGNMENT_TO_CELL = AssignmentToCell.URZADDZIELNICY;

    @Autowired
    private GlossaryOfPurposesRepository glossaryOfPurposesRepository;

    @Autowired
    private GlossaryOfPurposesService glossaryOfPurposesService;

    @Autowired
    private GlossaryOfMeasureUnitsService glossaryOfMeasureUnitsService;

    @Autowired
    private GlossaryOfRisksService glossaryOfRisksService;

    @Autowired
    private UserService userService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGlossaryOfPurposesMockMvc;

    private GlossaryOfPurposes glossaryOfPurposes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GlossaryOfPurposesResource glossaryOfPurposesResource = new GlossaryOfPurposesResource();
        this.restGlossaryOfPurposesMockMvc = MockMvcBuilders.standaloneSetup(glossaryOfPurposesResource)
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
    public static GlossaryOfPurposes createEntity(EntityManager em) {
        GlossaryOfPurposes glossaryOfPurposes = new GlossaryOfPurposes()
            .name(DEFAULT_NAME)
            .parentId(DEFAULT_PARENT_ID)
            .sumUp(DEFAULT_SUM_UP)
            .assignmentToCell(DEFAULT_ASSIGNMENT_TO_CELL);
        return glossaryOfPurposes;
    }

    @Before
    public void initTest() {
        glossaryOfPurposes = createEntity(em);
    }

    @Test
    @Transactional
    public void createGlossaryOfPurposes() throws Exception {
        int databaseSizeBeforeCreate = glossaryOfPurposesRepository.findAll().size();

        // Create the GlossaryOfPurposes
        restGlossaryOfPurposesMockMvc.perform(post("/api/glossary-of-purposes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(glossaryOfPurposes)))
            .andExpect(status().isCreated());

        // Validate the GlossaryOfPurposes in the database
        List<GlossaryOfPurposes> glossaryOfPurposesList = glossaryOfPurposesRepository.findAll();
        assertThat(glossaryOfPurposesList).hasSize(databaseSizeBeforeCreate + 1);
        GlossaryOfPurposes testGlossaryOfPurposes = glossaryOfPurposesList.get(glossaryOfPurposesList.size() - 1);
        assertThat(testGlossaryOfPurposes.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGlossaryOfPurposes.getParentId()).isEqualTo(DEFAULT_PARENT_ID);
        assertThat(testGlossaryOfPurposes.isSumUp()).isEqualTo(DEFAULT_SUM_UP);
        assertThat(testGlossaryOfPurposes.getAssignmentToCell()).isEqualTo(DEFAULT_ASSIGNMENT_TO_CELL);
    }

    @Test
    @Transactional
    public void createGlossaryOfPurposesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = glossaryOfPurposesRepository.findAll().size();

        // Create the GlossaryOfPurposes with an existing ID
        glossaryOfPurposes.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGlossaryOfPurposesMockMvc.perform(post("/api/glossary-of-purposes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(glossaryOfPurposes)))
            .andExpect(status().isBadRequest());

        // Validate the GlossaryOfPurposes in the database
        List<GlossaryOfPurposes> glossaryOfPurposesList = glossaryOfPurposesRepository.findAll();
        assertThat(glossaryOfPurposesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = glossaryOfPurposesRepository.findAll().size();
        // set the field null
        glossaryOfPurposes.setName(null);

        // Create the GlossaryOfPurposes, which fails.

        restGlossaryOfPurposesMockMvc.perform(post("/api/glossary-of-purposes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(glossaryOfPurposes)))
            .andExpect(status().isBadRequest());

        List<GlossaryOfPurposes> glossaryOfPurposesList = glossaryOfPurposesRepository.findAll();
        assertThat(glossaryOfPurposesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGlossaryOfPurposes() throws Exception {
        // Initialize the database
        glossaryOfPurposesRepository.saveAndFlush(glossaryOfPurposes);

        // Get all the glossaryOfPurposesList
        restGlossaryOfPurposesMockMvc.perform(get("/api/glossary-of-purposes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(glossaryOfPurposes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].sumUp").value(hasItem(DEFAULT_SUM_UP.booleanValue())))
            .andExpect(jsonPath("$.[*].assignmentToCell").value(hasItem(DEFAULT_ASSIGNMENT_TO_CELL.toString())));
    }

    @Test
    @Transactional
    public void getGlossaryOfPurposes() throws Exception {
        // Initialize the database
        glossaryOfPurposesRepository.saveAndFlush(glossaryOfPurposes);

        // Get the glossaryOfPurposes
        restGlossaryOfPurposesMockMvc.perform(get("/api/glossary-of-purposes/{id}", glossaryOfPurposes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(glossaryOfPurposes.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.parentId").value(DEFAULT_PARENT_ID.intValue()))
            .andExpect(jsonPath("$.sumUp").value(DEFAULT_SUM_UP.booleanValue()))
            .andExpect(jsonPath("$.assignmentToCell").value(DEFAULT_ASSIGNMENT_TO_CELL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGlossaryOfPurposes() throws Exception {
        // Get the glossaryOfPurposes
        restGlossaryOfPurposesMockMvc.perform(get("/api/glossary-of-purposes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGlossaryOfPurposes() throws Exception {
        // Initialize the database
        glossaryOfPurposesService.save(glossaryOfPurposes);

        int databaseSizeBeforeUpdate = glossaryOfPurposesRepository.findAll().size();

        // Update the glossaryOfPurposes
        GlossaryOfPurposes updatedGlossaryOfPurposes = glossaryOfPurposesRepository.findOne(glossaryOfPurposes.getId());
        updatedGlossaryOfPurposes
            .name(UPDATED_NAME)
            .parentId(UPDATED_PARENT_ID)
            .sumUp(UPDATED_SUM_UP)
            .assignmentToCell(UPDATED_ASSIGNMENT_TO_CELL);

        restGlossaryOfPurposesMockMvc.perform(put("/api/glossary-of-purposes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGlossaryOfPurposes)))
            .andExpect(status().isOk());

        // Validate the GlossaryOfPurposes in the database
        List<GlossaryOfPurposes> glossaryOfPurposesList = glossaryOfPurposesRepository.findAll();
        assertThat(glossaryOfPurposesList).hasSize(databaseSizeBeforeUpdate);
        GlossaryOfPurposes testGlossaryOfPurposes = glossaryOfPurposesList.get(glossaryOfPurposesList.size() - 1);
        assertThat(testGlossaryOfPurposes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGlossaryOfPurposes.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testGlossaryOfPurposes.isSumUp()).isEqualTo(UPDATED_SUM_UP);
        assertThat(testGlossaryOfPurposes.getAssignmentToCell()).isEqualTo(UPDATED_ASSIGNMENT_TO_CELL);
    }

    @Test
    @Transactional
    public void updateNonExistingGlossaryOfPurposes() throws Exception {
        int databaseSizeBeforeUpdate = glossaryOfPurposesRepository.findAll().size();

        // Create the GlossaryOfPurposes

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGlossaryOfPurposesMockMvc.perform(put("/api/glossary-of-purposes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(glossaryOfPurposes)))
            .andExpect(status().isCreated());

        // Validate the GlossaryOfPurposes in the database
        List<GlossaryOfPurposes> glossaryOfPurposesList = glossaryOfPurposesRepository.findAll();
        assertThat(glossaryOfPurposesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGlossaryOfPurposes() throws Exception {
        // Initialize the database
        glossaryOfPurposesService.save(glossaryOfPurposes);

        int databaseSizeBeforeDelete = glossaryOfPurposesRepository.findAll().size();

        // Get the glossaryOfPurposes
        restGlossaryOfPurposesMockMvc.perform(delete("/api/glossary-of-purposes/{id}", glossaryOfPurposes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GlossaryOfPurposes> glossaryOfPurposesList = glossaryOfPurposesRepository.findAll();
        assertThat(glossaryOfPurposesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GlossaryOfPurposes.class);
        GlossaryOfPurposes glossaryOfPurposes1 = new GlossaryOfPurposes();
        glossaryOfPurposes1.setId(1L);
        GlossaryOfPurposes glossaryOfPurposes2 = new GlossaryOfPurposes();
        glossaryOfPurposes2.setId(glossaryOfPurposes1.getId());
        assertThat(glossaryOfPurposes1).isEqualTo(glossaryOfPurposes2);
        glossaryOfPurposes2.setId(2L);
        assertThat(glossaryOfPurposes1).isNotEqualTo(glossaryOfPurposes2);
        glossaryOfPurposes1.setId(null);
        assertThat(glossaryOfPurposes1).isNotEqualTo(glossaryOfPurposes2);
    }
}
