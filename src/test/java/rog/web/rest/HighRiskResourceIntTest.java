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
import rog.domain.DecisionForRisk;
import rog.domain.HighRisk;
import rog.domain.enumeration.AnalyzeOfRisk;
import rog.repository.HighRiskRepository;
import rog.service.HighRiskService;
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
 * Test class for the HighRiskResource REST controller.
 *
 * @see HighRiskResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RorumApp.class)
public class HighRiskResourceIntTest {

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
    private HighRiskRepository highRiskRepository;

    @Autowired
    private HighRiskService highRiskService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHighRiskMockMvc;

    private HighRisk highRisk;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HighRiskResource highRiskResource = new HighRiskResource();
        this.restHighRiskMockMvc = MockMvcBuilders.standaloneSetup(highRiskResource)
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
    public static HighRisk createEntity(EntityManager em) {
        HighRisk highRisk = new HighRisk()
            .rejectToAccomplishPurpose(DEFAULT_REJECT_TO_ACCOMPLISH_PURPOSE)
            .postponePurposeAccomplishment(DEFAULT_POSTPONE_PURPOSE_ACCOMPLISHMENT)
            .restrictRangeOfPurposeAccomplishment(DEFAULT_RESTRICT_RANGE_OF_PURPOSE_ACCOMPLISHMENT)
            .costOfListedPossibilities(DEFAULT_COST_OF_LISTED_POSSIBILITIES)
            .probabilityToReach(DEFAULT_PROBABILITY_TO_REACH)
            .powerOfInfluenceToReach(DEFAULT_POWER_OF_INFLUENCE_TO_REACH)
            .analyze(DEFAULT_ANALYZE)
            .substantiationForAnalyze(DEFAULT_SUBSTANTIATION_FOR_ANALYZE)
            .commentToHighRiskProcedure(DEFAULT_COMMENT_TO_HIGH_RISK_PROCEDURE);
        // Add required entity
        DecisionForRisk decisionForRisk = DecisionForRiskResourceIntTest.createEntity(em);
        em.persist(decisionForRisk);
        em.flush();
        highRisk.setDecisionForRisk(decisionForRisk);
        return highRisk;
    }

    @Before
    public void initTest() {
        highRisk = createEntity(em);
    }

