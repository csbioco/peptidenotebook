package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PeptidenotebookApp;

import com.mycompany.myapp.domain.Calculated;
import com.mycompany.myapp.repository.CalculatedRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import com.mycompany.myapp.service.CalculationAll;
import com.mycompany.myapp.service.UserService;
import com.mycompany.myapp.repository.CalculatedEntryRepository;


import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CalculatedResource REST controller.
 *
 * @see CalculatedResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PeptidenotebookApp.class)
public class CalculatedResourceIntTest {

    private static final String DEFAULT_SEQUENCE = "AAAAAAAAAA";
    private static final String UPDATED_SEQUENCE = "BBBBBBBBBB";

    private static final Integer DEFAULT_SC = 1;
    private static final Integer UPDATED_SC = 2;

    private static final Integer DEFAULT_DC = 1;
    private static final Integer UPDATED_DC = 2;

    private static final Integer DEFAULT_SCALE = 1;
    private static final Integer UPDATED_SCALE = 2;

    private static final Double DEFAULT_PI = 1D;
    private static final Double UPDATED_PI = 2D;

    private static final Double DEFAULT_NETCHARGE = 1D;
    private static final Double UPDATED_NETCHARGE = 2D;

    private static final String DEFAULT_SOLUBILITY = "AAAAAAAAAA";
    private static final String UPDATED_SOLUBILITY = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMC = 1;
    private static final Integer UPDATED_NUMC = 2;

    private static final Integer DEFAULT_NUMH = 1;
    private static final Integer UPDATED_NUMH = 2;

    private static final Integer DEFAULT_NUMN = 1;
    private static final Integer UPDATED_NUMN = 2;

    private static final Integer DEFAULT_NUMO = 1;
    private static final Integer UPDATED_NUMO = 2;

    private static final Integer DEFAULT_NUMS = 1;
    private static final Integer UPDATED_NUMS = 2;

    private static final Double DEFAULT_STARTRESIN = 1D;
    private static final Double UPDATED_STARTRESIN = 2D;

    private static final Double DEFAULT_COSTRESIN = 1D;
    private static final Double UPDATED_COSTRESIN = 2D;

    private static final Double DEFAULT_WEIGHTGAIN = 1D;
    private static final Double UPDATED_WEIGHTGAIN = 2D;

    private static final Double DEFAULT_TOTALWEIGHT = 1D;
    private static final Double UPDATED_TOTALWEIGHT = 2D;

    private static final Double DEFAULT_COSTAA = 1D;
    private static final Double UPDATED_COSTAA = 2D;

    private static final Double DEFAULT_SUMEACHAAWEIGHT = 1D;
    private static final Double UPDATED_SUMEACHAAWEIGHT = 2D;

    private static final Double DEFAULT_RESINUNITPRICE = 1D;
    private static final Double UPDATED_RESINUNITPRICE = 2D;

    private static final Double DEFAULT_SUBSTITUDE = 1D;
    private static final Double UPDATED_SUBSTITUDE = 2D;

    private static final Integer DEFAULT_BOUND = 1;
    private static final Integer UPDATED_BOUND = 2;

    private static final Double DEFAULT_COSTWASTE = 1D;
    private static final Double UPDATED_COSTWASTE = 2D;

    private static final String DEFAULT_PROTOCOLNAME = "AAAAAAAAAA";
    private static final String UPDATED_PROTOCOLNAME = "BBBBBBBBBB";

    @Autowired
    private CalculatedRepository calculatedRepository;

    @Autowired
    private CalculationAll calculationAll;
    @Autowired
    private UserService userService;
    @Autowired
    private CalculatedEntryRepository calculatedEntryRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCalculatedMockMvc;

