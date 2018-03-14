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
import rog.domain.FilledMeasureUnits;
import rog.domain.enumeration.UnitsOfMeasurement;
import rog.repository.FilledMeasureUnitsRepository;
import rog.service.FilledMeasureUnitsService;
import rog.web.rest.errors.ExceptionTranslator;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static rog.web.rest.TestUtil.createFormattingConversionService;

/**
 * Test class for the FilledMeasureUnitsResource REST controller.
 *
 * @see FilledMeasureUnitsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RorumApp.class)
public class FilledMeasureUnitsResourceIntTest {

    private static final Integer DEFAULT_VALUE = 1;
    private static final Integer UPDATED_VALUE = 2;

    private static final UnitsOfMeasurement DEFAULT_OPTION = UnitsOfMeasurement.M2;
    private static final UnitsOfMeasurement UPDATED_OPTION = UnitsOfMeasurement.KM;

    @Autowired
    private FilledMeasureUnitsRepository filledMeasureUnitsRepository;

    @Autowired
    private FilledMeasureUnitsService filledMeasureUnitsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFilledMeasureUnitsMockMvc;

    private FilledMeasureUnits filledMeasureUnits;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FilledMeasureUnitsResource filledMeasureUnitsResource = new FilledMeasureUnitsResource();
        this.restFilledMeasureUnitsMockMvc = MockMvcBuilders.standaloneSetup(filledMeasureUnitsResource)
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
    public static FilledMeasureUnits createEntity(EntityManager em) {
        FilledMeasureUnits filledMeasureUnits = new FilledMeasureUnits()
            .value(DEFAULT_VALUE);
        return filledMeasureUnits;
    }

    @Before
    public void initTest() {
        filledMeasureUnits = createEntity(em);
    }

    @Test
    @Transactional
    public void createFilledMeasureUnits() throws Exception {
        int databaseSizeBeforeCreate = filledMeasureUnitsRepository.findAll().size();

        // Create the FilledMeasureUnits
        restFilledMeasureUnitsMockMvc.perform(post("/api/filled-measure-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filledMeasureUnits)))
            .andExpect(status().isCreated());

        // Validate the FilledMeasureUnits in the database
        List<FilledMeasureUnits> filledMeasureUnitsList = filledMeasureUnitsRepository.findAll();
        assertThat(filledMeasureUnitsList).hasSize(databaseSizeBeforeCreate + 1);
        FilledMeasureUnits testFilledMeasureUnits = filledMeasureUnitsList.get(filledMeasureUnitsList.size() - 1);
        assertThat(testFilledMeasureUnits.getBaseValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createFilledMeasureUnitsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = filledMeasureUnitsRepository.findAll().size();

        // Create the FilledMeasureUnits with an existing ID
        filledMeasureUnits.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFilledMeasureUnitsMockMvc.perform(post("/api/filled-measure-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filledMeasureUnits)))
            .andExpect(status().isBadRequest());

        // Validate the FilledMeasureUnits in the database
        List<FilledMeasureUnits> filledMeasureUnitsList = filledMeasureUnitsRepository.findAll();
        assertThat(filledMeasureUnitsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFilledMeasureUnits() throws Exception {
        // Initialize the database
        filledMeasureUnitsRepository.saveAndFlush(filledMeasureUnits);

        // Get all the filledMeasureUnitsList
        restFilledMeasureUnitsMockMvc.perform(get("/api/filled-measure-units?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(filledMeasureUnits.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].option").value(hasItem(DEFAULT_OPTION.toString())));
    }

    @Test
    @Transactional
    public void getFilledMeasureUnits() throws Exception {
        // Initialize the database
        filledMeasureUnitsRepository.saveAndFlush(filledMeasureUnits);

        // Get the filledMeasureUnits
        restFilledMeasureUnitsMockMvc.perform(get("/api/filled-measure-units/{id}", filledMeasureUnits.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(filledMeasureUnits.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.option").value(DEFAULT_OPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFilledMeasureUnits() throws Exception {
        // Get the filledMeasureUnits
        restFilledMeasureUnitsMockMvc.perform(get("/api/filled-measure-units/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFilledMeasureUnits() throws Exception {
        // Initialize the database
        filledMeasureUnitsService.save(filledMeasureUnits);

        int databaseSizeBeforeUpdate = filledMeasureUnitsRepository.findAll().size();

        // Update the filledMeasureUnits
        FilledMeasureUnits updatedFilledMeasureUnits = filledMeasureUnitsRepository.findOne(filledMeasureUnits.getId());
        updatedFilledMeasureUnits
            .value(UPDATED_VALUE);

        restFilledMeasureUnitsMockMvc.perform(put("/api/filled-measure-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFilledMeasureUnits)))
            .andExpect(status().isOk());

        // Validate the FilledMeasureUnits in the database
        List<FilledMeasureUnits> filledMeasureUnitsList = filledMeasureUnitsRepository.findAll();
        assertThat(filledMeasureUnitsList).hasSize(databaseSizeBeforeUpdate);
        FilledMeasureUnits testFilledMeasureUnits = filledMeasureUnitsList.get(filledMeasureUnitsList.size() - 1);
        assertThat(testFilledMeasureUnits.getBaseValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingFilledMeasureUnits() throws Exception {
        int databaseSizeBeforeUpdate = filledMeasureUnitsRepository.findAll().size();

        // Create the FilledMeasureUnits

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFilledMeasureUnitsMockMvc.perform(put("/api/filled-measure-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filledMeasureUnits)))
            .andExpect(status().isCreated());

        // Validate the FilledMeasureUnits in the database
        List<FilledMeasureUnits> filledMeasureUnitsList = filledMeasureUnitsRepository.findAll();
        assertThat(filledMeasureUnitsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFilledMeasureUnits() throws Exception {
        // Initialize the database
        filledMeasureUnitsService.save(filledMeasureUnits);

        int databaseSizeBeforeDelete = filledMeasureUnitsRepository.findAll().size();

        // Get the filledMeasureUnits
        restFilledMeasureUnitsMockMvc.perform(delete("/api/filled-measure-units/{id}", filledMeasureUnits.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FilledMeasureUnits> filledMeasureUnitsList = filledMeasureUnitsRepository.findAll();
        assertThat(filledMeasureUnitsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FilledMeasureUnits.class);
        FilledMeasureUnits filledMeasureUnits1 = new FilledMeasureUnits();
        filledMeasureUnits1.setId(1L);
        FilledMeasureUnits filledMeasureUnits2 = new FilledMeasureUnits();
        filledMeasureUnits2.setId(filledMeasureUnits1.getId());
        assertThat(filledMeasureUnits1).isEqualTo(filledMeasureUnits2);
        filledMeasureUnits2.setId(2L);
        assertThat(filledMeasureUnits1).isNotEqualTo(filledMeasureUnits2);
        filledMeasureUnits1.setId(null);
        assertThat(filledMeasureUnits1).isNotEqualTo(filledMeasureUnits2);
    }
}