    @Test
    @Transactional
    public void createHighRisk() throws Exception {
        int databaseSizeBeforeCreate = highRiskRepository.findAll().size();

        // Create the HighRisk
        restHighRiskMockMvc.perform(post("/api/high-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(highRisk)))
            .andExpect(status().isCreated());

        // Validate the HighRisk in the database
        List<HighRisk> highRiskList = highRiskRepository.findAll();
        assertThat(highRiskList).hasSize(databaseSizeBeforeCreate + 1);
        HighRisk testHighRisk = highRiskList.get(highRiskList.size() - 1);
        assertThat(testHighRisk.isRejectToAccomplishPurpose()).isEqualTo(DEFAULT_REJECT_TO_ACCOMPLISH_PURPOSE);
        assertThat(testHighRisk.isPostponePurposeAccomplishment()).isEqualTo(DEFAULT_POSTPONE_PURPOSE_ACCOMPLISHMENT);
        assertThat(testHighRisk.isRestrictRangeOfPurposeAccomplishment()).isEqualTo(DEFAULT_RESTRICT_RANGE_OF_PURPOSE_ACCOMPLISHMENT);
        assertThat(testHighRisk.getCostOfListedPossibilities()).isEqualTo(DEFAULT_COST_OF_LISTED_POSSIBILITIES);
        assertThat(testHighRisk.getProjectedTermDeployStart()).isEqualTo(DEFAULT_PROJECTED_TERM_DEPLOY_START);
        assertThat(testHighRisk.getProjectedTermDeployFinish()).isEqualTo(DEFAULT_PROJECTED_TERM_DEPLOY_FINISH);
        assertThat(testHighRisk.getProbabilityToReach()).isEqualTo(DEFAULT_PROBABILITY_TO_REACH);
        assertThat(testHighRisk.getPowerOfInfluenceToReach()).isEqualTo(DEFAULT_POWER_OF_INFLUENCE_TO_REACH);
        assertThat(testHighRisk.getAnalyze()).isEqualTo(DEFAULT_ANALYZE);
        assertThat(testHighRisk.getSubstantiationForAnalyze()).isEqualTo(DEFAULT_SUBSTANTIATION_FOR_ANALYZE);
        assertThat(testHighRisk.getCommentToHighRiskProcedure()).isEqualTo(DEFAULT_COMMENT_TO_HIGH_RISK_PROCEDURE);
    }

    @Test
    @Transactional
    public void createHighRiskWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = highRiskRepository.findAll().size();

        // Create the HighRisk with an existing ID
        highRisk.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHighRiskMockMvc.perform(post("/api/high-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(highRisk)))
            .andExpect(status().isBadRequest());

        // Validate the HighRisk in the database
        List<HighRisk> highRiskList = highRiskRepository.findAll();
        assertThat(highRiskList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRejectToAccomplishPurposeIsRequired() throws Exception {
        int databaseSizeBeforeTest = highRiskRepository.findAll().size();
        // set the field null
        highRisk.setRejectToAccomplishPurpose(null);

        // Create the HighRisk, which fails.

        restHighRiskMockMvc.perform(post("/api/high-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(highRisk)))
            .andExpect(status().isBadRequest());

        List<HighRisk> highRiskList = highRiskRepository.findAll();
        assertThat(highRiskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostponePurposeAccomplishmentIsRequired() throws Exception {
        int databaseSizeBeforeTest = highRiskRepository.findAll().size();
        // set the field null
        highRisk.setPostponePurposeAccomplishment(null);

        // Create the HighRisk, which fails.

        restHighRiskMockMvc.perform(post("/api/high-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(highRisk)))
            .andExpect(status().isBadRequest());

        List<HighRisk> highRiskList = highRiskRepository.findAll();
        assertThat(highRiskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRestrictRangeOfPurposeAccomplishmentIsRequired() throws Exception {
        int databaseSizeBeforeTest = highRiskRepository.findAll().size();
        // set the field null
        highRisk.setRestrictRangeOfPurposeAccomplishment(null);

        // Create the HighRisk, which fails.

        restHighRiskMockMvc.perform(post("/api/high-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(highRisk)))
            .andExpect(status().isBadRequest());

        List<HighRisk> highRiskList = highRiskRepository.findAll();
        assertThat(highRiskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCostOfListedPossibilitiesIsRequired() throws Exception {
        int databaseSizeBeforeTest = highRiskRepository.findAll().size();
        // set the field null
        highRisk.setCostOfListedPossibilities(null);

        // Create the HighRisk, which fails.

        restHighRiskMockMvc.perform(post("/api/high-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(highRisk)))
            .andExpect(status().isBadRequest());

        List<HighRisk> highRiskList = highRiskRepository.findAll();
        assertThat(highRiskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProjectedTermDeployStartIsRequired() throws Exception {
        int databaseSizeBeforeTest = highRiskRepository.findAll().size();
        // set the field null
        highRisk.setProjectedTermDeployStart(null);

        // Create the HighRisk, which fails.

        restHighRiskMockMvc.perform(post("/api/high-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(highRisk)))
            .andExpect(status().isBadRequest());

        List<HighRisk> highRiskList = highRiskRepository.findAll();
        assertThat(highRiskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProjectedTermDeployFinishIsRequired() throws Exception {
        int databaseSizeBeforeTest = highRiskRepository.findAll().size();
        // set the field null
        highRisk.setProjectedTermDeployFinish(null);

        // Create the HighRisk, which fails.

        restHighRiskMockMvc.perform(post("/api/high-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(highRisk)))
            .andExpect(status().isBadRequest());

        List<HighRisk> highRiskList = highRiskRepository.findAll();
        assertThat(highRiskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProbabilityToReachIsRequired() throws Exception {
        int databaseSizeBeforeTest = highRiskRepository.findAll().size();
        // set the field null
        highRisk.setProbabilityToReach(null);

        // Create the HighRisk, which fails.

        restHighRiskMockMvc.perform(post("/api/high-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(highRisk)))
            .andExpect(status().isBadRequest());

        List<HighRisk> highRiskList = highRiskRepository.findAll();
        assertThat(highRiskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPowerOfInfluenceToReachIsRequired() throws Exception {
        int databaseSizeBeforeTest = highRiskRepository.findAll().size();
        // set the field null
        highRisk.setPowerOfInfluenceToReach(null);

        // Create the HighRisk, which fails.

        restHighRiskMockMvc.perform(post("/api/high-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(highRisk)))
            .andExpect(status().isBadRequest());

        List<HighRisk> highRiskList = highRiskRepository.findAll();
        assertThat(highRiskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnalyzeIsRequired() throws Exception {
        int databaseSizeBeforeTest = highRiskRepository.findAll().size();
        // set the field null
        highRisk.setAnalyze(null);

        // Create the HighRisk, which fails.

        restHighRiskMockMvc.perform(post("/api/high-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(highRisk)))
            .andExpect(status().isBadRequest());

        List<HighRisk> highRiskList = highRiskRepository.findAll();
        assertThat(highRiskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSubstantiationForAnalyzeIsRequired() throws Exception {
        int databaseSizeBeforeTest = highRiskRepository.findAll().size();
        // set the field null
        highRisk.setSubstantiationForAnalyze(null);

        // Create the HighRisk, which fails.

        restHighRiskMockMvc.perform(post("/api/high-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(highRisk)))
            .andExpect(status().isBadRequest());

        List<HighRisk> highRiskList = highRiskRepository.findAll();
        assertThat(highRiskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCommentToHighRiskProcedureIsRequired() throws Exception {
        int databaseSizeBeforeTest = highRiskRepository.findAll().size();
        // set the field null
        highRisk.setCommentToHighRiskProcedure(null);

        // Create the HighRisk, which fails.

        restHighRiskMockMvc.perform(post("/api/high-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(highRisk)))
            .andExpect(status().isBadRequest());

        List<HighRisk> highRiskList = highRiskRepository.findAll();
        assertThat(highRiskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHighRisks() throws Exception {
        // Initialize the database
        highRiskRepository.saveAndFlush(highRisk);

        // Get all the highRiskList
        restHighRiskMockMvc.perform(get("/api/high-risks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(highRisk.getId().intValue())))
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
    public void getHighRisk() throws Exception {
        // Initialize the database
        highRiskRepository.saveAndFlush(highRisk);

        // Get the highRisk
        restHighRiskMockMvc.perform(get("/api/high-risks/{id}", highRisk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(highRisk.getId().intValue()))
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
    public void getNonExistingHighRisk() throws Exception {
        // Get the highRisk
        restHighRiskMockMvc.perform(get("/api/high-risks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHighRisk() throws Exception {
        // Initialize the database
        highRiskService.save(highRisk);

        int databaseSizeBeforeUpdate = highRiskRepository.findAll().size();

        // Update the highRisk
        HighRisk updatedHighRisk = highRiskRepository.findOne(highRisk.getId());
        updatedHighRisk
            .rejectToAccomplishPurpose(UPDATED_REJECT_TO_ACCOMPLISH_PURPOSE)
            .postponePurposeAccomplishment(UPDATED_POSTPONE_PURPOSE_ACCOMPLISHMENT)
            .restrictRangeOfPurposeAccomplishment(UPDATED_RESTRICT_RANGE_OF_PURPOSE_ACCOMPLISHMENT)
            .costOfListedPossibilities(UPDATED_COST_OF_LISTED_POSSIBILITIES)
            .probabilityToReach(UPDATED_PROBABILITY_TO_REACH)
            .powerOfInfluenceToReach(UPDATED_POWER_OF_INFLUENCE_TO_REACH)
            .analyze(UPDATED_ANALYZE)
            .substantiationForAnalyze(UPDATED_SUBSTANTIATION_FOR_ANALYZE)
            .commentToHighRiskProcedure(UPDATED_COMMENT_TO_HIGH_RISK_PROCEDURE);

        restHighRiskMockMvc.perform(put("/api/high-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHighRisk)))
            .andExpect(status().isOk());

        // Validate the HighRisk in the database
        List<HighRisk> highRiskList = highRiskRepository.findAll();
        assertThat(highRiskList).hasSize(databaseSizeBeforeUpdate);
        HighRisk testHighRisk = highRiskList.get(highRiskList.size() - 1);
        assertThat(testHighRisk.isRejectToAccomplishPurpose()).isEqualTo(UPDATED_REJECT_TO_ACCOMPLISH_PURPOSE);
        assertThat(testHighRisk.isPostponePurposeAccomplishment()).isEqualTo(UPDATED_POSTPONE_PURPOSE_ACCOMPLISHMENT);
        assertThat(testHighRisk.isRestrictRangeOfPurposeAccomplishment()).isEqualTo(UPDATED_RESTRICT_RANGE_OF_PURPOSE_ACCOMPLISHMENT);
        assertThat(testHighRisk.getCostOfListedPossibilities()).isEqualTo(UPDATED_COST_OF_LISTED_POSSIBILITIES);
        assertThat(testHighRisk.getProjectedTermDeployStart()).isEqualTo(UPDATED_PROJECTED_TERM_DEPLOY_START);
        assertThat(testHighRisk.getProjectedTermDeployFinish()).isEqualTo(UPDATED_PROJECTED_TERM_DEPLOY_FINISH);
        assertThat(testHighRisk.getProbabilityToReach()).isEqualTo(UPDATED_PROBABILITY_TO_REACH);
        assertThat(testHighRisk.getPowerOfInfluenceToReach()).isEqualTo(UPDATED_POWER_OF_INFLUENCE_TO_REACH);
        assertThat(testHighRisk.getAnalyze()).isEqualTo(UPDATED_ANALYZE);
        assertThat(testHighRisk.getSubstantiationForAnalyze()).isEqualTo(UPDATED_SUBSTANTIATION_FOR_ANALYZE);
        assertThat(testHighRisk.getCommentToHighRiskProcedure()).isEqualTo(UPDATED_COMMENT_TO_HIGH_RISK_PROCEDURE);
    }

    @Test
    @Transactional
    public void updateNonExistingHighRisk() throws Exception {
        int databaseSizeBeforeUpdate = highRiskRepository.findAll().size();

        // Create the HighRisk

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHighRiskMockMvc.perform(put("/api/high-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(highRisk)))
            .andExpect(status().isCreated());

        // Validate the HighRisk in the database
        List<HighRisk> highRiskList = highRiskRepository.findAll();
        assertThat(highRiskList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHighRisk() throws Exception {
        // Initialize the database
        highRiskService.save(highRisk);

        int databaseSizeBeforeDelete = highRiskRepository.findAll().size();

        // Get the highRisk
        restHighRiskMockMvc.perform(delete("/api/high-risks/{id}", highRisk.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<HighRisk> highRiskList = highRiskRepository.findAll();
        assertThat(highRiskList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HighRisk.class);
        HighRisk highRisk1 = new HighRisk();
        highRisk1.setId(1L);
        HighRisk highRisk2 = new HighRisk();
        highRisk2.setId(highRisk1.getId());
        assertThat(highRisk1).isEqualTo(highRisk2);
        highRisk2.setId(2L);
        assertThat(highRisk1).isNotEqualTo(highRisk2);
        highRisk1.setId(null);
        assertThat(highRisk1).isNotEqualTo(highRisk2);
    }
}
