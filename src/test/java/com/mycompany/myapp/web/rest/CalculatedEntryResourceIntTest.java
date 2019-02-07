package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PeptidenotebookApp;

import com.mycompany.myapp.domain.CalculatedEntry;
import com.mycompany.myapp.repository.CalculatedEntryRepository;
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


import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.mycompany.myapp.service.CalculationAll;

/**
 * Test class for the CalculatedEntryResource REST controller.
 *
 * @see CalculatedEntryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PeptidenotebookApp.class)
public class CalculatedEntryResourceIntTest {

    private static final Integer DEFAULT_SEQUENCENUMBER = 1;
    private static final Integer UPDATED_SEQUENCENUMBER = 2;

    private static final String DEFAULT_AA = "AAAAAAAAAA";
    private static final String UPDATED_AA = "BBBBBBBBBB";

    private static final Integer DEFAULT_SC = 1;
    private static final Integer UPDATED_SC = 2;

    private static final Integer DEFAULT_DC = 1;
    private static final Integer UPDATED_DC = 2;

    private static final Integer DEFAULT_SCALE = 1;
    private static final Integer UPDATED_SCALE = 2;

    private static final Double DEFAULT_MWWITHPROTECTION = 1D;
    private static final Double UPDATED_MWWITHPROTECTION = 2D;

    private static final Double DEFAULT_MWWITHOUTPROTECTION = 1D;
    private static final Double UPDATED_MWWITHOUTPROTECTION = 2D;

    private static final Double DEFAULT_EACHAAWEIGHT = 1D;
    private static final Double UPDATED_EACHAAWEIGHT = 2D;

    private static final Double DEFAULT_DIFFICULTY = 1D;
    private static final Double UPDATED_DIFFICULTY = 2D;

    private static final Double DEFAULT_CURRENTRESINWEIGHT = 1D;
    private static final Double UPDATED_CURRENTRESINWEIGHT = 2D;

    private static final String DEFAULT_PROTOCOLNAME = "AAAAAAAAAA";
    private static final String UPDATED_PROTOCOLNAME = "BBBBBBBBBB";

    @Autowired
    private CalculatedEntryRepository calculatedEntryRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private CalculationAll calculationAll;
    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCalculatedEntryMockMvc;

    private CalculatedEntry calculatedEntry;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CalculatedEntryResource calculatedEntryResource = new CalculatedEntryResource(calculatedEntryRepository, calculationAll);
        this.restCalculatedEntryMockMvc = MockMvcBuilders.standaloneSetup(calculatedEntryResource)
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
    public static CalculatedEntry createEntity(EntityManager em) {
        CalculatedEntry calculatedEntry = new CalculatedEntry()
            .sequencenumber(DEFAULT_SEQUENCENUMBER)
            .aa(DEFAULT_AA)
            .sc(DEFAULT_SC)
            .dc(DEFAULT_DC)
            .scale(DEFAULT_SCALE)
            .mwwithprotection(DEFAULT_MWWITHPROTECTION)
            .mwwithoutprotection(DEFAULT_MWWITHOUTPROTECTION)
            .eachaaweight(DEFAULT_EACHAAWEIGHT)
            .difficulty(DEFAULT_DIFFICULTY)
            .currentresinweight(DEFAULT_CURRENTRESINWEIGHT)
            .protocolname(DEFAULT_PROTOCOLNAME);
        return calculatedEntry;
    }

    @Before
    public void initTest() {
        calculatedEntry = createEntity(em);
    }

    @Test
    @Transactional
    public void createCalculatedEntry() throws Exception {
        int databaseSizeBeforeCreate = calculatedEntryRepository.findAll().size();

        // Create the CalculatedEntry
        restCalculatedEntryMockMvc.perform(post("/api/calculated-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calculatedEntry)))
            .andExpect(status().isCreated());

        // Validate the CalculatedEntry in the database
        List<CalculatedEntry> calculatedEntryList = calculatedEntryRepository.findAll();
        assertThat(calculatedEntryList).hasSize(databaseSizeBeforeCreate + 1);
        CalculatedEntry testCalculatedEntry = calculatedEntryList.get(calculatedEntryList.size() - 1);
        assertThat(testCalculatedEntry.getSequencenumber()).isEqualTo(DEFAULT_SEQUENCENUMBER);
        assertThat(testCalculatedEntry.getAa()).isEqualTo(DEFAULT_AA);
        assertThat(testCalculatedEntry.getSc()).isEqualTo(DEFAULT_SC);
        assertThat(testCalculatedEntry.getDc()).isEqualTo(DEFAULT_DC);
        assertThat(testCalculatedEntry.getScale()).isEqualTo(DEFAULT_SCALE);
        assertThat(testCalculatedEntry.getMwwithprotection()).isEqualTo(DEFAULT_MWWITHPROTECTION);
        assertThat(testCalculatedEntry.getMwwithoutprotection()).isEqualTo(DEFAULT_MWWITHOUTPROTECTION);
        assertThat(testCalculatedEntry.getEachaaweight()).isEqualTo(DEFAULT_EACHAAWEIGHT);
        assertThat(testCalculatedEntry.getDifficulty()).isEqualTo(DEFAULT_DIFFICULTY);
        assertThat(testCalculatedEntry.getCurrentresinweight()).isEqualTo(DEFAULT_CURRENTRESINWEIGHT);
        assertThat(testCalculatedEntry.getProtocolname()).isEqualTo(DEFAULT_PROTOCOLNAME);
    }

    @Test
    @Transactional
    public void createCalculatedEntryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = calculatedEntryRepository.findAll().size();

        // Create the CalculatedEntry with an existing ID
        calculatedEntry.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCalculatedEntryMockMvc.perform(post("/api/calculated-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calculatedEntry)))
            .andExpect(status().isBadRequest());

        // Validate the CalculatedEntry in the database
        List<CalculatedEntry> calculatedEntryList = calculatedEntryRepository.findAll();
        assertThat(calculatedEntryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSequencenumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = calculatedEntryRepository.findAll().size();
        // set the field null
        calculatedEntry.setSequencenumber(null);

        // Create the CalculatedEntry, which fails.

        restCalculatedEntryMockMvc.perform(post("/api/calculated-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calculatedEntry)))
            .andExpect(status().isBadRequest());

        List<CalculatedEntry> calculatedEntryList = calculatedEntryRepository.findAll();
        assertThat(calculatedEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAaIsRequired() throws Exception {
        int databaseSizeBeforeTest = calculatedEntryRepository.findAll().size();
        // set the field null
        calculatedEntry.setAa(null);

        // Create the CalculatedEntry, which fails.

        restCalculatedEntryMockMvc.perform(post("/api/calculated-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calculatedEntry)))
            .andExpect(status().isBadRequest());

        List<CalculatedEntry> calculatedEntryList = calculatedEntryRepository.findAll();
        assertThat(calculatedEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkScIsRequired() throws Exception {
        int databaseSizeBeforeTest = calculatedEntryRepository.findAll().size();
        // set the field null
        calculatedEntry.setSc(null);

        // Create the CalculatedEntry, which fails.

        restCalculatedEntryMockMvc.perform(post("/api/calculated-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calculatedEntry)))
            .andExpect(status().isBadRequest());

        List<CalculatedEntry> calculatedEntryList = calculatedEntryRepository.findAll();
        assertThat(calculatedEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDcIsRequired() throws Exception {
        int databaseSizeBeforeTest = calculatedEntryRepository.findAll().size();
        // set the field null
        calculatedEntry.setDc(null);

        // Create the CalculatedEntry, which fails.

        restCalculatedEntryMockMvc.perform(post("/api/calculated-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calculatedEntry)))
            .andExpect(status().isBadRequest());

        List<CalculatedEntry> calculatedEntryList = calculatedEntryRepository.findAll();
        assertThat(calculatedEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkScaleIsRequired() throws Exception {
        int databaseSizeBeforeTest = calculatedEntryRepository.findAll().size();
        // set the field null
        calculatedEntry.setScale(null);

        // Create the CalculatedEntry, which fails.

        restCalculatedEntryMockMvc.perform(post("/api/calculated-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calculatedEntry)))
            .andExpect(status().isBadRequest());

        List<CalculatedEntry> calculatedEntryList = calculatedEntryRepository.findAll();
        assertThat(calculatedEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCalculatedEntries() throws Exception {
        // Initialize the database
        calculatedEntryRepository.saveAndFlush(calculatedEntry);

        // Get all the calculatedEntryList
        restCalculatedEntryMockMvc.perform(get("/api/calculated-entries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calculatedEntry.getId().intValue())))
            .andExpect(jsonPath("$.[*].sequencenumber").value(hasItem(DEFAULT_SEQUENCENUMBER)))
            .andExpect(jsonPath("$.[*].aa").value(hasItem(DEFAULT_AA.toString())))
            .andExpect(jsonPath("$.[*].sc").value(hasItem(DEFAULT_SC)))
            .andExpect(jsonPath("$.[*].dc").value(hasItem(DEFAULT_DC)))
            .andExpect(jsonPath("$.[*].scale").value(hasItem(DEFAULT_SCALE)))
            .andExpect(jsonPath("$.[*].mwwithprotection").value(hasItem(DEFAULT_MWWITHPROTECTION.doubleValue())))
            .andExpect(jsonPath("$.[*].mwwithoutprotection").value(hasItem(DEFAULT_MWWITHOUTPROTECTION.doubleValue())))
            .andExpect(jsonPath("$.[*].eachaaweight").value(hasItem(DEFAULT_EACHAAWEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].difficulty").value(hasItem(DEFAULT_DIFFICULTY.doubleValue())))
            .andExpect(jsonPath("$.[*].currentresinweight").value(hasItem(DEFAULT_CURRENTRESINWEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].protocolname").value(hasItem(DEFAULT_PROTOCOLNAME.toString())));
    }
    
    @Test
    @Transactional
    public void getCalculatedEntry() throws Exception {
        // Initialize the database
        calculatedEntryRepository.saveAndFlush(calculatedEntry);

        // Get the calculatedEntry
        restCalculatedEntryMockMvc.perform(get("/api/calculated-entries/{id}", calculatedEntry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(calculatedEntry.getId().intValue()))
            .andExpect(jsonPath("$.sequencenumber").value(DEFAULT_SEQUENCENUMBER))
            .andExpect(jsonPath("$.aa").value(DEFAULT_AA.toString()))
            .andExpect(jsonPath("$.sc").value(DEFAULT_SC))
            .andExpect(jsonPath("$.dc").value(DEFAULT_DC))
            .andExpect(jsonPath("$.scale").value(DEFAULT_SCALE))
            .andExpect(jsonPath("$.mwwithprotection").value(DEFAULT_MWWITHPROTECTION.doubleValue()))
            .andExpect(jsonPath("$.mwwithoutprotection").value(DEFAULT_MWWITHOUTPROTECTION.doubleValue()))
            .andExpect(jsonPath("$.eachaaweight").value(DEFAULT_EACHAAWEIGHT.doubleValue()))
            .andExpect(jsonPath("$.difficulty").value(DEFAULT_DIFFICULTY.doubleValue()))
            .andExpect(jsonPath("$.currentresinweight").value(DEFAULT_CURRENTRESINWEIGHT.doubleValue()))
            .andExpect(jsonPath("$.protocolname").value(DEFAULT_PROTOCOLNAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCalculatedEntry() throws Exception {
        // Get the calculatedEntry
        restCalculatedEntryMockMvc.perform(get("/api/calculated-entries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCalculatedEntry() throws Exception {
        // Initialize the database
        calculatedEntryRepository.saveAndFlush(calculatedEntry);

        int databaseSizeBeforeUpdate = calculatedEntryRepository.findAll().size();

        // Update the calculatedEntry
        CalculatedEntry updatedCalculatedEntry = calculatedEntryRepository.findById(calculatedEntry.getId()).get();
        // Disconnect from session so that the updates on updatedCalculatedEntry are not directly saved in db
        em.detach(updatedCalculatedEntry);
        updatedCalculatedEntry
            .sequencenumber(UPDATED_SEQUENCENUMBER)
            .aa(UPDATED_AA)
            .sc(UPDATED_SC)
            .dc(UPDATED_DC)
            .scale(UPDATED_SCALE)
            .mwwithprotection(UPDATED_MWWITHPROTECTION)
            .mwwithoutprotection(UPDATED_MWWITHOUTPROTECTION)
            .eachaaweight(UPDATED_EACHAAWEIGHT)
            .difficulty(UPDATED_DIFFICULTY)
            .currentresinweight(UPDATED_CURRENTRESINWEIGHT)
            .protocolname(UPDATED_PROTOCOLNAME);

        restCalculatedEntryMockMvc.perform(put("/api/calculated-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCalculatedEntry)))
            .andExpect(status().isOk());

        // Validate the CalculatedEntry in the database
        List<CalculatedEntry> calculatedEntryList = calculatedEntryRepository.findAll();
        assertThat(calculatedEntryList).hasSize(databaseSizeBeforeUpdate);
        CalculatedEntry testCalculatedEntry = calculatedEntryList.get(calculatedEntryList.size() - 1);
        assertThat(testCalculatedEntry.getSequencenumber()).isEqualTo(UPDATED_SEQUENCENUMBER);
        assertThat(testCalculatedEntry.getAa()).isEqualTo(UPDATED_AA);
        assertThat(testCalculatedEntry.getSc()).isEqualTo(UPDATED_SC);
        assertThat(testCalculatedEntry.getDc()).isEqualTo(UPDATED_DC);
        assertThat(testCalculatedEntry.getScale()).isEqualTo(UPDATED_SCALE);
        assertThat(testCalculatedEntry.getMwwithprotection()).isEqualTo(UPDATED_MWWITHPROTECTION);
        assertThat(testCalculatedEntry.getMwwithoutprotection()).isEqualTo(UPDATED_MWWITHOUTPROTECTION);
        assertThat(testCalculatedEntry.getEachaaweight()).isEqualTo(UPDATED_EACHAAWEIGHT);
        assertThat(testCalculatedEntry.getDifficulty()).isEqualTo(UPDATED_DIFFICULTY);
        assertThat(testCalculatedEntry.getCurrentresinweight()).isEqualTo(UPDATED_CURRENTRESINWEIGHT);
        assertThat(testCalculatedEntry.getProtocolname()).isEqualTo(UPDATED_PROTOCOLNAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCalculatedEntry() throws Exception {
        int databaseSizeBeforeUpdate = calculatedEntryRepository.findAll().size();

        // Create the CalculatedEntry

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCalculatedEntryMockMvc.perform(put("/api/calculated-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calculatedEntry)))
            .andExpect(status().isBadRequest());

        // Validate the CalculatedEntry in the database
        List<CalculatedEntry> calculatedEntryList = calculatedEntryRepository.findAll();
        assertThat(calculatedEntryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCalculatedEntry() throws Exception {
        // Initialize the database
        calculatedEntryRepository.saveAndFlush(calculatedEntry);

        int databaseSizeBeforeDelete = calculatedEntryRepository.findAll().size();

        // Delete the calculatedEntry
        restCalculatedEntryMockMvc.perform(delete("/api/calculated-entries/{id}", calculatedEntry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CalculatedEntry> calculatedEntryList = calculatedEntryRepository.findAll();
        assertThat(calculatedEntryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CalculatedEntry.class);
        CalculatedEntry calculatedEntry1 = new CalculatedEntry();
        calculatedEntry1.setId(1L);
        CalculatedEntry calculatedEntry2 = new CalculatedEntry();
        calculatedEntry2.setId(calculatedEntry1.getId());
        assertThat(calculatedEntry1).isEqualTo(calculatedEntry2);
        calculatedEntry2.setId(2L);
        assertThat(calculatedEntry1).isNotEqualTo(calculatedEntry2);
        calculatedEntry1.setId(null);
        assertThat(calculatedEntry1).isNotEqualTo(calculatedEntry2);
    }
}
