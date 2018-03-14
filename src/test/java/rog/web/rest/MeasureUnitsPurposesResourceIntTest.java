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
import rog.domain.MeasureUnitsPurposes;
import rog.repository.MeasureUnitsPurposesRepository;
import rog.service.MeasureUnitsPurposesService;
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
 * Test class for the MeasureUnitsPurposesResource REST controller.
 *
 * @see MeasureUnitsPurposesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RorumApp.class)
public class MeasureUnitsPurposesResourceIntTest {

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MeasureUnitsPurposesRepository measureUnitsPurposesRepository;

    @Autowired
    private MeasureUnitsPurposesService measureUnitsPurposesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMeasureUnitsPurposesMockMvc;

    private MeasureUnitsPurposes measureUnitsPurposes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MeasureUnitsPurposesResource measureUnitsPurposesResource = new MeasureUnitsPurposesResource();
        this.restMeasureUnitsPurposesMockMvc = MockMvcBuilders.standaloneSetup(measureUnitsPurposesResource)
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
    public static MeasureUnitsPurposes createEntity(EntityManager em) {
        MeasureUnitsPurposes measureUnitsPurposes = new MeasureUnitsPurposes();
        return measureUnitsPurposes;
    }

    @Before
    public void initTest() {
        measureUnitsPurposes = createEntity(em);
    }

    @Test
    @Transactional
    public void createMeasureUnitsPurposes() throws Exception {
        int databaseSizeBeforeCreate = measureUnitsPurposesRepository.findAll().size();

        // Create the MeasureUnitsPurposes
        restMeasureUnitsPurposesMockMvc.perform(post("/api/measure-units-purposes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(measureUnitsPurposes)))
            .andExpect(status().isCreated());

        // Validate the MeasureUnitsPurposes in the database
        List<MeasureUnitsPurposes> measureUnitsPurposesList = measureUnitsPurposesRepository.findAll();
        assertThat(measureUnitsPurposesList).hasSize(databaseSizeBeforeCreate + 1);
    }

    @Test
    @Transactional
    public void createMeasureUnitsPurposesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = measureUnitsPurposesRepository.findAll().size();

        // Create the MeasureUnitsPurposes with an existing ID
        measureUnitsPurposes.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMeasureUnitsPurposesMockMvc.perform(post("/api/measure-units-purposes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(measureUnitsPurposes)))
            .andExpect(status().isBadRequest());

        // Validate the MeasureUnitsPurposes in the database
        List<MeasureUnitsPurposes> measureUnitsPurposesList = measureUnitsPurposesRepository.findAll();
        assertThat(measureUnitsPurposesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMeasureUnitsPurposes() throws Exception {
        // Initialize the database
        measureUnitsPurposesRepository.saveAndFlush(measureUnitsPurposes);

        // Get all the measureUnitsPurposesList
        restMeasureUnitsPurposesMockMvc.perform(get("/api/measure-units-purposes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(measureUnitsPurposes.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }

    @Test
    @Transactional
    public void getMeasureUnitsPurposes() throws Exception {
        // Initialize the database
        measureUnitsPurposesRepository.saveAndFlush(measureUnitsPurposes);

        // Get the measureUnitsPurposes
        restMeasureUnitsPurposesMockMvc.perform(get("/api/measure-units-purposes/{id}", measureUnitsPurposes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(measureUnitsPurposes.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMeasureUnitsPurposes() throws Exception {
        // Get the measureUnitsPurposes
        restMeasureUnitsPurposesMockMvc.perform(get("/api/measure-units-purposes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMeasureUnitsPurposes() throws Exception {
        // Initialize the database
        measureUnitsPurposesService.save(measureUnitsPurposes);

        int databaseSizeBeforeUpdate = measureUnitsPurposesRepository.findAll().size();

        // Update the measureUnitsPurposes
        MeasureUnitsPurposes updatedMeasureUnitsPurposes = measureUnitsPurposesRepository.findOne(measureUnitsPurposes.getId());

        restMeasureUnitsPurposesMockMvc.perform(put("/api/measure-units-purposes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMeasureUnitsPurposes)))
            .andExpect(status().isOk());

        // Validate the MeasureUnitsPurposes in the database
        List<MeasureUnitsPurposes> measureUnitsPurposesList = measureUnitsPurposesRepository.findAll();
        assertThat(measureUnitsPurposesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void updateNonExistingMeasureUnitsPurposes() throws Exception {
        int databaseSizeBeforeUpdate = measureUnitsPurposesRepository.findAll().size();

        // Create the MeasureUnitsPurposes

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMeasureUnitsPurposesMockMvc.perform(put("/api/measure-units-purposes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(measureUnitsPurposes)))
            .andExpect(status().isCreated());

        // Validate the MeasureUnitsPurposes in the database
        List<MeasureUnitsPurposes> measureUnitsPurposesList = measureUnitsPurposesRepository.findAll();
        assertThat(measureUnitsPurposesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMeasureUnitsPurposes() throws Exception {
        // Initialize the database
        measureUnitsPurposesService.save(measureUnitsPurposes);

        int databaseSizeBeforeDelete = measureUnitsPurposesRepository.findAll().size();

        // Get the measureUnitsPurposes
        restMeasureUnitsPurposesMockMvc.perform(delete("/api/measure-units-purposes/{id}", measureUnitsPurposes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MeasureUnitsPurposes> measureUnitsPurposesList = measureUnitsPurposesRepository.findAll();
        assertThat(measureUnitsPurposesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MeasureUnitsPurposes.class);
        MeasureUnitsPurposes measureUnitsPurposes1 = new MeasureUnitsPurposes();
        measureUnitsPurposes1.setId(1L);
        MeasureUnitsPurposes measureUnitsPurposes2 = new MeasureUnitsPurposes();
        measureUnitsPurposes2.setId(measureUnitsPurposes1.getId());
        assertThat(measureUnitsPurposes1).isEqualTo(measureUnitsPurposes2);
        measureUnitsPurposes2.setId(2L);
        assertThat(measureUnitsPurposes1).isNotEqualTo(measureUnitsPurposes2);
        measureUnitsPurposes1.setId(null);
        assertThat(measureUnitsPurposes1).isNotEqualTo(measureUnitsPurposes2);
    }
}
