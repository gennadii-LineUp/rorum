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
import rog.domain.GlossaryOfMeasureUnits;
import rog.domain.enumeration.FrequencyOfGatheringData;
import rog.domain.enumeration.UnitsOfMeasurement;
import rog.repository.GlossaryOfMeasureUnitsRepository;
import rog.service.GlossaryOfMeasureUnitsService;
import rog.web.rest.errors.ExceptionTranslator;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static rog.web.rest.TestUtil.createFormattingConversionService;
/**
 * Test class for the GlossaryOfMeasureUnitsResource REST controller.
 *
 * @see GlossaryOfMeasureUnitsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RorumApp.class)
public class GlossaryOfMeasureUnitsResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final UnitsOfMeasurement DEFAULT_UNITS_OF_MEASUREMENT = UnitsOfMeasurement.ZL;
    private static final UnitsOfMeasurement UPDATED_UNITS_OF_MEASUREMENT = UnitsOfMeasurement.OS;

    private static final FrequencyOfGatheringData DEFAULT_FREQUENCY_OF_GATHERING_DATA = FrequencyOfGatheringData.DAY;
    private static final FrequencyOfGatheringData UPDATED_FREQUENCY_OF_GATHERING_DATA = FrequencyOfGatheringData.WEEK;

    @Autowired
    private GlossaryOfMeasureUnitsRepository glossaryOfMeasureUnitsRepository;

    @Autowired
    private GlossaryOfMeasureUnitsService glossaryOfMeasureUnitsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGlossaryOfMeasureUnitsMockMvc;

    private GlossaryOfMeasureUnits glossaryOfMeasureUnits;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GlossaryOfMeasureUnitsResource glossaryOfMeasureUnitsResource = new GlossaryOfMeasureUnitsResource();
        this.restGlossaryOfMeasureUnitsMockMvc = MockMvcBuilders.standaloneSetup(glossaryOfMeasureUnitsResource)
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
    public static GlossaryOfMeasureUnits createEntity(EntityManager em) {
        GlossaryOfMeasureUnits glossaryOfMeasureUnits = new GlossaryOfMeasureUnits()
            .name(DEFAULT_NAME)
            .unitsOfMeasurement(DEFAULT_UNITS_OF_MEASUREMENT)
            .frequencyOfGatheringData(DEFAULT_FREQUENCY_OF_GATHERING_DATA);
        return glossaryOfMeasureUnits;
    }

    @Before
    public void initTest() {
        glossaryOfMeasureUnits = createEntity(em);
    }

    @Test
    @Transactional
    public void createGlossaryOfMeasureUnits() throws Exception {
        int databaseSizeBeforeCreate = glossaryOfMeasureUnitsRepository.findAll().size();

        // Create the GlossaryOfMeasureUnits
        restGlossaryOfMeasureUnitsMockMvc.perform(post("/api/glossary-of-measure-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(glossaryOfMeasureUnits)))
            .andExpect(status().isCreated());

        // Validate the GlossaryOfMeasureUnits in the database
        List<GlossaryOfMeasureUnits> glossaryOfMeasureUnitsList = glossaryOfMeasureUnitsRepository.findAll();
        assertThat(glossaryOfMeasureUnitsList).hasSize(databaseSizeBeforeCreate + 1);
        GlossaryOfMeasureUnits testGlossaryOfMeasureUnits = glossaryOfMeasureUnitsList.get(glossaryOfMeasureUnitsList.size() - 1);
        assertThat(testGlossaryOfMeasureUnits.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGlossaryOfMeasureUnits.getUnitsOfMeasurement()).isEqualTo(DEFAULT_UNITS_OF_MEASUREMENT);
        assertThat(testGlossaryOfMeasureUnits.getFrequencyOfGatheringData()).isEqualTo(DEFAULT_FREQUENCY_OF_GATHERING_DATA);
    }

    @Test
    @Transactional
    public void createGlossaryOfMeasureUnitsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = glossaryOfMeasureUnitsRepository.findAll().size();

        // Create the GlossaryOfMeasureUnits with an existing ID
        glossaryOfMeasureUnits.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGlossaryOfMeasureUnitsMockMvc.perform(post("/api/glossary-of-measure-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(glossaryOfMeasureUnits)))
            .andExpect(status().isBadRequest());

        // Validate the GlossaryOfMeasureUnits in the database
        List<GlossaryOfMeasureUnits> glossaryOfMeasureUnitsList = glossaryOfMeasureUnitsRepository.findAll();
        assertThat(glossaryOfMeasureUnitsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = glossaryOfMeasureUnitsRepository.findAll().size();
        // set the field null
        glossaryOfMeasureUnits.setName(null);

        // Create the GlossaryOfMeasureUnits, which fails.

        restGlossaryOfMeasureUnitsMockMvc.perform(post("/api/glossary-of-measure-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(glossaryOfMeasureUnits)))
            .andExpect(status().isBadRequest());

        List<GlossaryOfMeasureUnits> glossaryOfMeasureUnitsList = glossaryOfMeasureUnitsRepository.findAll();
        assertThat(glossaryOfMeasureUnitsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGlossaryOfMeasureUnits() throws Exception {
        // Initialize the database
        glossaryOfMeasureUnitsRepository.saveAndFlush(glossaryOfMeasureUnits);

        // Get all the glossaryOfMeasureUnitsList
        restGlossaryOfMeasureUnitsMockMvc.perform(get("/api/glossary-of-measure-units?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(glossaryOfMeasureUnits.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].unitsOfMeasurement").value(hasItem(DEFAULT_UNITS_OF_MEASUREMENT.toString())))
            .andExpect(jsonPath("$.[*].frequencyOfGatheringData").value(hasItem(DEFAULT_FREQUENCY_OF_GATHERING_DATA.toString())));
    }

    @Test
    @Transactional
    public void getGlossaryOfMeasureUnits() throws Exception {
        // Initialize the database
        glossaryOfMeasureUnitsRepository.saveAndFlush(glossaryOfMeasureUnits);

        // Get the glossaryOfMeasureUnits
        restGlossaryOfMeasureUnitsMockMvc.perform(get("/api/glossary-of-measure-units/{id}", glossaryOfMeasureUnits.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(glossaryOfMeasureUnits.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.unitsOfMeasurement").value(DEFAULT_UNITS_OF_MEASUREMENT.toString()))
            .andExpect(jsonPath("$.frequencyOfGatheringData").value(DEFAULT_FREQUENCY_OF_GATHERING_DATA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGlossaryOfMeasureUnits() throws Exception {
        // Get the glossaryOfMeasureUnits
        restGlossaryOfMeasureUnitsMockMvc.perform(get("/api/glossary-of-measure-units/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGlossaryOfMeasureUnits() throws Exception {
        // Initialize the database
        glossaryOfMeasureUnitsService.save(glossaryOfMeasureUnits);

        int databaseSizeBeforeUpdate = glossaryOfMeasureUnitsRepository.findAll().size();

        // Update the glossaryOfMeasureUnits
        GlossaryOfMeasureUnits updatedGlossaryOfMeasureUnits = glossaryOfMeasureUnitsRepository.findOne(glossaryOfMeasureUnits.getId());
        updatedGlossaryOfMeasureUnits
            .name(UPDATED_NAME)
            .unitsOfMeasurement(UPDATED_UNITS_OF_MEASUREMENT)
            .frequencyOfGatheringData(UPDATED_FREQUENCY_OF_GATHERING_DATA);

        restGlossaryOfMeasureUnitsMockMvc.perform(put("/api/glossary-of-measure-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGlossaryOfMeasureUnits)))
            .andExpect(status().isOk());

        // Validate the GlossaryOfMeasureUnits in the database
        List<GlossaryOfMeasureUnits> glossaryOfMeasureUnitsList = glossaryOfMeasureUnitsRepository.findAll();
        assertThat(glossaryOfMeasureUnitsList).hasSize(databaseSizeBeforeUpdate);
        GlossaryOfMeasureUnits testGlossaryOfMeasureUnits = glossaryOfMeasureUnitsList.get(glossaryOfMeasureUnitsList.size() - 1);
        assertThat(testGlossaryOfMeasureUnits.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGlossaryOfMeasureUnits.getUnitsOfMeasurement()).isEqualTo(UPDATED_UNITS_OF_MEASUREMENT);
        assertThat(testGlossaryOfMeasureUnits.getFrequencyOfGatheringData()).isEqualTo(UPDATED_FREQUENCY_OF_GATHERING_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingGlossaryOfMeasureUnits() throws Exception {
        int databaseSizeBeforeUpdate = glossaryOfMeasureUnitsRepository.findAll().size();

        // Create the GlossaryOfMeasureUnits

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGlossaryOfMeasureUnitsMockMvc.perform(put("/api/glossary-of-measure-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(glossaryOfMeasureUnits)))
            .andExpect(status().isCreated());

        // Validate the GlossaryOfMeasureUnits in the database
        List<GlossaryOfMeasureUnits> glossaryOfMeasureUnitsList = glossaryOfMeasureUnitsRepository.findAll();
        assertThat(glossaryOfMeasureUnitsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGlossaryOfMeasureUnits() throws Exception {
        // Initialize the database
        glossaryOfMeasureUnitsService.save(glossaryOfMeasureUnits);

        int databaseSizeBeforeDelete = glossaryOfMeasureUnitsRepository.findAll().size();

        // Get the glossaryOfMeasureUnits
        restGlossaryOfMeasureUnitsMockMvc.perform(delete("/api/glossary-of-measure-units/{id}", glossaryOfMeasureUnits.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GlossaryOfMeasureUnits> glossaryOfMeasureUnitsList = glossaryOfMeasureUnitsRepository.findAll();
        assertThat(glossaryOfMeasureUnitsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GlossaryOfMeasureUnits.class);
        GlossaryOfMeasureUnits glossaryOfMeasureUnits1 = new GlossaryOfMeasureUnits();
        glossaryOfMeasureUnits1.setId(1L);
        GlossaryOfMeasureUnits glossaryOfMeasureUnits2 = new GlossaryOfMeasureUnits();
        glossaryOfMeasureUnits2.setId(glossaryOfMeasureUnits1.getId());
        assertThat(glossaryOfMeasureUnits1).isEqualTo(glossaryOfMeasureUnits2);
        glossaryOfMeasureUnits2.setId(2L);
        assertThat(glossaryOfMeasureUnits1).isNotEqualTo(glossaryOfMeasureUnits2);
        glossaryOfMeasureUnits1.setId(null);
        assertThat(glossaryOfMeasureUnits1).isNotEqualTo(glossaryOfMeasureUnits2);
    }
}
