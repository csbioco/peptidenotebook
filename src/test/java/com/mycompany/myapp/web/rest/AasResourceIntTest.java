package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PeptidenotebookApp;

import com.mycompany.myapp.domain.Aas;
import com.mycompany.myapp.repository.AasRepository;
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
import com.mycompany.myapp.service.Create22aa;

import javax.persistence.EntityManager;
import java.util.List;


import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AasResource REST controller.
 *
 * @see AasResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PeptidenotebookApp.class)
public class AasResourceIntTest {

    private static final String DEFAULT_AANAME = "AAAAAAAAAA";
    private static final String UPDATED_AANAME = "BBBBBBBBBB";

    private static final String DEFAULT_THREELETTER = "AAAAAAAAAA";
    private static final String UPDATED_THREELETTER = "BBBBBBBBBB";

    private static final Double DEFAULT_MWWITHPROTECTION = 1D;
    private static final Double UPDATED_MWWITHPROTECTION = 2D;

    private static final Double DEFAULT_MWWITHOUTPROTECTION = 1D;
    private static final Double UPDATED_MWWITHOUTPROTECTION = 2D;

    private static final Double DEFAULT_PI = 1D;
    private static final Double UPDATED_PI = 2D;

    private static final Double DEFAULT_UNITPRICE = 1D;
    private static final Double UPDATED_UNITPRICE = 2D;

    private static final Double DEFAULT_DIFFICULTY = 1D;
    private static final Double UPDATED_DIFFICULTY = 2D;

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

    private static final String DEFAULT_SOLUBILITY = "AAAAAAAAAA";
    private static final String UPDATED_SOLUBILITY = "BBBBBBBBBB";

    @Autowired
    private AasRepository aasRepository;

    @Autowired
    private Create22aa create22aa;
    
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

    private MockMvc restAasMockMvc;

    private Aas aas;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AasResource aasResource = new AasResource(aasRepository, create22aa);
        this.restAasMockMvc = MockMvcBuilders.standaloneSetup(aasResource)
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
    public static Aas createEntity(EntityManager em) {
        Aas aas = new Aas()
            .aaname(DEFAULT_AANAME)
            .threeletter(DEFAULT_THREELETTER)
            .mwwithprotection(DEFAULT_MWWITHPROTECTION)
            .mwwithoutprotection(DEFAULT_MWWITHOUTPROTECTION)
            .pi(DEFAULT_PI)
            .unitprice(DEFAULT_UNITPRICE)
            .difficulty(DEFAULT_DIFFICULTY)
            .numc(DEFAULT_NUMC)
            .numh(DEFAULT_NUMH)
            .numn(DEFAULT_NUMN)
            .numo(DEFAULT_NUMO)
            .nums(DEFAULT_NUMS)
            .solubility(DEFAULT_SOLUBILITY);
        return aas;
    }

    @Before
    public void initTest() {
        aas = createEntity(em);
    }

