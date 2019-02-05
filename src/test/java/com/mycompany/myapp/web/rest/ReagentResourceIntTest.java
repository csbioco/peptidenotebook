package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PeptidenotebookApp;

import com.mycompany.myapp.domain.Reagent;
import com.mycompany.myapp.repository.ReagentRepository;
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

/**
 * Test class for the ReagentResource REST controller.
 *
 * @see ReagentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PeptidenotebookApp.class)
public class ReagentResourceIntTest {

    private static final String DEFAULT_REAGENTNAME = "AAAAAAAAAA";
    private static final String UPDATED_REAGENTNAME = "BBBBBBBBBB";

    private static final Double DEFAULT_UNITPRICE = 1D;
    private static final Double UPDATED_UNITPRICE = 2D;

    private static final Double DEFAULT_WASTERUNITPRICE = 1D;
    private static final Double UPDATED_WASTERUNITPRICE = 2D;

    @Autowired
    private ReagentRepository reagentRepository;

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

    private MockMvc restReagentMockMvc;

    private Reagent reagent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReagentResource reagentResource = new ReagentResource(reagentRepository);
        this.restReagentMockMvc = MockMvcBuilders.standaloneSetup(reagentResource)
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
    public static Reagent createEntity(EntityManager em) {
        Reagent reagent = new Reagent()
            .reagentname(DEFAULT_REAGENTNAME)
            .unitprice(DEFAULT_UNITPRICE)
            .wasterunitprice(DEFAULT_WASTERUNITPRICE);
        return reagent;
    }

    @Before
    public void initTest() {
        reagent = createEntity(em);
    }

    @Test
    @Transactional
    public void createReagent() throws Exception {
        int databaseSizeBeforeCreate = reagentRepository.findAll().size();

        // Create the Reagent
        restReagentMockMvc.perform(post("/api/reagents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reagent)))
            .andExpect(status().isCreated());

        // Validate the Reagent in the database
        List<Reagent> reagentList = reagentRepository.findAll();
        assertThat(reagentList).hasSize(databaseSizeBeforeCreate + 1);
        Reagent testReagent = reagentList.get(reagentList.size() - 1);
        assertThat(testReagent.getReagentname()).isEqualTo(DEFAULT_REAGENTNAME);
        assertThat(testReagent.getUnitprice()).isEqualTo(DEFAULT_UNITPRICE);
        assertThat(testReagent.getWasterunitprice()).isEqualTo(DEFAULT_WASTERUNITPRICE);
    }

    @Test
    @Transactional
    public void createReagentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reagentRepository.findAll().size();

        // Create the Reagent with an existing ID
        reagent.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReagentMockMvc.perform(post("/api/reagents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reagent)))
            .andExpect(status().isBadRequest());

        // Validate the Reagent in the database
        List<Reagent> reagentList = reagentRepository.findAll();
        assertThat(reagentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkReagentnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = reagentRepository.findAll().size();
        // set the field null
        reagent.setReagentname(null);

        // Create the Reagent, which fails.

        restReagentMockMvc.perform(post("/api/reagents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reagent)))
            .andExpect(status().isBadRequest());

        List<Reagent> reagentList = reagentRepository.findAll();
        assertThat(reagentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnitpriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = reagentRepository.findAll().size();
        // set the field null
        reagent.setUnitprice(null);

        // Create the Reagent, which fails.

        restReagentMockMvc.perform(post("/api/reagents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reagent)))
            .andExpect(status().isBadRequest());

        List<Reagent> reagentList = reagentRepository.findAll();
        assertThat(reagentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWasterunitpriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = reagentRepository.findAll().size();
        // set the field null
        reagent.setWasterunitprice(null);

        // Create the Reagent, which fails.

        restReagentMockMvc.perform(post("/api/reagents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reagent)))
            .andExpect(status().isBadRequest());

        List<Reagent> reagentList = reagentRepository.findAll();
        assertThat(reagentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReagents() throws Exception {
        // Initialize the database
        reagentRepository.saveAndFlush(reagent);

        // Get all the reagentList
        restReagentMockMvc.perform(get("/api/reagents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reagent.getId().intValue())))
            .andExpect(jsonPath("$.[*].reagentname").value(hasItem(DEFAULT_REAGENTNAME.toString())))
            .andExpect(jsonPath("$.[*].unitprice").value(hasItem(DEFAULT_UNITPRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].wasterunitprice").value(hasItem(DEFAULT_WASTERUNITPRICE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getReagent() throws Exception {
        // Initialize the database
        reagentRepository.saveAndFlush(reagent);

        // Get the reagent
        restReagentMockMvc.perform(get("/api/reagents/{id}", reagent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reagent.getId().intValue()))
            .andExpect(jsonPath("$.reagentname").value(DEFAULT_REAGENTNAME.toString()))
            .andExpect(jsonPath("$.unitprice").value(DEFAULT_UNITPRICE.doubleValue()))
            .andExpect(jsonPath("$.wasterunitprice").value(DEFAULT_WASTERUNITPRICE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingReagent() throws Exception {
        // Get the reagent
        restReagentMockMvc.perform(get("/api/reagents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReagent() throws Exception {
        // Initialize the database
        reagentRepository.saveAndFlush(reagent);

        int databaseSizeBeforeUpdate = reagentRepository.findAll().size();

        // Update the reagent
        Reagent updatedReagent = reagentRepository.findById(reagent.getId()).get();
        // Disconnect from session so that the updates on updatedReagent are not directly saved in db
        em.detach(updatedReagent);
        updatedReagent
            .reagentname(UPDATED_REAGENTNAME)
            .unitprice(UPDATED_UNITPRICE)
            .wasterunitprice(UPDATED_WASTERUNITPRICE);

        restReagentMockMvc.perform(put("/api/reagents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReagent)))
            .andExpect(status().isOk());

        // Validate the Reagent in the database
        List<Reagent> reagentList = reagentRepository.findAll();
        assertThat(reagentList).hasSize(databaseSizeBeforeUpdate);
        Reagent testReagent = reagentList.get(reagentList.size() - 1);
        assertThat(testReagent.getReagentname()).isEqualTo(UPDATED_REAGENTNAME);
        assertThat(testReagent.getUnitprice()).isEqualTo(UPDATED_UNITPRICE);
        assertThat(testReagent.getWasterunitprice()).isEqualTo(UPDATED_WASTERUNITPRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingReagent() throws Exception {
        int databaseSizeBeforeUpdate = reagentRepository.findAll().size();

        // Create the Reagent

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReagentMockMvc.perform(put("/api/reagents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reagent)))
            .andExpect(status().isBadRequest());

        // Validate the Reagent in the database
        List<Reagent> reagentList = reagentRepository.findAll();
        assertThat(reagentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReagent() throws Exception {
        // Initialize the database
        reagentRepository.saveAndFlush(reagent);

        int databaseSizeBeforeDelete = reagentRepository.findAll().size();

        // Delete the reagent
        restReagentMockMvc.perform(delete("/api/reagents/{id}", reagent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Reagent> reagentList = reagentRepository.findAll();
        assertThat(reagentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reagent.class);
        Reagent reagent1 = new Reagent();
        reagent1.setId(1L);
        Reagent reagent2 = new Reagent();
        reagent2.setId(reagent1.getId());
        assertThat(reagent1).isEqualTo(reagent2);
        reagent2.setId(2L);
        assertThat(reagent1).isNotEqualTo(reagent2);
        reagent1.setId(null);
        assertThat(reagent1).isNotEqualTo(reagent2);
    }
}
