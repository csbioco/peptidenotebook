package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PeptidenotebookApp;

import com.mycompany.myapp.domain.Protocol;
import com.mycompany.myapp.repository.ProtocolRepository;
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
 * Test class for the ProtocolResource REST controller.
 *
 * @see ProtocolResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PeptidenotebookApp.class)
public class ProtocolResourceIntTest {

    private static final String DEFAULT_PROTOCOLNAME = "AAAAAAAAAA";
    private static final String UPDATED_PROTOCOLNAME = "BBBBBBBBBB";

    @Autowired
    private ProtocolRepository protocolRepository;

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

    private MockMvc restProtocolMockMvc;

    private Protocol protocol;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProtocolResource protocolResource = new ProtocolResource(protocolRepository);
        this.restProtocolMockMvc = MockMvcBuilders.standaloneSetup(protocolResource)
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
    public static Protocol createEntity(EntityManager em) {
        Protocol protocol = new Protocol()
            .protocolname(DEFAULT_PROTOCOLNAME);
        return protocol;
    }

    @Before
    public void initTest() {
        protocol = createEntity(em);
    }

    @Test
    @Transactional
    public void createProtocol() throws Exception {
        int databaseSizeBeforeCreate = protocolRepository.findAll().size();

        // Create the Protocol
        restProtocolMockMvc.perform(post("/api/protocols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(protocol)))
            .andExpect(status().isCreated());

        // Validate the Protocol in the database
        List<Protocol> protocolList = protocolRepository.findAll();
        assertThat(protocolList).hasSize(databaseSizeBeforeCreate + 1);
        Protocol testProtocol = protocolList.get(protocolList.size() - 1);
        assertThat(testProtocol.getProtocolname()).isEqualTo(DEFAULT_PROTOCOLNAME);
    }

    @Test
    @Transactional
    public void createProtocolWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = protocolRepository.findAll().size();

        // Create the Protocol with an existing ID
        protocol.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProtocolMockMvc.perform(post("/api/protocols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(protocol)))
            .andExpect(status().isBadRequest());

        // Validate the Protocol in the database
        List<Protocol> protocolList = protocolRepository.findAll();
        assertThat(protocolList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkProtocolnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = protocolRepository.findAll().size();
        // set the field null
        protocol.setProtocolname(null);

        // Create the Protocol, which fails.

        restProtocolMockMvc.perform(post("/api/protocols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(protocol)))
            .andExpect(status().isBadRequest());

        List<Protocol> protocolList = protocolRepository.findAll();
        assertThat(protocolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProtocols() throws Exception {
        // Initialize the database
        protocolRepository.saveAndFlush(protocol);

        // Get all the protocolList
        restProtocolMockMvc.perform(get("/api/protocols?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(protocol.getId().intValue())))
            .andExpect(jsonPath("$.[*].protocolname").value(hasItem(DEFAULT_PROTOCOLNAME.toString())));
    }
    
    @Test
    @Transactional
    public void getProtocol() throws Exception {
        // Initialize the database
        protocolRepository.saveAndFlush(protocol);

        // Get the protocol
        restProtocolMockMvc.perform(get("/api/protocols/{id}", protocol.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(protocol.getId().intValue()))
            .andExpect(jsonPath("$.protocolname").value(DEFAULT_PROTOCOLNAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProtocol() throws Exception {
        // Get the protocol
        restProtocolMockMvc.perform(get("/api/protocols/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProtocol() throws Exception {
        // Initialize the database
        protocolRepository.saveAndFlush(protocol);

        int databaseSizeBeforeUpdate = protocolRepository.findAll().size();

        // Update the protocol
        Protocol updatedProtocol = protocolRepository.findById(protocol.getId()).get();
        // Disconnect from session so that the updates on updatedProtocol are not directly saved in db
        em.detach(updatedProtocol);
        updatedProtocol
            .protocolname(UPDATED_PROTOCOLNAME);

        restProtocolMockMvc.perform(put("/api/protocols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProtocol)))
            .andExpect(status().isOk());

        // Validate the Protocol in the database
        List<Protocol> protocolList = protocolRepository.findAll();
        assertThat(protocolList).hasSize(databaseSizeBeforeUpdate);
        Protocol testProtocol = protocolList.get(protocolList.size() - 1);
        assertThat(testProtocol.getProtocolname()).isEqualTo(UPDATED_PROTOCOLNAME);
    }

    @Test
    @Transactional
    public void updateNonExistingProtocol() throws Exception {
        int databaseSizeBeforeUpdate = protocolRepository.findAll().size();

        // Create the Protocol

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProtocolMockMvc.perform(put("/api/protocols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(protocol)))
            .andExpect(status().isBadRequest());

        // Validate the Protocol in the database
        List<Protocol> protocolList = protocolRepository.findAll();
        assertThat(protocolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProtocol() throws Exception {
        // Initialize the database
        protocolRepository.saveAndFlush(protocol);

        int databaseSizeBeforeDelete = protocolRepository.findAll().size();

        // Delete the protocol
        restProtocolMockMvc.perform(delete("/api/protocols/{id}", protocol.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Protocol> protocolList = protocolRepository.findAll();
        assertThat(protocolList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Protocol.class);
        Protocol protocol1 = new Protocol();
        protocol1.setId(1L);
        Protocol protocol2 = new Protocol();
        protocol2.setId(protocol1.getId());
        assertThat(protocol1).isEqualTo(protocol2);
        protocol2.setId(2L);
        assertThat(protocol1).isNotEqualTo(protocol2);
        protocol1.setId(null);
        assertThat(protocol1).isNotEqualTo(protocol2);
    }
}
