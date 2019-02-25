package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.DemoApp;

import com.mycompany.myapp.domain.Grade;
import com.mycompany.myapp.repository.GradeRepository;
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
 * Test class for the GradeResource REST controller.
 *
 * @see GradeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApp.class)
public class GradeResourceIntTest {

    private static final String DEFAULT_GRADE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GRADE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GRADE_ACADEMY = "AAAAAAAAAA";
    private static final String UPDATED_GRADE_ACADEMY = "BBBBBBBBBB";

    private static final String DEFAULT_GRADE_DEPARTMENT = "AAAAAAAAAA";
    private static final String UPDATED_GRADE_DEPARTMENT = "BBBBBBBBBB";

    @Autowired
    private GradeRepository gradeRepository;

    @Mock
    private GradeRepository gradeRepositoryMock;

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

    private MockMvc restGradeMockMvc;

    private Grade grade;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GradeResource gradeResource = new GradeResource(gradeRepository);
        this.restGradeMockMvc = MockMvcBuilders.standaloneSetup(gradeResource)
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
    public static Grade createEntity(EntityManager em) {
        Grade grade = new Grade()
            .gradeName(DEFAULT_GRADE_NAME)
            .gradeAcademy(DEFAULT_GRADE_ACADEMY)
            .gradeDepartment(DEFAULT_GRADE_DEPARTMENT);
        return grade;
    }

    @Before
    public void initTest() {
        grade = createEntity(em);
    }

