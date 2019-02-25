package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.TimeTable;
import com.mycompany.myapp.repository.TimeTableRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TimeTable.
 */
@RestController
@RequestMapping("/api")
@Api(value = "",description = "时间表管理")
public class TimeTableResource {

    private final Logger log = LoggerFactory.getLogger(TimeTableResource.class);

    private static final String ENTITY_NAME = "timeTable";

    private final TimeTableRepository timeTableRepository;

    public TimeTableResource(TimeTableRepository timeTableRepository) {
        this.timeTableRepository = timeTableRepository;
    }

    /**
     * POST  /time-tables : Create a new timeTable.
     *
     * @param timeTable the timeTable to create
     * @return the ResponseEntity with status 201 (Created) and with body the new timeTable, or with status 400 (Bad Request) if the timeTable has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/time-tables")
    @Timed
    @ApiOperation(value = "创建时间表")
    public ResponseEntity<TimeTable> createTimeTable(@Valid @RequestBody TimeTable timeTable) throws URISyntaxException {
        log.debug("REST request to save TimeTable : {}", timeTable);
        if (timeTable.getId() != null) {
            throw new BadRequestAlertException("A new timeTable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TimeTable result = timeTableRepository.save(timeTable);
        return ResponseEntity.created(new URI("/api/time-tables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /time-tables : Updates an existing timeTable.
     *
     * @param timeTable the timeTable to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated timeTable,
     * or with status 400 (Bad Request) if the timeTable is not valid,
     * or with status 500 (Internal Server Error) if the timeTable couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/time-tables")
    @Timed
    @ApiOperation(value = "修改时间表")
    public ResponseEntity<TimeTable> updateTimeTable(@Valid @RequestBody TimeTable timeTable) throws URISyntaxException {
        log.debug("REST request to update TimeTable : {}", timeTable);
        if (timeTable.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TimeTable result = timeTableRepository.save(timeTable);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, timeTable.getId().toString()))
            .body(result);
    }

    /**
     * GET  /time-tables : get all the timeTables.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of timeTables in body
     */
    @GetMapping("/time-tables")
    @Timed
    @ApiOperation(value = "获取所有时间表")
    public List<TimeTable> getAllTimeTables() {
        log.debug("REST request to get all TimeTables");
        return timeTableRepository.findAll();
    }

    /**
     * GET  /time-tables/:id : get the "id" timeTable.
     *
     * @param id the id of the timeTable to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the timeTable, or with status 404 (Not Found)
     */
    @GetMapping("/time-tables/{id}")
    @Timed
    @ApiOperation(value = "获取时间表")
    public ResponseEntity<TimeTable> getTimeTable(@PathVariable Long id) {
        log.debug("REST request to get TimeTable : {}", id);
        Optional<TimeTable> timeTable = timeTableRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(timeTable);
    }

    /**
     * DELETE  /time-tables/:id : delete the "id" timeTable.
     *
     * @param id the id of the timeTable to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/time-tables/{id}")
    @Timed
    @ApiOperation(value = "删除时间表")
    public ResponseEntity<Void> deleteTimeTable(@PathVariable Long id) {
        log.debug("REST request to delete TimeTable : {}", id);

        timeTableRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
