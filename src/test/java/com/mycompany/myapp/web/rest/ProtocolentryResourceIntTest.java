package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PeptidenotebookApp;

import com.mycompany.myapp.domain.Protocolentry;
import com.mycompany.myapp.repository.ProtocolentryRepository;
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
 * Test class for the ProtocolentryResource REST controller.
 *
 * @see ProtocolentryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PeptidenotebookApp.class)
public class ProtocolentryResourceIntTest {

    private static final String DEFAULT_ORDERNUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ORDERNUMBER = "BBBBBBBBBB";

    @Autowired
    private ProtocolentryRepository protocolentryRepository;

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

    private MockMvc restProtocolentryMockMvc;

    private Protocolentry protocolentry;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProtocolentryResource protocolentryResource = new ProtocolentryResource(protocolentryRepository);
        this.restProtocolentryMockMvc = MockMvcBuilders.standaloneSetup(protocolentryResource)
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
    public static Protocolentry createEntity(EntityManager em) {
        Protocolentry protocolentry = new Protocolentry()
            .ordernumber(DEFAULT_ORDERNUMBER);
        return protocolentry;
    }

    @Before
    public void initTest() {
        protocolentry = createEntity(em);
    }

    @Test
    @Transactional
    public void createProtocolentry() throws Exception {
        int databaseSizeBeforeCreate = protocolentryRepository.findAll().size();

        // Create the Protocolentry
        restProtocolentryMockMvc.perform(post("/api/protocolentries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(protocolentry)))
            .andExpect(status().isCreated());

        // Validate the Protocolentry in the database
        List<Protocolentry> protocolentryList = protocolentryRepository.findAll();
        assertThat(protocolentryList).hasSize(databaseSizeBeforeCreate + 1);
        Protocolentry testProtocolentry = protocolentryList.get(protocolentryList.size() - 1);
        assertThat(testProtocolentry.getOrdernumber()).isEqualTo(DEFAULT_ORDERNUMBER);
    }

    @Test
    @Transactional
    public void createProtocolentryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = protocolentryRepository.findAll().size();

        // Create the Protocolentry with an existing ID
        protocolentry.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProtocolentryMockMvc.perform(post("/api/protocolentries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(protocolentry)))
            .andExpect(status().isBadRequest());

        // Validate the Protocolentry in the database
        List<Protocolentry> protocolentryList = protocolentryRepository.findAll();
        assertThat(protocolentryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkOrdernumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = protocolentryRepository.findAll().size();
        // set the field null
        protocolentry.setOrdernumber(null);

        // Create the Protocolentry, which fails.

        restProtocolentryMockMvc.perform(post("/api/protocolentries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(protocolentry)))
            .andExpect(status().isBadRequest());

        List<Protocolentry> protocolentryList = protocolentryRepository.findAll();
        assertThat(protocolentryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProtocolentries() throws Exception {
        // Initialize the database
        protocolentryRepository.saveAndFlush(protocolentry);

        // Get all the protocolentryList
        restProtocolentryMockMvc.perform(get("/api/protocolentries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(protocolentry.getId().intValue())))
            .andExpect(jsonPath("$.[*].ordernumber").value(hasItem(DEFAULT_ORDERNUMBER.toString())));
    }
    
    @Test
    @Transactional
    public void getProtocolentry() throws Exception {
        // Initialize the database
        protocolentryRepository.saveAndFlush(protocolentry);

        // Get the protocolentry
        restProtocolentryMockMvc.perform(get("/api/protocolentries/{id}", protocolentry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(protocolentry.getId().intValue()))
            .andExpect(jsonPath("$.ordernumber").value(DEFAULT_ORDERNUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProtocolentry() throws Exception {
        // Get the protocolentry
        restProtocolentryMockMvc.perform(get("/api/protocolentries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProtocolentry() throws Exception {
        // Initialize the database
        protocolentryRepository.saveAndFlush(protocolentry);

        int databaseSizeBeforeUpdate = protocolentryRepository.findAll().size();

        // Update the protocolentry
        Protocolentry updatedProtocolentry = protocolentryRepository.findById(protocolentry.getId()).get();
        // Disconnect from session so that the updates on updatedProtocolentry are not directly saved in db
        em.detach(updatedProtocolentry);
        updatedProtocolentry
            .ordernumber(UPDATED_ORDERNUMBER);

        restProtocolentryMockMvc.perform(put("/api/protocolentries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProtocolentry)))
            .andExpect(status().isOk());

        // Validate the Protocolentry in the database
        List<Protocolentry> protocolentryList = protocolentryRepository.findAll();
        assertThat(protocolentryList).hasSize(databaseSizeBeforeUpdate);
        Protocolentry testProtocolentry = protocolentryList.get(protocolentryList.size() - 1);
        assertThat(testProtocolentry.getOrdernumber()).isEqualTo(UPDATED_ORDERNUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingProtocolentry() throws Exception {
        int databaseSizeBeforeUpdate = protocolentryRepository.findAll().size();

        // Create the Protocolentry

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProtocolentryMockMvc.perform(put("/api/protocolentries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(protocolentry)))
            .andExpect(status().isBadRequest());

        // Validate the Protocolentry in the database
        List<Protocolentry> protocolentryList = protocolentryRepository.findAll();
        assertThat(protocolentryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProtocolentry() throws Exception {
        // Initialize the database
        protocolentryRepository.saveAndFlush(protocolentry);

        int databaseSizeBeforeDelete = protocolentryRepository.findAll().size();

        // Delete the protocolentry
        restProtocolentryMockMvc.perform(delete("/api/protocolentries/{id}", protocolentry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Protocolentry> protocolentryList = protocolentryRepository.findAll();
        assertThat(protocolentryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Protocolentry.class);
        Protocolentry protocolentry1 = new Protocolentry();
        protocolentry1.setId(1L);
        Protocolentry protocolentry2 = new Protocolentry();
        protocolentry2.setId(protocolentry1.getId());
        assertThat(protocolentry1).isEqualTo(protocolentry2);
        protocolentry2.setId(2L);
        assertThat(protocolentry1).isNotEqualTo(protocolentry2);
        protocolentry1.setId(null);
        assertThat(protocolentry1).isNotEqualTo(protocolentry2);
    }
}
