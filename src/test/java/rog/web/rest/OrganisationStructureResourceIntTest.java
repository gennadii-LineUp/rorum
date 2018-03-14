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
import rog.domain.OrganisationStructure;
import rog.domain.enumeration.SpecifyingCells;
import rog.repository.OrganisationStructureRepository;
import rog.service.GlossaryOfCommercialRisksService;
import rog.service.GlossaryOfPurposesService;
import rog.service.OrganisationStructureService;
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
 * Test class for the OrganisationStructureResource REST controller.
 *
 * @see OrganisationStructureResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RorumApp.class)
public class OrganisationStructureResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_PARENT_ID = 1L;
    private static final Long UPDATED_PARENT_ID = 2L;

    private static final Long DEFAULT_SUPERVISORY_UNIT_ID = 1L;
    private static final Long UPDATED_SUPERVISORY_UNIT_ID = 2L;

    private static final Boolean DEFAULT_STATUS_ID = false;
    private static final Boolean UPDATED_STATUS_ID = true;

    private static final SpecifyingCells DEFAULT_SPECIFYING_CELLS = SpecifyingCells.BIURO;
    private static final SpecifyingCells UPDATED_SPECIFYING_CELLS = SpecifyingCells.URZAD;

    @Autowired
    private OrganisationStructureRepository organisationStructureRepository;

    @Autowired
    private OrganisationStructureService organisationStructureService;

    @Autowired
    private GlossaryOfCommercialRisksService glossaryOfCommercialRisksService;

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

    private MockMvc restOrganisationStructureMockMvc;

    private OrganisationStructure organisationStructure;

    @Autowired
    private GlossaryOfPurposesService glossaryOfPurposesService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrganisationStructureResource organisationStructureResource = new OrganisationStructureResource();
        this.restOrganisationStructureMockMvc = MockMvcBuilders.standaloneSetup(organisationStructureResource)
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
    public static OrganisationStructure createEntity(EntityManager em) {
        OrganisationStructure organisationStructure = new OrganisationStructure()
            .name(DEFAULT_NAME)
            .parentId(DEFAULT_PARENT_ID)
            .supervisoryUnitId(DEFAULT_SUPERVISORY_UNIT_ID)
            .statusId(DEFAULT_STATUS_ID)
            .specifyingCells(DEFAULT_SPECIFYING_CELLS);
        return organisationStructure;
    }

    @Before
    public void initTest() {
        organisationStructure = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrganisationStructure() throws Exception {
        int databaseSizeBeforeCreate = organisationStructureRepository.findAll().size();

        // Create the OrganisationStructure
        restOrganisationStructureMockMvc.perform(post("/api/organisation-structures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organisationStructure)))
            .andExpect(status().isCreated());

        // Validate the OrganisationStructure in the database
        List<OrganisationStructure> organisationStructureList = organisationStructureRepository.findAll();
        assertThat(organisationStructureList).hasSize(databaseSizeBeforeCreate + 1);
        OrganisationStructure testOrganisationStructure = organisationStructureList.get(organisationStructureList.size() - 1);
        assertThat(testOrganisationStructure.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrganisationStructure.getParentId()).isEqualTo(DEFAULT_PARENT_ID);
        assertThat(testOrganisationStructure.getSupervisoryUnitId()).isEqualTo(DEFAULT_SUPERVISORY_UNIT_ID);
        assertThat(testOrganisationStructure.isStatusId()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testOrganisationStructure.getSpecifyingCells()).isEqualTo(DEFAULT_SPECIFYING_CELLS);
    }

    @Test
    @Transactional
    public void createOrganisationStructureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = organisationStructureRepository.findAll().size();

        // Create the OrganisationStructure with an existing ID
        organisationStructure.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganisationStructureMockMvc.perform(post("/api/organisation-structures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organisationStructure)))
            .andExpect(status().isBadRequest());

        // Validate the OrganisationStructure in the database
        List<OrganisationStructure> organisationStructureList = organisationStructureRepository.findAll();
        assertThat(organisationStructureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOrganisationStructures() throws Exception {
        // Initialize the database
        organisationStructureRepository.saveAndFlush(organisationStructure);

        // Get all the organisationStructureList
        restOrganisationStructureMockMvc.perform(get("/api/organisation-structures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organisationStructure.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].supervisoryUnitId").value(hasItem(DEFAULT_SUPERVISORY_UNIT_ID.intValue())))
            .andExpect(jsonPath("$.[*].statusId").value(hasItem(DEFAULT_STATUS_ID.booleanValue())))
            .andExpect(jsonPath("$.[*].specifyingCells").value(hasItem(DEFAULT_SPECIFYING_CELLS.toString())));
    }

    @Test
    @Transactional
    public void getOrganisationStructure() throws Exception {
        // Initialize the database
        organisationStructureRepository.saveAndFlush(organisationStructure);

        // Get the organisationStructure
        restOrganisationStructureMockMvc.perform(get("/api/organisation-structures/{id}", organisationStructure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(organisationStructure.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.parentId").value(DEFAULT_PARENT_ID.intValue()))
            .andExpect(jsonPath("$.supervisoryUnitId").value(DEFAULT_SUPERVISORY_UNIT_ID.intValue()))
            .andExpect(jsonPath("$.statusId").value(DEFAULT_STATUS_ID.booleanValue()))
            .andExpect(jsonPath("$.specifyingCells").value(DEFAULT_SPECIFYING_CELLS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrganisationStructure() throws Exception {
        // Get the organisationStructure
        restOrganisationStructureMockMvc.perform(get("/api/organisation-structures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrganisationStructure() throws Exception {
        // Initialize the database
        organisationStructureService.save(organisationStructure);

        int databaseSizeBeforeUpdate = organisationStructureRepository.findAll().size();

        // Update the organisationStructure
        OrganisationStructure updatedOrganisationStructure = organisationStructureRepository.findOne(organisationStructure.getId());
        updatedOrganisationStructure
            .name(UPDATED_NAME)
            .parentId(UPDATED_PARENT_ID)
            .supervisoryUnitId(UPDATED_SUPERVISORY_UNIT_ID)
            .statusId(UPDATED_STATUS_ID)
            .specifyingCells(UPDATED_SPECIFYING_CELLS);

        restOrganisationStructureMockMvc.perform(put("/api/organisation-structures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrganisationStructure)))
            .andExpect(status().isOk());

        // Validate the OrganisationStructure in the database
        List<OrganisationStructure> organisationStructureList = organisationStructureRepository.findAll();
        assertThat(organisationStructureList).hasSize(databaseSizeBeforeUpdate);
        OrganisationStructure testOrganisationStructure = organisationStructureList.get(organisationStructureList.size() - 1);
        assertThat(testOrganisationStructure.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrganisationStructure.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testOrganisationStructure.getSupervisoryUnitId()).isEqualTo(UPDATED_SUPERVISORY_UNIT_ID);
        assertThat(testOrganisationStructure.isStatusId()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testOrganisationStructure.getSpecifyingCells()).isEqualTo(UPDATED_SPECIFYING_CELLS);
    }

    @Test
    @Transactional
    public void updateNonExistingOrganisationStructure() throws Exception {
        int databaseSizeBeforeUpdate = organisationStructureRepository.findAll().size();

        // Create the OrganisationStructure

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrganisationStructureMockMvc.perform(put("/api/organisation-structures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organisationStructure)))
            .andExpect(status().isCreated());

        // Validate the OrganisationStructure in the database
        List<OrganisationStructure> organisationStructureList = organisationStructureRepository.findAll();
        assertThat(organisationStructureList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOrganisationStructure() throws Exception {
        // Initialize the database
        organisationStructureService.save(organisationStructure);

        int databaseSizeBeforeDelete = organisationStructureRepository.findAll().size();

        // Get the organisationStructure
        restOrganisationStructureMockMvc.perform(delete("/api/organisation-structures/{id}", organisationStructure.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OrganisationStructure> organisationStructureList = organisationStructureRepository.findAll();
        assertThat(organisationStructureList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganisationStructure.class);
        OrganisationStructure organisationStructure1 = new OrganisationStructure();
        organisationStructure1.setId(1L);
        OrganisationStructure organisationStructure2 = new OrganisationStructure();
        organisationStructure2.setId(organisationStructure1.getId());
        assertThat(organisationStructure1).isEqualTo(organisationStructure2);
        organisationStructure2.setId(2L);
        assertThat(organisationStructure1).isNotEqualTo(organisationStructure2);
        organisationStructure1.setId(null);
        assertThat(organisationStructure1).isNotEqualTo(organisationStructure2);
    }
}