    @Test
    @Transactional
    public void createGrade() throws Exception {
        int databaseSizeBeforeCreate = gradeRepository.findAll().size();

        // Create the Grade
        restGradeMockMvc.perform(post("/api/grades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grade)))
            .andExpect(status().isCreated());

        // Validate the Grade in the database
        List<Grade> gradeList = gradeRepository.findAll();
        assertThat(gradeList).hasSize(databaseSizeBeforeCreate + 1);
        Grade testGrade = gradeList.get(gradeList.size() - 1);
        assertThat(testGrade.getGradeName()).isEqualTo(DEFAULT_GRADE_NAME);
        assertThat(testGrade.getGradeAcademy()).isEqualTo(DEFAULT_GRADE_ACADEMY);
        assertThat(testGrade.getGradeDepartment()).isEqualTo(DEFAULT_GRADE_DEPARTMENT);
    }

    @Test
    @Transactional
    public void createGradeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gradeRepository.findAll().size();

        // Create the Grade with an existing ID
        grade.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGradeMockMvc.perform(post("/api/grades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grade)))
            .andExpect(status().isBadRequest());

        // Validate the Grade in the database
        List<Grade> gradeList = gradeRepository.findAll();
        assertThat(gradeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkGradeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = gradeRepository.findAll().size();
        // set the field null
        grade.setGradeName(null);

        // Create the Grade, which fails.

        restGradeMockMvc.perform(post("/api/grades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grade)))
            .andExpect(status().isBadRequest());

        List<Grade> gradeList = gradeRepository.findAll();
        assertThat(gradeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGradeAcademyIsRequired() throws Exception {
        int databaseSizeBeforeTest = gradeRepository.findAll().size();
        // set the field null
        grade.setGradeAcademy(null);

        // Create the Grade, which fails.

        restGradeMockMvc.perform(post("/api/grades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grade)))
            .andExpect(status().isBadRequest());

        List<Grade> gradeList = gradeRepository.findAll();
        assertThat(gradeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGrades() throws Exception {
        // Initialize the database
        gradeRepository.saveAndFlush(grade);

        // Get all the gradeList
        restGradeMockMvc.perform(get("/api/grades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grade.getId().intValue())))
            .andExpect(jsonPath("$.[*].gradeName").value(hasItem(DEFAULT_GRADE_NAME.toString())))
            .andExpect(jsonPath("$.[*].gradeAcademy").value(hasItem(DEFAULT_GRADE_ACADEMY.toString())))
            .andExpect(jsonPath("$.[*].gradeDepartment").value(hasItem(DEFAULT_GRADE_DEPARTMENT.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllGradesWithEagerRelationshipsIsEnabled() throws Exception {
        GradeResource gradeResource = new GradeResource(gradeRepositoryMock);
        when(gradeRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restGradeMockMvc = MockMvcBuilders.standaloneSetup(gradeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restGradeMockMvc.perform(get("/api/grades?eagerload=true"))
        .andExpect(status().isOk());

        verify(gradeRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllGradesWithEagerRelationshipsIsNotEnabled() throws Exception {
        GradeResource gradeResource = new GradeResource(gradeRepositoryMock);
            when(gradeRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restGradeMockMvc = MockMvcBuilders.standaloneSetup(gradeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restGradeMockMvc.perform(get("/api/grades?eagerload=true"))
        .andExpect(status().isOk());

            verify(gradeRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getGrade() throws Exception {
        // Initialize the database
        gradeRepository.saveAndFlush(grade);

        // Get the grade
        restGradeMockMvc.perform(get("/api/grades/{id}", grade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(grade.getId().intValue()))
            .andExpect(jsonPath("$.gradeName").value(DEFAULT_GRADE_NAME.toString()))
            .andExpect(jsonPath("$.gradeAcademy").value(DEFAULT_GRADE_ACADEMY.toString()))
            .andExpect(jsonPath("$.gradeDepartment").value(DEFAULT_GRADE_DEPARTMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGrade() throws Exception {
        // Get the grade
        restGradeMockMvc.perform(get("/api/grades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGrade() throws Exception {
        // Initialize the database
        gradeRepository.saveAndFlush(grade);

        int databaseSizeBeforeUpdate = gradeRepository.findAll().size();

        // Update the grade
        Grade updatedGrade = gradeRepository.findById(grade.getId()).get();
        // Disconnect from session so that the updates on updatedGrade are not directly saved in db
        em.detach(updatedGrade);
        updatedGrade
            .gradeName(UPDATED_GRADE_NAME)
            .gradeAcademy(UPDATED_GRADE_ACADEMY)
            .gradeDepartment(UPDATED_GRADE_DEPARTMENT);

        restGradeMockMvc.perform(put("/api/grades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGrade)))
            .andExpect(status().isOk());

        // Validate the Grade in the database
        List<Grade> gradeList = gradeRepository.findAll();
        assertThat(gradeList).hasSize(databaseSizeBeforeUpdate);
        Grade testGrade = gradeList.get(gradeList.size() - 1);
        assertThat(testGrade.getGradeName()).isEqualTo(UPDATED_GRADE_NAME);
        assertThat(testGrade.getGradeAcademy()).isEqualTo(UPDATED_GRADE_ACADEMY);
        assertThat(testGrade.getGradeDepartment()).isEqualTo(UPDATED_GRADE_DEPARTMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingGrade() throws Exception {
        int databaseSizeBeforeUpdate = gradeRepository.findAll().size();

        // Create the Grade

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGradeMockMvc.perform(put("/api/grades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grade)))
            .andExpect(status().isBadRequest());

        // Validate the Grade in the database
        List<Grade> gradeList = gradeRepository.findAll();
        assertThat(gradeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGrade() throws Exception {
        // Initialize the database
        gradeRepository.saveAndFlush(grade);

        int databaseSizeBeforeDelete = gradeRepository.findAll().size();

        // Get the grade
        restGradeMockMvc.perform(delete("/api/grades/{id}", grade.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Grade> gradeList = gradeRepository.findAll();
        assertThat(gradeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Grade.class);
        Grade grade1 = new Grade();
        grade1.setId(1L);
        Grade grade2 = new Grade();
        grade2.setId(grade1.getId());
        assertThat(grade1).isEqualTo(grade2);
        grade2.setId(2L);
        assertThat(grade1).isNotEqualTo(grade2);
        grade1.setId(null);
        assertThat(grade1).isNotEqualTo(grade2);
    }
}