    @Test
    @Transactional
    public void createAas() throws Exception {
        int databaseSizeBeforeCreate = aasRepository.findAll().size();

        // Create the Aas
        restAasMockMvc.perform(post("/api/aas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aas)))
            .andExpect(status().isCreated());

        // Validate the Aas in the database
        List<Aas> aasList = aasRepository.findAll();
        assertThat(aasList).hasSize(databaseSizeBeforeCreate + 1);
        Aas testAas = aasList.get(aasList.size() - 1);
        assertThat(testAas.getAaname()).isEqualTo(DEFAULT_AANAME);
        assertThat(testAas.getThreeletter()).isEqualTo(DEFAULT_THREELETTER);
        assertThat(testAas.getMwwithprotection()).isEqualTo(DEFAULT_MWWITHPROTECTION);
        assertThat(testAas.getMwwithoutprotection()).isEqualTo(DEFAULT_MWWITHOUTPROTECTION);
        assertThat(testAas.getPi()).isEqualTo(DEFAULT_PI);
        assertThat(testAas.getUnitprice()).isEqualTo(DEFAULT_UNITPRICE);
        assertThat(testAas.getDifficulty()).isEqualTo(DEFAULT_DIFFICULTY);
        assertThat(testAas.getNumc()).isEqualTo(DEFAULT_NUMC);
        assertThat(testAas.getNumh()).isEqualTo(DEFAULT_NUMH);
        assertThat(testAas.getNumn()).isEqualTo(DEFAULT_NUMN);
        assertThat(testAas.getNumo()).isEqualTo(DEFAULT_NUMO);
        assertThat(testAas.getNums()).isEqualTo(DEFAULT_NUMS);
        assertThat(testAas.getSolubility()).isEqualTo(DEFAULT_SOLUBILITY);
    }

    @Test
    @Transactional
    public void createAasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aasRepository.findAll().size();

        // Create the Aas with an existing ID
        aas.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAasMockMvc.perform(post("/api/aas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aas)))
            .andExpect(status().isBadRequest());

        // Validate the Aas in the database
        List<Aas> aasList = aasRepository.findAll();
        assertThat(aasList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAanameIsRequired() throws Exception {
        int databaseSizeBeforeTest = aasRepository.findAll().size();
        // set the field null
        aas.setAaname(null);

        // Create the Aas, which fails.

        restAasMockMvc.perform(post("/api/aas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aas)))
            .andExpect(status().isBadRequest());

        List<Aas> aasList = aasRepository.findAll();
        assertThat(aasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkThreeletterIsRequired() throws Exception {
        int databaseSizeBeforeTest = aasRepository.findAll().size();
        // set the field null
        aas.setThreeletter(null);

        // Create the Aas, which fails.

        restAasMockMvc.perform(post("/api/aas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aas)))
            .andExpect(status().isBadRequest());

        List<Aas> aasList = aasRepository.findAll();
        assertThat(aasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMwwithprotectionIsRequired() throws Exception {
        int databaseSizeBeforeTest = aasRepository.findAll().size();
        // set the field null
        aas.setMwwithprotection(null);

        // Create the Aas, which fails.

        restAasMockMvc.perform(post("/api/aas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aas)))
            .andExpect(status().isBadRequest());

        List<Aas> aasList = aasRepository.findAll();
        assertThat(aasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMwwithoutprotectionIsRequired() throws Exception {
        int databaseSizeBeforeTest = aasRepository.findAll().size();
        // set the field null
        aas.setMwwithoutprotection(null);

        // Create the Aas, which fails.

        restAasMockMvc.perform(post("/api/aas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aas)))
            .andExpect(status().isBadRequest());

        List<Aas> aasList = aasRepository.findAll();
        assertThat(aasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPiIsRequired() throws Exception {
        int databaseSizeBeforeTest = aasRepository.findAll().size();
        // set the field null
        aas.setPi(null);

        // Create the Aas, which fails.

        restAasMockMvc.perform(post("/api/aas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aas)))
            .andExpect(status().isBadRequest());

        List<Aas> aasList = aasRepository.findAll();
        assertThat(aasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnitpriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = aasRepository.findAll().size();
        // set the field null
        aas.setUnitprice(null);

        // Create the Aas, which fails.

        restAasMockMvc.perform(post("/api/aas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aas)))
            .andExpect(status().isBadRequest());

        List<Aas> aasList = aasRepository.findAll();
        assertThat(aasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDifficultyIsRequired() throws Exception {
        int databaseSizeBeforeTest = aasRepository.findAll().size();
        // set the field null
        aas.setDifficulty(null);

        // Create the Aas, which fails.

        restAasMockMvc.perform(post("/api/aas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aas)))
            .andExpect(status().isBadRequest());

        List<Aas> aasList = aasRepository.findAll();
        assertThat(aasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumcIsRequired() throws Exception {
        int databaseSizeBeforeTest = aasRepository.findAll().size();
        // set the field null
        aas.setNumc(null);

        // Create the Aas, which fails.

        restAasMockMvc.perform(post("/api/aas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aas)))
            .andExpect(status().isBadRequest());

        List<Aas> aasList = aasRepository.findAll();
        assertThat(aasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumhIsRequired() throws Exception {
        int databaseSizeBeforeTest = aasRepository.findAll().size();
        // set the field null
        aas.setNumh(null);

        // Create the Aas, which fails.

        restAasMockMvc.perform(post("/api/aas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aas)))
            .andExpect(status().isBadRequest());

        List<Aas> aasList = aasRepository.findAll();
        assertThat(aasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumnIsRequired() throws Exception {
        int databaseSizeBeforeTest = aasRepository.findAll().size();
        // set the field null
        aas.setNumn(null);

        // Create the Aas, which fails.

        restAasMockMvc.perform(post("/api/aas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aas)))
            .andExpect(status().isBadRequest());

        List<Aas> aasList = aasRepository.findAll();
        assertThat(aasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumoIsRequired() throws Exception {
        int databaseSizeBeforeTest = aasRepository.findAll().size();
        // set the field null
        aas.setNumo(null);

        // Create the Aas, which fails.

        restAasMockMvc.perform(post("/api/aas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aas)))
            .andExpect(status().isBadRequest());

        List<Aas> aasList = aasRepository.findAll();
        assertThat(aasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumsIsRequired() throws Exception {
        int databaseSizeBeforeTest = aasRepository.findAll().size();
        // set the field null
        aas.setNums(null);

        // Create the Aas, which fails.

        restAasMockMvc.perform(post("/api/aas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aas)))
            .andExpect(status().isBadRequest());

        List<Aas> aasList = aasRepository.findAll();
        assertThat(aasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSolubilityIsRequired() throws Exception {
        int databaseSizeBeforeTest = aasRepository.findAll().size();
        // set the field null
        aas.setSolubility(null);

        // Create the Aas, which fails.

        restAasMockMvc.perform(post("/api/aas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aas)))
            .andExpect(status().isBadRequest());

        List<Aas> aasList = aasRepository.findAll();
        assertThat(aasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAas() throws Exception {
        // Initialize the database
        aasRepository.saveAndFlush(aas);

        // Get all the aasList
        restAasMockMvc.perform(get("/api/aas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aas.getId().intValue())))
            .andExpect(jsonPath("$.[*].aaname").value(hasItem(DEFAULT_AANAME.toString())))
            .andExpect(jsonPath("$.[*].threeletter").value(hasItem(DEFAULT_THREELETTER.toString())))
            .andExpect(jsonPath("$.[*].mwwithprotection").value(hasItem(DEFAULT_MWWITHPROTECTION.doubleValue())))
            .andExpect(jsonPath("$.[*].mwwithoutprotection").value(hasItem(DEFAULT_MWWITHOUTPROTECTION.doubleValue())))
            .andExpect(jsonPath("$.[*].pi").value(hasItem(DEFAULT_PI.doubleValue())))
            .andExpect(jsonPath("$.[*].unitprice").value(hasItem(DEFAULT_UNITPRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].difficulty").value(hasItem(DEFAULT_DIFFICULTY.doubleValue())))
            .andExpect(jsonPath("$.[*].numc").value(hasItem(DEFAULT_NUMC)))
            .andExpect(jsonPath("$.[*].numh").value(hasItem(DEFAULT_NUMH)))
            .andExpect(jsonPath("$.[*].numn").value(hasItem(DEFAULT_NUMN)))
            .andExpect(jsonPath("$.[*].numo").value(hasItem(DEFAULT_NUMO)))
            .andExpect(jsonPath("$.[*].nums").value(hasItem(DEFAULT_NUMS)))
            .andExpect(jsonPath("$.[*].solubility").value(hasItem(DEFAULT_SOLUBILITY.toString())));
    }
    
    @Test
    @Transactional
    public void getAas() throws Exception {
        // Initialize the database
        aasRepository.saveAndFlush(aas);

        // Get the aas
        restAasMockMvc.perform(get("/api/aas/{id}", aas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(aas.getId().intValue()))
            .andExpect(jsonPath("$.aaname").value(DEFAULT_AANAME.toString()))
            .andExpect(jsonPath("$.threeletter").value(DEFAULT_THREELETTER.toString()))
            .andExpect(jsonPath("$.mwwithprotection").value(DEFAULT_MWWITHPROTECTION.doubleValue()))
            .andExpect(jsonPath("$.mwwithoutprotection").value(DEFAULT_MWWITHOUTPROTECTION.doubleValue()))
            .andExpect(jsonPath("$.pi").value(DEFAULT_PI.doubleValue()))
            .andExpect(jsonPath("$.unitprice").value(DEFAULT_UNITPRICE.doubleValue()))
            .andExpect(jsonPath("$.difficulty").value(DEFAULT_DIFFICULTY.doubleValue()))
            .andExpect(jsonPath("$.numc").value(DEFAULT_NUMC))
            .andExpect(jsonPath("$.numh").value(DEFAULT_NUMH))
            .andExpect(jsonPath("$.numn").value(DEFAULT_NUMN))
            .andExpect(jsonPath("$.numo").value(DEFAULT_NUMO))
            .andExpect(jsonPath("$.nums").value(DEFAULT_NUMS))
            .andExpect(jsonPath("$.solubility").value(DEFAULT_SOLUBILITY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAas() throws Exception {
        // Get the aas
        restAasMockMvc.perform(get("/api/aas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAas() throws Exception {
        // Initialize the database
        aasRepository.saveAndFlush(aas);

        int databaseSizeBeforeUpdate = aasRepository.findAll().size();

        // Update the aas
        Aas updatedAas = aasRepository.findById(aas.getId()).get();
        // Disconnect from session so that the updates on updatedAas are not directly saved in db
        em.detach(updatedAas);
        updatedAas
            .aaname(UPDATED_AANAME)
            .threeletter(UPDATED_THREELETTER)
            .mwwithprotection(UPDATED_MWWITHPROTECTION)
            .mwwithoutprotection(UPDATED_MWWITHOUTPROTECTION)
            .pi(UPDATED_PI)
            .unitprice(UPDATED_UNITPRICE)
            .difficulty(UPDATED_DIFFICULTY)
            .numc(UPDATED_NUMC)
            .numh(UPDATED_NUMH)
            .numn(UPDATED_NUMN)
            .numo(UPDATED_NUMO)
            .nums(UPDATED_NUMS)
            .solubility(UPDATED_SOLUBILITY);

        restAasMockMvc.perform(put("/api/aas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAas)))
            .andExpect(status().isOk());

        // Validate the Aas in the database
        List<Aas> aasList = aasRepository.findAll();
        assertThat(aasList).hasSize(databaseSizeBeforeUpdate);
        Aas testAas = aasList.get(aasList.size() - 1);
        assertThat(testAas.getAaname()).isEqualTo(UPDATED_AANAME);
        assertThat(testAas.getThreeletter()).isEqualTo(UPDATED_THREELETTER);
        assertThat(testAas.getMwwithprotection()).isEqualTo(UPDATED_MWWITHPROTECTION);
        assertThat(testAas.getMwwithoutprotection()).isEqualTo(UPDATED_MWWITHOUTPROTECTION);
        assertThat(testAas.getPi()).isEqualTo(UPDATED_PI);
        assertThat(testAas.getUnitprice()).isEqualTo(UPDATED_UNITPRICE);
        assertThat(testAas.getDifficulty()).isEqualTo(UPDATED_DIFFICULTY);
        assertThat(testAas.getNumc()).isEqualTo(UPDATED_NUMC);
        assertThat(testAas.getNumh()).isEqualTo(UPDATED_NUMH);
        assertThat(testAas.getNumn()).isEqualTo(UPDATED_NUMN);
        assertThat(testAas.getNumo()).isEqualTo(UPDATED_NUMO);
        assertThat(testAas.getNums()).isEqualTo(UPDATED_NUMS);
        assertThat(testAas.getSolubility()).isEqualTo(UPDATED_SOLUBILITY);
    }

    @Test
    @Transactional
    public void updateNonExistingAas() throws Exception {
        int databaseSizeBeforeUpdate = aasRepository.findAll().size();

        // Create the Aas

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAasMockMvc.perform(put("/api/aas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aas)))
            .andExpect(status().isBadRequest());

        // Validate the Aas in the database
        List<Aas> aasList = aasRepository.findAll();
        assertThat(aasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAas() throws Exception {
        // Initialize the database
        aasRepository.saveAndFlush(aas);

        int databaseSizeBeforeDelete = aasRepository.findAll().size();

        // Delete the aas
        restAasMockMvc.perform(delete("/api/aas/{id}", aas.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Aas> aasList = aasRepository.findAll();
        assertThat(aasList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aas.class);
        Aas aas1 = new Aas();
        aas1.setId(1L);
        Aas aas2 = new Aas();
        aas2.setId(aas1.getId());
        assertThat(aas1).isEqualTo(aas2);
        aas2.setId(2L);
        assertThat(aas1).isNotEqualTo(aas2);
        aas1.setId(null);
        assertThat(aas1).isNotEqualTo(aas2);
    }
}
