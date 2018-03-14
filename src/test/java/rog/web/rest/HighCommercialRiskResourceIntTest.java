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
import rog.domain.HighCommercialRisk;
import rog.domain.enumeration.AnalyzeOfRisk;
import rog.repository.HighCommercialRiskRepository;
import rog.service.HighCommercialRiskService;
import rog.web.rest.errors.ExceptionTranslator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static rog.web.rest.TestUtil.createFormattingConversionService;
/**
 * Test class for the HighCommercialRiskResource REST controller.
 *
 * @see HighCommercialRiskResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RorumApp.class)
public class HighCommercialRiskResourceIntTest {

    private static final Boolean DEFAULT_REJECT_TO_ACCOMPLISH_PURPOSE = false;
    private static final Boolean UPDATED_REJECT_TO_ACCOMPLISH_PURPOSE = true;

    private static final Boolean DEFAULT_POSTPONE_PURPOSE_ACCOMPLISHMENT = false;
    private static final Boolean UPDATED_POSTPONE_PURPOSE_ACCOMPLISHMENT = true;

    private static final Boolean DEFAULT_RESTRICT_RANGE_OF_PURPOSE_ACCOMPLISHMENT = false;
    private static final Boolean UPDATED_RESTRICT_RANGE_OF_PURPOSE_ACCOMPLISHMENT = true;

    private static final BigDecimal DEFAULT_COST_OF_LISTED_POSSIBILITIES = new BigDecimal(1);
    private static final BigDecimal UPDATED_COST_OF_LISTED_POSSIBILITIES = new BigDecimal(2);

    private static final Integer DEFAULT_PROJECTED_TERM_DEPLOY_START = 1;
    private static final Integer UPDATED_PROJECTED_TERM_DEPLOY_START = 2;

    private static final Integer DEFAULT_PROJECTED_TERM_DEPLOY_FINISH = 1;
    private static final Integer UPDATED_PROJECTED_TERM_DEPLOY_FINISH = 2;

    private static final Integer DEFAULT_PROBABILITY_TO_REACH = 1;
    private static final Integer UPDATED_PROBABILITY_TO_REACH = 2;

    private static final Integer DEFAULT_POWER_OF_INFLUENCE_TO_REACH = 1;
    private static final Integer UPDATED_POWER_OF_INFLUENCE_TO_REACH = 2;

    private static final AnalyzeOfRisk DEFAULT_ANALYZE = AnalyzeOfRisk.COSTS_MORE_BENEFITS;
    private static final AnalyzeOfRisk UPDATED_ANALYZE = AnalyzeOfRisk.COSTS_ARE_EQUALED_TO_BENEFITS;

    private static final String DEFAULT_SUBSTANTIATION_FOR_ANALYZE = "AAAAAAAAAA";
    private static final String UPDATED_SUBSTANTIATION_FOR_ANALYZE = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT_TO_HIGH_RISK_PROCEDURE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT_TO_HIGH_RISK_PROCEDURE = "BBBBBBBBBB";

    @Autowired
    private HighCommercialRiskRepository highCommercialRiskRepository;

    @Autowired
    private HighCommercialRiskService highCommercialRiskService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHighCommercialRiskMockMvc;

    private HighCommercialRisk highCommercialRisk;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HighCommercialRiskResource highCommercialRiskResource = new HighCommercialRiskResource(highCommercialRiskService);
        this.restHighCommercialRiskMockMvc = MockMvcBuilders.standaloneSetup(highCommercialRiskResource)
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
    public static HighCommercialRisk createEntity(EntityManager em) {
        HighCommercialRisk highCommercialRisk = new HighCommercialRisk()
            .rejectToAccomplishPurpose(DEFAULT_REJECT_TO_ACCOMPLISH_PURPOSE)
            .postponePurposeAccomplishment(DEFAULT_POSTPONE_PURPOSE_ACCOMPLISHMENT)
            .restrictRangeOfPurposeAccomplishment(DEFAULT_RESTRICT_RANGE_OF_PURPOSE_ACCOMPLISHMENT)
            .costOfListedPossibilities(DEFAULT_COST_OF_LISTED_POSSIBILITIES)
            .probabilityToReach(DEFAULT_PROBABILITY_TO_REACH)
            .powerOfInfluenceToReach(DEFAULT_POWER_OF_INFLUENCE_TO_REACH)
            .analyze(DEFAULT_ANALYZE)
            .substantiationForAnalyze(DEFAULT_SUBSTANTIATION_FOR_ANALYZE)
            .commentToHighRiskProcedure(DEFAULT_COMMENT_TO_HIGH_RISK_PROCEDURE);
        return highCommercialRisk;
    }

    @Before
    public void initTest() {
        highCommercialRisk = createEntity(em);
    }

    @Test
    @Transactional
    public void createHighCommercialRisk() throws Exception {
        int databaseSizeBeforeCreate = highCommercialRiskRepository.findAll().size();

        // Create the HighCommercialRisk
        restHighCommercialRiskMockMvc.perform(post("/api/high-commercial-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(highCommercialRisk)))
            .andExpect(status().isCreated());

        // Validate the HighCommercialRisk in the database
        List<HighCommercialRisk> highCommercialRiskList = highCommercialRiskRepository.findAll();
        assertThat(highCommercialRiskList).hasSize(databaseSizeBeforeCreate + 1);
        HighCommercialRisk testHighCommercialRisk = highCommercialRiskList.get(highCommercialRiskList.size() - 1);
        assertThat(testHighCommercialRisk.isRejectToAccomplishPurpose()).isEqualTo(DEFAULT_REJECT_TO_ACCOMPLISH_PURPOSE);
        assertThat(testHighCommercialRisk.isPostponePurposeAccomplishment()).isEqualTo(DEFAULT_POSTPONE_PURPOSE_ACCOMPLISHMENT);
        assertThat(testHighCommercialRisk.isRestrictRangeOfPurposeAccomplishment()).isEqualTo(DEFAULT_RESTRICT_RANGE_OF_PURPOSE_ACCOMPLISHMENT);
        assertThat(testHighCommercialRisk.getCostOfListedPossibilities()).isEqualTo(DEFAULT_COST_OF_LISTED_POSSIBILITIES);
        assertThat(testHighCommercialRisk.getProjectedTermDeployStart()).isEqualTo(DEFAULT_PROJECTED_TERM_DEPLOY_START);
        assertThat(testHighCommercialRisk.getProjectedTermDeployFinish()).isEqualTo(DEFAULT_PROJECTED_TERM_DEPLOY_FINISH);
        assertThat(testHighCommercialRisk.getProbabilityToReach()).isEqualTo(DEFAULT_PROBABILITY_TO_REACH);
        assertThat(testHighCommercialRisk.getPowerOfInfluenceToReach()).isEqualTo(DEFAULT_POWER_OF_INFLUENCE_TO_REACH);
        assertThat(testHighCommercialRisk.getAnalyze()).isEqualTo(DEFAULT_ANALYZE);
        assertThat(testHighCommercialRisk.getSubstantiationForAnalyze()).isEqualTo(DEFAULT_SUBSTANTIATION_FOR_ANALYZE);
        assertThat(testHighCommercialRisk.getCommentToHighRiskProcedure()).isEqualTo(DEFAULT_COMMENT_TO_HIGH_RISK_PROCEDURE);
    }

    @Test
    @Transactional
    public void createHighCommercialRiskWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = highCommercialRiskRepository.findAll().size();

        // Create the HighCommercialRisk with an existing ID
        highCommercialRisk.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHighCommercialRiskMockMvc.perform(post("/api/high-commercial-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(highCommercialRisk)))
            .andExpect(status().isBadRequest());

        // Validate the HighCommercialRisk in the database
        List<HighCommercialRisk> highCommercialRiskList = highCommercialRiskRepository.findAll();
        assertThat(highCommercialRiskList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRejectToAccomplishPurposeIsRequired() throws Exception {
        int databaseSizeBeforeTest = highCommercialRiskRepository.findAll().size();
        // set the field null
        highCommercialRisk.setRejectToAccomplishPurpose(null);

        // Create the HighCommercialRisk, which fails.

        restHighCommercialRiskMockMvc.perform(post("/api/high-commercial-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(highCommercialRisk)))
            .andExpect(status().isBadRequest());

        List<HighCommercialRisk> highCommercialRiskList = highCommercialRiskRepository.findAll();
        assertThat(highCommercialRiskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostponePurposeAccomplishmentIsRequired() throws Exception {
        int databaseSizeBeforeTest = highCommercialRiskRepository.findAll().size();
        // set the field null
        highCommercialRisk.setPostponePurposeAccomplishment(null);

        // Create the HighCommercialRisk, which fails.

        restHighCommercialRiskMockMvc.perform(post("/api/high-commercial-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(highCommercialRisk)))
            .andExpect(status().isBadRequest());

        List<HighCommercialRisk> highCommercialRiskList = highCommercialRiskRepository.findAll();
        assertThat(highCommercialRiskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRestrictRangeOfPurposeAccomplishmentIsRequired() throws Exception {
        int databaseSizeBeforeTest = highCommercialRiskRepository.findAll().size();
        // set the field null
        highCommercialRisk.setRestrictRangeOfPurposeAccomplishment(null);

        // Create the HighCommercialRisk, which fails.

        restHighCommercialRiskMockMvc.perform(post("/api/high-commercial-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(highCommercialRisk)))
            .andExpect(status().isBadRequest());

        List<HighCommercialRisk> highCommercialRiskList = highCommercialRiskRepository.findAll();
        assertThat(highCommercialRiskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCostOfListedPossibilitiesIsRequired() throws Exception {
        int databaseSizeBeforeTest = highCommercialRiskRepository.findAll().size();
        // set the field null
        highCommercialRisk.setCostOfListedPossibilities(null);

        // Create the HighCommercialRisk, which fails.

        restHighCommercialRiskMockMvc.perform(post("/api/high-commercial-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(highCommercialRisk)))
            .andExpect(status().isBadRequest());

        List<HighCommercialRisk> highCommercialRiskList = highCommercialRiskRepository.findAll();
        assertThat(highCommercialRiskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProjectedTermDeployStartIsRequired() throws Exception {
        int databaseSizeBeforeTest = highCommercialRiskRepository.findAll().size();
        // set the field null
        highCommercialRisk.setProjectedTermDeployStart(null);

        // Create the HighCommercialRisk, which fails.

        restHighCommercialRiskMockMvc.perform(post("/api/high-commercial-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(highCommercialRisk)))
            .andExpect(status().isBadRequest());

        List<HighCommercialRisk> highCommercialRiskList = highCommercialRiskRepository.findAll();
        assertThat(highCommercialRiskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProjectedTermDeployFinishIsRequired() throws Exception {
        int databaseSizeBeforeTest = highCommercialRiskRepository.findAll().size();
        // set the field null
        highCommercialRisk.setProjectedTermDeployFinish(null);

        // Create the HighCommercialRisk, which fails.

        restHighCommercialRiskMockMvc.perform(post("/api/high-commercial-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(highCommercialRisk)))
            .andExpect(status().isBadRequest());

        List<HighCommercialRisk> highCommercialRiskList = highCommercialRiskRepository.findAll();
        assertThat(highCommercialRiskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProbabilityToReachIsRequired() throws Exception {
        int databaseSizeBeforeTest = highCommercialRiskRepository.findAll().size();
        // set the field null
        highCommercialRisk.setProbabilityToReach(null);

        // Create the HighCommercialRisk, which fails.

        restHighCommercialRiskMockMvc.perform(post("/api/high-commercial-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(highCommercialRisk)))
            .andExpect(status().isBadRequest());

        List<HighCommercialRisk> highCommercialRiskList = highCommercialRiskRepository.findAll();
        assertThat(highCommercialRiskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPowerOfInfluenceToReachIsRequired() throws Exception {
        int databaseSizeBeforeTest = highCommercialRiskRepository.findAll().size();
        // set the field null
        highCommercialRisk.setPowerOfInfluenceToReach(null);

        // Create the HighCommercialRisk, which fails.

        restHighCommercialRiskMockMvc.perform(post("/api/high-commercial-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(highCommercialRisk)))
            .andExpect(status().isBadRequest());

        List<HighCommercialRisk> highCommercialRiskList = highCommercialRiskRepository.findAll();
        assertThat(highCommercialRiskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnalyzeIsRequired() throws Exception {
        int databaseSizeBeforeTest = highCommercialRiskRepository.findAll().size();
        // set the field null
        highCommercialRisk.setAnalyze(null);

        // Create the HighCommercialRisk, which fails.

        restHighCommercialRiskMockMvc.perform(post("/api/high-commercial-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(highCommercialRisk)))
            .andExpect(status().isBadRequest());

        List<HighCommercialRisk> highCommercialRiskList = highCommercialRiskRepository.findAll();
        assertThat(highCommercialRiskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSubstantiationForAnalyzeIsRequired() throws Exception {
        int databaseSizeBeforeTest = highCommercialRiskRepository.findAll().size();
        // set the field null
        highCommercialRisk.setSubstantiationForAnalyze(null);

        // Create the HighCommercialRisk, which fails.

        restHighCommercialRiskMockMvc.perform(post("/api/high-commercial-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(highCommercialRisk)))
            .andExpect(status().isBadRequest());

        List<HighCommercialRisk> highCommercialRiskList = highCommercialRiskRepository.findAll();
        assertThat(highCommercialRiskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCommentToHighRiskProcedureIsRequired() throws Exception {
        int databaseSizeBeforeTest = highCommercialRiskRepository.findAll().size();
        // set the field null
        highCommercialRisk.setCommentToHighRiskProcedure(null);

        // Create the HighCommercialRisk, which fails.

        restHighCommercialRiskMockMvc.perform(post("/api/high-commercial-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(highCommercialRisk)))
            .andExpect(status().isBadRequest());

        List<HighCommercialRisk> highCommercialRiskList = highCommercialRiskRepository.findAll();
        assertThat(highCommercialRiskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHighCommercialRisks() throws Exception {
        // Initialize the database
        highCommercialRiskRepository.saveAndFlush(highCommercialRisk);

        // Get all the highCommercialRiskList
        restHighCommercialRiskMockMvc.perform(get("/api/high-commercial-risks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(highCommercialRisk.getId().intValue())))
            .andExpect(jsonPath("$.[*].rejectToAccomplishPurpose").value(hasItem(DEFAULT_REJECT_TO_ACCOMPLISH_PURPOSE.booleanValue())))
            .andExpect(jsonPath("$.[*].postponePurposeAccomplishment").value(hasItem(DEFAULT_POSTPONE_PURPOSE_ACCOMPLISHMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].restrictRangeOfPurposeAccomplishment").value(hasItem(DEFAULT_RESTRICT_RANGE_OF_PURPOSE_ACCOMPLISHMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].costOfListedPossibilities").value(hasItem(DEFAULT_COST_OF_LISTED_POSSIBILITIES.intValue())))
            .andExpect(jsonPath("$.[*].projectedTermDeployStart").value(hasItem(DEFAULT_PROJECTED_TERM_DEPLOY_START)))
            .andExpect(jsonPath("$.[*].projectedTermDeployFinish").value(hasItem(DEFAULT_PROJECTED_TERM_DEPLOY_FINISH)))
            .andExpect(jsonPath("$.[*].probabilityToReach").value(hasItem(DEFAULT_PROBABILITY_TO_REACH)))
            .andExpect(jsonPath("$.[*].powerOfInfluenceToReach").value(hasItem(DEFAULT_POWER_OF_INFLUENCE_TO_REACH)))
            .andExpect(jsonPath("$.[*].analyze").value(hasItem(DEFAULT_ANALYZE.toString())))
            .andExpect(jsonPath("$.[*].substantiationForAnalyze").value(hasItem(DEFAULT_SUBSTANTIATION_FOR_ANALYZE.toString())))
            .andExpect(jsonPath("$.[*].commentToHighRiskProcedure").value(hasItem(DEFAULT_COMMENT_TO_HIGH_RISK_PROCEDURE.toString())));
    }

    @Test
    @Transactional
    public void getHighCommercialRisk() throws Exception {
        // Initialize the database
        highCommercialRiskRepository.saveAndFlush(highCommercialRisk);

        // Get the highCommercialRisk
        restHighCommercialRiskMockMvc.perform(get("/api/high-commercial-risks/{id}", highCommercialRisk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(highCommercialRisk.getId().intValue()))
            .andExpect(jsonPath("$.rejectToAccomplishPurpose").value(DEFAULT_REJECT_TO_ACCOMPLISH_PURPOSE.booleanValue()))
            .andExpect(jsonPath("$.postponePurposeAccomplishment").value(DEFAULT_POSTPONE_PURPOSE_ACCOMPLISHMENT.booleanValue()))
            .andExpect(jsonPath("$.restrictRangeOfPurposeAccomplishment").value(DEFAULT_RESTRICT_RANGE_OF_PURPOSE_ACCOMPLISHMENT.booleanValue()))
            .andExpect(jsonPath("$.costOfListedPossibilities").value(DEFAULT_COST_OF_LISTED_POSSIBILITIES.intValue()))
            .andExpect(jsonPath("$.projectedTermDeployStart").value(DEFAULT_PROJECTED_TERM_DEPLOY_START))
            .andExpect(jsonPath("$.projectedTermDeployFinish").value(DEFAULT_PROJECTED_TERM_DEPLOY_FINISH))
            .andExpect(jsonPath("$.probabilityToReach").value(DEFAULT_PROBABILITY_TO_REACH))
            .andExpect(jsonPath("$.powerOfInfluenceToReach").value(DEFAULT_POWER_OF_INFLUENCE_TO_REACH))
            .andExpect(jsonPath("$.analyze").value(DEFAULT_ANALYZE.toString()))
            .andExpect(jsonPath("$.substantiationForAnalyze").value(DEFAULT_SUBSTANTIATION_FOR_ANALYZE.toString()))
            .andExpect(jsonPath("$.commentToHighRiskProcedure").value(DEFAULT_COMMENT_TO_HIGH_RISK_PROCEDURE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHighCommercialRisk() throws Exception {
        // Get the highCommercialRisk
        restHighCommercialRiskMockMvc.perform(get("/api/high-commercial-risks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHighCommercialRisk() throws Exception {
        // Initialize the database
        highCommercialRiskService.save(highCommercialRisk);

        int databaseSizeBeforeUpdate = highCommercialRiskRepository.findAll().size();

        // Update the highCommercialRisk
        HighCommercialRisk updatedHighCommercialRisk = highCommercialRiskRepository.findOne(highCommercialRisk.getId());
        updatedHighCommercialRisk
            .rejectToAccomplishPurpose(UPDATED_REJECT_TO_ACCOMPLISH_PURPOSE)
            .postponePurposeAccomplishment(UPDATED_POSTPONE_PURPOSE_ACCOMPLISHMENT)
            .restrictRangeOfPurposeAccomplishment(UPDATED_RESTRICT_RANGE_OF_PURPOSE_ACCOMPLISHMENT)
            .costOfListedPossibilities(UPDATED_COST_OF_LISTED_POSSIBILITIES)
            .probabilityToReach(UPDATED_PROBABILITY_TO_REACH)
            .powerOfInfluenceToReach(UPDATED_POWER_OF_INFLUENCE_TO_REACH)
            .analyze(UPDATED_ANALYZE)
            .substantiationForAnalyze(UPDATED_SUBSTANTIATION_FOR_ANALYZE)
            .commentToHighRiskProcedure(UPDATED_COMMENT_TO_HIGH_RISK_PROCEDURE);

        restHighCommercialRiskMockMvc.perform(put("/api/high-commercial-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHighCommercialRisk)))
            .andExpect(status().isOk());

        // Validate the HighCommercialRisk in the database
        List<HighCommercialRisk> highCommercialRiskList = highCommercialRiskRepository.findAll();
        assertThat(highCommercialRiskList).hasSize(databaseSizeBeforeUpdate);
        HighCommercialRisk testHighCommercialRisk = highCommercialRiskList.get(highCommercialRiskList.size() - 1);
        assertThat(testHighCommercialRisk.isRejectToAccomplishPurpose()).isEqualTo(UPDATED_REJECT_TO_ACCOMPLISH_PURPOSE);
        assertThat(testHighCommercialRisk.isPostponePurposeAccomplishment()).isEqualTo(UPDATED_POSTPONE_PURPOSE_ACCOMPLISHMENT);
        assertThat(testHighCommercialRisk.isRestrictRangeOfPurposeAccomplishment()).isEqualTo(UPDATED_RESTRICT_RANGE_OF_PURPOSE_ACCOMPLISHMENT);
        assertThat(testHighCommercialRisk.getCostOfListedPossibilities()).isEqualTo(UPDATED_COST_OF_LISTED_POSSIBILITIES);
        assertThat(testHighCommercialRisk.getProjectedTermDeployStart()).isEqualTo(UPDATED_PROJECTED_TERM_DEPLOY_START);
        assertThat(testHighCommercialRisk.getProjectedTermDeployFinish()).isEqualTo(UPDATED_PROJECTED_TERM_DEPLOY_FINISH);
        assertThat(testHighCommercialRisk.getProbabilityToReach()).isEqualTo(UPDATED_PROBABILITY_TO_REACH);
        assertThat(testHighCommercialRisk.getPowerOfInfluenceToReach()).isEqualTo(UPDATED_POWER_OF_INFLUENCE_TO_REACH);
        assertThat(testHighCommercialRisk.getAnalyze()).isEqualTo(UPDATED_ANALYZE);
        assertThat(testHighCommercialRisk.getSubstantiationForAnalyze()).isEqualTo(UPDATED_SUBSTANTIATION_FOR_ANALYZE);
        assertThat(testHighCommercialRisk.getCommentToHighRiskProcedure()).isEqualTo(UPDATED_COMMENT_TO_HIGH_RISK_PROCEDURE);
    }

    @Test
    @Transactional
    public void updateNonExistingHighCommercialRisk() throws Exception {
        int databaseSizeBeforeUpdate = highCommercialRiskRepository.findAll().size();

        // Create the HighCommercialRisk

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHighCommercialRiskMockMvc.perform(put("/api/high-commercial-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(highCommercialRisk)))
            .andExpect(status().isCreated());

        // Validate the HighCommercialRisk in the database
        List<HighCommercialRisk> highCommercialRiskList = highCommercialRiskRepository.findAll();
        assertThat(highCommercialRiskList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHighCommercialRisk() throws Exception {
        // Initialize the database
        highCommercialRiskService.save(highCommercialRisk);

        int databaseSizeBeforeDelete = highCommercialRiskRepository.findAll().size();

        // Get the highCommercialRisk
        restHighCommercialRiskMockMvc.perform(delete("/api/high-commercial-risks/{id}", highCommercialRisk.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<HighCommercialRisk> highCommercialRiskList = highCommercialRiskRepository.findAll();
        assertThat(highCommercialRiskList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HighCommercialRisk.class);
        HighCommercialRisk highCommercialRisk1 = new HighCommercialRisk();
        highCommercialRisk1.setId(1L);
        HighCommercialRisk highCommercialRisk2 = new HighCommercialRisk();
        highCommercialRisk2.setId(highCommercialRisk1.getId());
        assertThat(highCommercialRisk1).isEqualTo(highCommercialRisk2);
        highCommercialRisk2.setId(2L);
        assertThat(highCommercialRisk1).isNotEqualTo(highCommercialRisk2);
        highCommercialRisk1.setId(null);
        assertThat(highCommercialRisk1).isNotEqualTo(highCommercialRisk2);
    }
}