    private Calculated calculated;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CalculatedResource calculatedResource = new CalculatedResource(calculatedRepository, calculationAll, userService, calculatedEntryRepository);
        this.restCalculatedMockMvc = MockMvcBuilders.standaloneSetup(calculatedResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Calculated createEntity(EntityManager em) {
        Calculated calculated = new Calculated()
            .sequence(DEFAULT_SEQUENCE)
            .sc(DEFAULT_SC)
            .dc(DEFAULT_DC)
            .scale(DEFAULT_SCALE)
            .pi(DEFAULT_PI)
            .netcharge(DEFAULT_NETCHARGE)
            .solubility(DEFAULT_SOLUBILITY)
            .numc(DEFAULT_NUMC)
            .numh(DEFAULT_NUMH)
            .numn(DEFAULT_NUMN)
            .numo(DEFAULT_NUMO)
            .nums(DEFAULT_NUMS)
            .startresin(DEFAULT_STARTRESIN)
            .costresin(DEFAULT_COSTRESIN)
            .weightgain(DEFAULT_WEIGHTGAIN)
            .totalweight(DEFAULT_TOTALWEIGHT)
            .costaa(DEFAULT_COSTAA)
            .sumeachaaweight(DEFAULT_SUMEACHAAWEIGHT)
            .resinunitprice(DEFAULT_RESINUNITPRICE)
            .substitude(DEFAULT_SUBSTITUDE)
            .bound(DEFAULT_BOUND)
            .costwaste(DEFAULT_COSTWASTE)
            .protocolname(DEFAULT_PROTOCOLNAME);
        return calculated;
    }

    @Before
    public void initTest() {
        calculated = createEntity(em);
    }

    @Test
    @Transactional
    public void createCalculated() throws Exception {
        int databaseSizeBeforeCreate = calculatedRepository.findAll().size();

        // Create the Calculated
        restCalculatedMockMvc.perform(post("/api/calculateds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calculated)))
            .andExpect(status().isCreated());

        // Validate the Calculated in the database
        List<Calculated> calculatedList = calculatedRepository.findAll();
        assertThat(calculatedList).hasSize(databaseSizeBeforeCreate + 1);
        Calculated testCalculated = calculatedList.get(calculatedList.size() - 1);
        assertThat(testCalculated.getSequence()).isEqualTo(DEFAULT_SEQUENCE);
        assertThat(testCalculated.getSc()).isEqualTo(DEFAULT_SC);
        assertThat(testCalculated.getDc()).isEqualTo(DEFAULT_DC);
        assertThat(testCalculated.getScale()).isEqualTo(DEFAULT_SCALE);
        assertThat(testCalculated.getPi()).isEqualTo(DEFAULT_PI);
        assertThat(testCalculated.getNetcharge()).isEqualTo(DEFAULT_NETCHARGE);
        assertThat(testCalculated.getSolubility()).isEqualTo(DEFAULT_SOLUBILITY);
        assertThat(testCalculated.getNumc()).isEqualTo(DEFAULT_NUMC);
        assertThat(testCalculated.getNumh()).isEqualTo(DEFAULT_NUMH);
        assertThat(testCalculated.getNumn()).isEqualTo(DEFAULT_NUMN);
        assertThat(testCalculated.getNumo()).isEqualTo(DEFAULT_NUMO);
        assertThat(testCalculated.getNums()).isEqualTo(DEFAULT_NUMS);
        assertThat(testCalculated.getStartresin()).isEqualTo(DEFAULT_STARTRESIN);
        assertThat(testCalculated.getCostresin()).isEqualTo(DEFAULT_COSTRESIN);
        assertThat(testCalculated.getWeightgain()).isEqualTo(DEFAULT_WEIGHTGAIN);
        assertThat(testCalculated.getTotalweight()).isEqualTo(DEFAULT_TOTALWEIGHT);
        assertThat(testCalculated.getCostaa()).isEqualTo(DEFAULT_COSTAA);
        assertThat(testCalculated.getSumeachaaweight()).isEqualTo(DEFAULT_SUMEACHAAWEIGHT);
        assertThat(testCalculated.getResinunitprice()).isEqualTo(DEFAULT_RESINUNITPRICE);
        assertThat(testCalculated.getSubstitude()).isEqualTo(DEFAULT_SUBSTITUDE);
        assertThat(testCalculated.getBound()).isEqualTo(DEFAULT_BOUND);
        assertThat(testCalculated.getCostwaste()).isEqualTo(DEFAULT_COSTWASTE);
        assertThat(testCalculated.getProtocolname()).isEqualTo(DEFAULT_PROTOCOLNAME);
    }

    @Test
    @Transactional
    public void createCalculatedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = calculatedRepository.findAll().size();

        // Create the Calculated with an existing ID
        calculated.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCalculatedMockMvc.perform(post("/api/calculateds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calculated)))
            .andExpect(status().isBadRequest());

        // Validate the Calculated in the database
        List<Calculated> calculatedList = calculatedRepository.findAll();
        assertThat(calculatedList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSequenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = calculatedRepository.findAll().size();
        // set the field null
        calculated.setSequence(null);

        // Create the Calculated, which fails.

        restCalculatedMockMvc.perform(post("/api/calculateds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calculated)))
            .andExpect(status().isBadRequest());

        List<Calculated> calculatedList = calculatedRepository.findAll();
        assertThat(calculatedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkScIsRequired() throws Exception {
        int databaseSizeBeforeTest = calculatedRepository.findAll().size();
        // set the field null
        calculated.setSc(null);

        // Create the Calculated, which fails.

        restCalculatedMockMvc.perform(post("/api/calculateds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calculated)))
            .andExpect(status().isBadRequest());

        List<Calculated> calculatedList = calculatedRepository.findAll();
        assertThat(calculatedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDcIsRequired() throws Exception {
        int databaseSizeBeforeTest = calculatedRepository.findAll().size();
        // set the field null
        calculated.setDc(null);

        // Create the Calculated, which fails.

        restCalculatedMockMvc.perform(post("/api/calculateds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calculated)))
            .andExpect(status().isBadRequest());

        List<Calculated> calculatedList = calculatedRepository.findAll();
        assertThat(calculatedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkScaleIsRequired() throws Exception {
        int databaseSizeBeforeTest = calculatedRepository.findAll().size();
        // set the field null
        calculated.setScale(null);

        // Create the Calculated, which fails.

        restCalculatedMockMvc.perform(post("/api/calculateds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calculated)))
            .andExpect(status().isBadRequest());

        List<Calculated> calculatedList = calculatedRepository.findAll();
        assertThat(calculatedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCalculateds() throws Exception {
        // Initialize the database
        calculatedRepository.saveAndFlush(calculated);

        // Get all the calculatedList
        restCalculatedMockMvc.perform(get("/api/calculateds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calculated.getId().intValue())))
            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE.toString())))
            .andExpect(jsonPath("$.[*].sc").value(hasItem(DEFAULT_SC)))
            .andExpect(jsonPath("$.[*].dc").value(hasItem(DEFAULT_DC)))
            .andExpect(jsonPath("$.[*].scale").value(hasItem(DEFAULT_SCALE)))
            .andExpect(jsonPath("$.[*].pi").value(hasItem(DEFAULT_PI.doubleValue())))
            .andExpect(jsonPath("$.[*].netcharge").value(hasItem(DEFAULT_NETCHARGE.doubleValue())))
            .andExpect(jsonPath("$.[*].solubility").value(hasItem(DEFAULT_SOLUBILITY.toString())))
            .andExpect(jsonPath("$.[*].numc").value(hasItem(DEFAULT_NUMC)))
            .andExpect(jsonPath("$.[*].numh").value(hasItem(DEFAULT_NUMH)))
            .andExpect(jsonPath("$.[*].numn").value(hasItem(DEFAULT_NUMN)))
            .andExpect(jsonPath("$.[*].numo").value(hasItem(DEFAULT_NUMO)))
            .andExpect(jsonPath("$.[*].nums").value(hasItem(DEFAULT_NUMS)))
            .andExpect(jsonPath("$.[*].startresin").value(hasItem(DEFAULT_STARTRESIN.doubleValue())))
            .andExpect(jsonPath("$.[*].costresin").value(hasItem(DEFAULT_COSTRESIN.doubleValue())))
            .andExpect(jsonPath("$.[*].weightgain").value(hasItem(DEFAULT_WEIGHTGAIN.doubleValue())))
            .andExpect(jsonPath("$.[*].totalweight").value(hasItem(DEFAULT_TOTALWEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].costaa").value(hasItem(DEFAULT_COSTAA.doubleValue())))
            .andExpect(jsonPath("$.[*].sumeachaaweight").value(hasItem(DEFAULT_SUMEACHAAWEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].resinunitprice").value(hasItem(DEFAULT_RESINUNITPRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].substitude").value(hasItem(DEFAULT_SUBSTITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].bound").value(hasItem(DEFAULT_BOUND)))
            .andExpect(jsonPath("$.[*].costwaste").value(hasItem(DEFAULT_COSTWASTE.doubleValue())))
            .andExpect(jsonPath("$.[*].protocolname").value(hasItem(DEFAULT_PROTOCOLNAME.toString())));
    }
    
    @Test
    @Transactional
    public void getCalculated() throws Exception {
        // Initialize the database
        calculatedRepository.saveAndFlush(calculated);

        // Get the calculated
        restCalculatedMockMvc.perform(get("/api/calculateds/{id}", calculated.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(calculated.getId().intValue()))
            .andExpect(jsonPath("$.sequence").value(DEFAULT_SEQUENCE.toString()))
            .andExpect(jsonPath("$.sc").value(DEFAULT_SC))
            .andExpect(jsonPath("$.dc").value(DEFAULT_DC))
            .andExpect(jsonPath("$.scale").value(DEFAULT_SCALE))
            .andExpect(jsonPath("$.pi").value(DEFAULT_PI.doubleValue()))
            .andExpect(jsonPath("$.netcharge").value(DEFAULT_NETCHARGE.doubleValue()))
            .andExpect(jsonPath("$.solubility").value(DEFAULT_SOLUBILITY.toString()))
            .andExpect(jsonPath("$.numc").value(DEFAULT_NUMC))
            .andExpect(jsonPath("$.numh").value(DEFAULT_NUMH))
            .andExpect(jsonPath("$.numn").value(DEFAULT_NUMN))
            .andExpect(jsonPath("$.numo").value(DEFAULT_NUMO))
            .andExpect(jsonPath("$.nums").value(DEFAULT_NUMS))
            .andExpect(jsonPath("$.startresin").value(DEFAULT_STARTRESIN.doubleValue()))
            .andExpect(jsonPath("$.costresin").value(DEFAULT_COSTRESIN.doubleValue()))
            .andExpect(jsonPath("$.weightgain").value(DEFAULT_WEIGHTGAIN.doubleValue()))
            .andExpect(jsonPath("$.totalweight").value(DEFAULT_TOTALWEIGHT.doubleValue()))
            .andExpect(jsonPath("$.costaa").value(DEFAULT_COSTAA.doubleValue()))
            .andExpect(jsonPath("$.sumeachaaweight").value(DEFAULT_SUMEACHAAWEIGHT.doubleValue()))
            .andExpect(jsonPath("$.resinunitprice").value(DEFAULT_RESINUNITPRICE.doubleValue()))
            .andExpect(jsonPath("$.substitude").value(DEFAULT_SUBSTITUDE.doubleValue()))
            .andExpect(jsonPath("$.bound").value(DEFAULT_BOUND))
            .andExpect(jsonPath("$.costwaste").value(DEFAULT_COSTWASTE.doubleValue()))
            .andExpect(jsonPath("$.protocolname").value(DEFAULT_PROTOCOLNAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCalculated() throws Exception {
        // Get the calculated
        restCalculatedMockMvc.perform(get("/api/calculateds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCalculated() throws Exception {
        // Initialize the database
        calculatedRepository.saveAndFlush(calculated);

        int databaseSizeBeforeUpdate = calculatedRepository.findAll().size();

        // Update the calculated
        Calculated updatedCalculated = calculatedRepository.findById(calculated.getId()).get();
        // Disconnect from session so that the updates on updatedCalculated are not directly saved in db
        em.detach(updatedCalculated);
        updatedCalculated
            .sequence(UPDATED_SEQUENCE)
            .sc(UPDATED_SC)
            .dc(UPDATED_DC)
            .scale(UPDATED_SCALE)
            .pi(UPDATED_PI)
            .netcharge(UPDATED_NETCHARGE)
            .solubility(UPDATED_SOLUBILITY)
            .numc(UPDATED_NUMC)
            .numh(UPDATED_NUMH)
            .numn(UPDATED_NUMN)
            .numo(UPDATED_NUMO)
            .nums(UPDATED_NUMS)
            .startresin(UPDATED_STARTRESIN)
            .costresin(UPDATED_COSTRESIN)
            .weightgain(UPDATED_WEIGHTGAIN)
            .totalweight(UPDATED_TOTALWEIGHT)
            .costaa(UPDATED_COSTAA)
            .sumeachaaweight(UPDATED_SUMEACHAAWEIGHT)
            .resinunitprice(UPDATED_RESINUNITPRICE)
            .substitude(UPDATED_SUBSTITUDE)
            .bound(UPDATED_BOUND)
            .costwaste(UPDATED_COSTWASTE)
            .protocolname(UPDATED_PROTOCOLNAME);

        restCalculatedMockMvc.perform(put("/api/calculateds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCalculated)))
            .andExpect(status().isOk());

        // Validate the Calculated in the database
        List<Calculated> calculatedList = calculatedRepository.findAll();
        assertThat(calculatedList).hasSize(databaseSizeBeforeUpdate);
        Calculated testCalculated = calculatedList.get(calculatedList.size() - 1);
        assertThat(testCalculated.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testCalculated.getSc()).isEqualTo(UPDATED_SC);
        assertThat(testCalculated.getDc()).isEqualTo(UPDATED_DC);
        assertThat(testCalculated.getScale()).isEqualTo(UPDATED_SCALE);
        assertThat(testCalculated.getPi()).isEqualTo(UPDATED_PI);
        assertThat(testCalculated.getNetcharge()).isEqualTo(UPDATED_NETCHARGE);
        assertThat(testCalculated.getSolubility()).isEqualTo(UPDATED_SOLUBILITY);
        assertThat(testCalculated.getNumc()).isEqualTo(UPDATED_NUMC);
        assertThat(testCalculated.getNumh()).isEqualTo(UPDATED_NUMH);
        assertThat(testCalculated.getNumn()).isEqualTo(UPDATED_NUMN);
        assertThat(testCalculated.getNumo()).isEqualTo(UPDATED_NUMO);
        assertThat(testCalculated.getNums()).isEqualTo(UPDATED_NUMS);
        assertThat(testCalculated.getStartresin()).isEqualTo(UPDATED_STARTRESIN);
        assertThat(testCalculated.getCostresin()).isEqualTo(UPDATED_COSTRESIN);
        assertThat(testCalculated.getWeightgain()).isEqualTo(UPDATED_WEIGHTGAIN);
        assertThat(testCalculated.getTotalweight()).isEqualTo(UPDATED_TOTALWEIGHT);
        assertThat(testCalculated.getCostaa()).isEqualTo(UPDATED_COSTAA);
        assertThat(testCalculated.getSumeachaaweight()).isEqualTo(UPDATED_SUMEACHAAWEIGHT);
        assertThat(testCalculated.getResinunitprice()).isEqualTo(UPDATED_RESINUNITPRICE);
        assertThat(testCalculated.getSubstitude()).isEqualTo(UPDATED_SUBSTITUDE);
        assertThat(testCalculated.getBound()).isEqualTo(UPDATED_BOUND);
        assertThat(testCalculated.getCostwaste()).isEqualTo(UPDATED_COSTWASTE);
        assertThat(testCalculated.getProtocolname()).isEqualTo(UPDATED_PROTOCOLNAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCalculated() throws Exception {
        int databaseSizeBeforeUpdate = calculatedRepository.findAll().size();

        // Create the Calculated

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCalculatedMockMvc.perform(put("/api/calculateds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calculated)))
            .andExpect(status().isBadRequest());

        // Validate the Calculated in the database
        List<Calculated> calculatedList = calculatedRepository.findAll();
        assertThat(calculatedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCalculated() throws Exception {
        // Initialize the database
        calculatedRepository.saveAndFlush(calculated);

        int databaseSizeBeforeDelete = calculatedRepository.findAll().size();

        // Delete the calculated
        restCalculatedMockMvc.perform(delete("/api/calculateds/{id}", calculated.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Calculated> calculatedList = calculatedRepository.findAll();
        assertThat(calculatedList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Calculated.class);
        Calculated calculated1 = new Calculated();
        calculated1.setId(1L);
        Calculated calculated2 = new Calculated();
        calculated2.setId(calculated1.getId());
        assertThat(calculated1).isEqualTo(calculated2);
        calculated2.setId(2L);
        assertThat(calculated1).isNotEqualTo(calculated2);
        calculated1.setId(null);
        assertThat(calculated1).isNotEqualTo(calculated2);
    }
}
