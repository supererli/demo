package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.DemoApp;

import com.mycompany.myapp.domain.TimeTable;
import com.mycompany.myapp.repository.TimeTableRepository;
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
 * Test class for the TimeTableResource REST controller.
 *
 * @see TimeTableResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApp.class)
public class TimeTableResourceIntTest {

    private static final String DEFAULT_WEEKDAY = "AAAAAAAAAA";
    private static final String UPDATED_WEEKDAY = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;

    @Autowired
    private TimeTableRepository timeTableRepository;

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

    private MockMvc restTimeTableMockMvc;

    private TimeTable timeTable;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TimeTableResource timeTableResource = new TimeTableResource(timeTableRepository);
        this.restTimeTableMockMvc = MockMvcBuilders.standaloneSetup(timeTableResource)
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
    public static TimeTable createEntity(EntityManager em) {
        TimeTable timeTable = new TimeTable()
            .weekday(DEFAULT_WEEKDAY)
            .number(DEFAULT_NUMBER);
        return timeTable;
    }

    @Before
    public void initTest() {
        timeTable = createEntity(em);
    }

    @Test
    @Transactional
    public void createTimeTable() throws Exception {
        int databaseSizeBeforeCreate = timeTableRepository.findAll().size();

        // Create the TimeTable
        restTimeTableMockMvc.perform(post("/api/time-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeTable)))
            .andExpect(status().isCreated());

        // Validate the TimeTable in the database
        List<TimeTable> timeTableList = timeTableRepository.findAll();
        assertThat(timeTableList).hasSize(databaseSizeBeforeCreate + 1);
        TimeTable testTimeTable = timeTableList.get(timeTableList.size() - 1);
        assertThat(testTimeTable.getWeekday()).isEqualTo(DEFAULT_WEEKDAY);
        assertThat(testTimeTable.getNumber()).isEqualTo(DEFAULT_NUMBER);
    }

    @Test
    @Transactional
    public void createTimeTableWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = timeTableRepository.findAll().size();

        // Create the TimeTable with an existing ID
        timeTable.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimeTableMockMvc.perform(post("/api/time-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeTable)))
            .andExpect(status().isBadRequest());

        // Validate the TimeTable in the database
        List<TimeTable> timeTableList = timeTableRepository.findAll();
        assertThat(timeTableList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkWeekdayIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeTableRepository.findAll().size();
        // set the field null
        timeTable.setWeekday(null);

        // Create the TimeTable, which fails.

        restTimeTableMockMvc.perform(post("/api/time-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeTable)))
            .andExpect(status().isBadRequest());

        List<TimeTable> timeTableList = timeTableRepository.findAll();
        assertThat(timeTableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeTableRepository.findAll().size();
        // set the field null
        timeTable.setNumber(null);

        // Create the TimeTable, which fails.

        restTimeTableMockMvc.perform(post("/api/time-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeTable)))
            .andExpect(status().isBadRequest());

        List<TimeTable> timeTableList = timeTableRepository.findAll();
        assertThat(timeTableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTimeTables() throws Exception {
        // Initialize the database
        timeTableRepository.saveAndFlush(timeTable);

        // Get all the timeTableList
        restTimeTableMockMvc.perform(get("/api/time-tables?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timeTable.getId().intValue())))
            .andExpect(jsonPath("$.[*].weekday").value(hasItem(DEFAULT_WEEKDAY.toString())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getTimeTable() throws Exception {
        // Initialize the database
        timeTableRepository.saveAndFlush(timeTable);

        // Get the timeTable
        restTimeTableMockMvc.perform(get("/api/time-tables/{id}", timeTable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(timeTable.getId().intValue()))
            .andExpect(jsonPath("$.weekday").value(DEFAULT_WEEKDAY.toString()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER));
    }

    @Test
    @Transactional
    public void getNonExistingTimeTable() throws Exception {
        // Get the timeTable
        restTimeTableMockMvc.perform(get("/api/time-tables/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimeTable() throws Exception {
        // Initialize the database
        timeTableRepository.saveAndFlush(timeTable);

        int databaseSizeBeforeUpdate = timeTableRepository.findAll().size();

        // Update the timeTable
        TimeTable updatedTimeTable = timeTableRepository.findById(timeTable.getId()).get();
        // Disconnect from session so that the updates on updatedTimeTable are not directly saved in db
        em.detach(updatedTimeTable);
        updatedTimeTable
            .weekday(UPDATED_WEEKDAY)
            .number(UPDATED_NUMBER);

        restTimeTableMockMvc.perform(put("/api/time-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTimeTable)))
            .andExpect(status().isOk());

        // Validate the TimeTable in the database
        List<TimeTable> timeTableList = timeTableRepository.findAll();
        assertThat(timeTableList).hasSize(databaseSizeBeforeUpdate);
        TimeTable testTimeTable = timeTableList.get(timeTableList.size() - 1);
        assertThat(testTimeTable.getWeekday()).isEqualTo(UPDATED_WEEKDAY);
        assertThat(testTimeTable.getNumber()).isEqualTo(UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingTimeTable() throws Exception {
        int databaseSizeBeforeUpdate = timeTableRepository.findAll().size();

        // Create the TimeTable

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimeTableMockMvc.perform(put("/api/time-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeTable)))
            .andExpect(status().isBadRequest());

        // Validate the TimeTable in the database
        List<TimeTable> timeTableList = timeTableRepository.findAll();
        assertThat(timeTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTimeTable() throws Exception {
        // Initialize the database
        timeTableRepository.saveAndFlush(timeTable);

        int databaseSizeBeforeDelete = timeTableRepository.findAll().size();

        // Get the timeTable
        restTimeTableMockMvc.perform(delete("/api/time-tables/{id}", timeTable.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TimeTable> timeTableList = timeTableRepository.findAll();
        assertThat(timeTableList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TimeTable.class);
        TimeTable timeTable1 = new TimeTable();
        timeTable1.setId(1L);
        TimeTable timeTable2 = new TimeTable();
        timeTable2.setId(timeTable1.getId());
        assertThat(timeTable1).isEqualTo(timeTable2);
        timeTable2.setId(2L);
        assertThat(timeTable1).isNotEqualTo(timeTable2);
        timeTable1.setId(null);
        assertThat(timeTable1).isNotEqualTo(timeTable2);
    }
}
