package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.DemoApp;

import com.mycompany.myapp.domain.Lab;
import com.mycompany.myapp.repository.LabRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LabResource REST controller.
 *
 * @see LabResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApp.class)
public class LabResourceIntTest {

    private static final String DEFAULT_LAB_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAB_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAB_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_LAB_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_LAB_VOLUME = 1;
    private static final Integer UPDATED_LAB_VOLUME = 2;

    @Autowired
    private LabRepository labRepository;

    @Mock
    private LabRepository labRepositoryMock;

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

    private MockMvc restLabMockMvc;

    private Lab lab;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LabResource labResource = new LabResource(labRepository);
        this.restLabMockMvc = MockMvcBuilders.standaloneSetup(labResource)
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
    public static Lab createEntity(EntityManager em) {
        Lab lab = new Lab()
            .labName(DEFAULT_LAB_NAME)
            .labType(DEFAULT_LAB_TYPE)
            .labVolume(DEFAULT_LAB_VOLUME);
        return lab;
    }

    @Before
    public void initTest() {
        lab = createEntity(em);
    }

    @Test
    @Transactional
    public void createLab() throws Exception {
        int databaseSizeBeforeCreate = labRepository.findAll().size();

        // Create the Lab
        restLabMockMvc.perform(post("/api/labs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lab)))
            .andExpect(status().isCreated());

        // Validate the Lab in the database
        List<Lab> labList = labRepository.findAll();
        assertThat(labList).hasSize(databaseSizeBeforeCreate + 1);
        Lab testLab = labList.get(labList.size() - 1);
        assertThat(testLab.getLabName()).isEqualTo(DEFAULT_LAB_NAME);
        assertThat(testLab.getLabType()).isEqualTo(DEFAULT_LAB_TYPE);
        assertThat(testLab.getLabVolume()).isEqualTo(DEFAULT_LAB_VOLUME);
    }

    @Test
    @Transactional
    public void createLabWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = labRepository.findAll().size();

        // Create the Lab with an existing ID
        lab.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLabMockMvc.perform(post("/api/labs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lab)))
            .andExpect(status().isBadRequest());

        // Validate the Lab in the database
        List<Lab> labList = labRepository.findAll();
        assertThat(labList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLabNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = labRepository.findAll().size();
        // set the field null
        lab.setLabName(null);

        // Create the Lab, which fails.

        restLabMockMvc.perform(post("/api/labs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lab)))
            .andExpect(status().isBadRequest());

        List<Lab> labList = labRepository.findAll();
        assertThat(labList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = labRepository.findAll().size();
        // set the field null
        lab.setLabType(null);

        // Create the Lab, which fails.

        restLabMockMvc.perform(post("/api/labs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lab)))
            .andExpect(status().isBadRequest());

        List<Lab> labList = labRepository.findAll();
        assertThat(labList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLabs() throws Exception {
        // Initialize the database
        labRepository.saveAndFlush(lab);

        // Get all the labList
        restLabMockMvc.perform(get("/api/labs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lab.getId().intValue())))
            .andExpect(jsonPath("$.[*].labName").value(hasItem(DEFAULT_LAB_NAME.toString())))
            .andExpect(jsonPath("$.[*].labType").value(hasItem(DEFAULT_LAB_TYPE.toString())))
            .andExpect(jsonPath("$.[*].labVolume").value(hasItem(DEFAULT_LAB_VOLUME)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllLabsWithEagerRelationshipsIsEnabled() throws Exception {
        LabResource labResource = new LabResource(labRepositoryMock);
        when(labRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restLabMockMvc = MockMvcBuilders.standaloneSetup(labResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restLabMockMvc.perform(get("/api/labs?eagerload=true"))
        .andExpect(status().isOk());

        verify(labRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllLabsWithEagerRelationshipsIsNotEnabled() throws Exception {
        LabResource labResource = new LabResource(labRepositoryMock);
            when(labRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restLabMockMvc = MockMvcBuilders.standaloneSetup(labResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restLabMockMvc.perform(get("/api/labs?eagerload=true"))
        .andExpect(status().isOk());

            verify(labRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getLab() throws Exception {
        // Initialize the database
        labRepository.saveAndFlush(lab);

        // Get the lab
        restLabMockMvc.perform(get("/api/labs/{id}", lab.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lab.getId().intValue()))
            .andExpect(jsonPath("$.labName").value(DEFAULT_LAB_NAME.toString()))
            .andExpect(jsonPath("$.labType").value(DEFAULT_LAB_TYPE.toString()))
            .andExpect(jsonPath("$.labVolume").value(DEFAULT_LAB_VOLUME));
    }

    @Test
    @Transactional
    public void getNonExistingLab() throws Exception {
        // Get the lab
        restLabMockMvc.perform(get("/api/labs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLab() throws Exception {
        // Initialize the database
        labRepository.saveAndFlush(lab);

        int databaseSizeBeforeUpdate = labRepository.findAll().size();

        // Update the lab
        Lab updatedLab = labRepository.findById(lab.getId()).get();
        // Disconnect from session so that the updates on updatedLab are not directly saved in db
        em.detach(updatedLab);
        updatedLab
            .labName(UPDATED_LAB_NAME)
            .labType(UPDATED_LAB_TYPE)
            .labVolume(UPDATED_LAB_VOLUME);

        restLabMockMvc.perform(put("/api/labs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLab)))
            .andExpect(status().isOk());

        // Validate the Lab in the database
        List<Lab> labList = labRepository.findAll();
        assertThat(labList).hasSize(databaseSizeBeforeUpdate);
        Lab testLab = labList.get(labList.size() - 1);
        assertThat(testLab.getLabName()).isEqualTo(UPDATED_LAB_NAME);
        assertThat(testLab.getLabType()).isEqualTo(UPDATED_LAB_TYPE);
        assertThat(testLab.getLabVolume()).isEqualTo(UPDATED_LAB_VOLUME);
    }

    @Test
    @Transactional
    public void updateNonExistingLab() throws Exception {
        int databaseSizeBeforeUpdate = labRepository.findAll().size();

        // Create the Lab

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLabMockMvc.perform(put("/api/labs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lab)))
            .andExpect(status().isBadRequest());

        // Validate the Lab in the database
        List<Lab> labList = labRepository.findAll();
        assertThat(labList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLab() throws Exception {
        // Initialize the database
        labRepository.saveAndFlush(lab);

        int databaseSizeBeforeDelete = labRepository.findAll().size();

        // Get the lab
        restLabMockMvc.perform(delete("/api/labs/{id}", lab.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Lab> labList = labRepository.findAll();
        assertThat(labList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lab.class);
        Lab lab1 = new Lab();
        lab1.setId(1L);
        Lab lab2 = new Lab();
        lab2.setId(lab1.getId());
        assertThat(lab1).isEqualTo(lab2);
        lab2.setId(2L);
        assertThat(lab1).isNotEqualTo(lab2);
        lab1.setId(null);
        assertThat(lab1).isNotEqualTo(lab2);
    }
}
