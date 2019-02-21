package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Grade;
import com.mycompany.myapp.repository.GradeRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Grade.
 */
@RestController
@RequestMapping("/api")
@Api(value = "班级管理",description = "班级管理")
public class GradeResource {

    private final Logger log = LoggerFactory.getLogger(GradeResource.class);

    private static final String ENTITY_NAME = "grade";

    private final GradeRepository gradeRepository;

    public GradeResource(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    /**
     * POST  /grades : Create a new grade.
     *
     * @param grade the grade to create
     * @return the ResponseEntity with status 201 (Created) and with body the new grade, or with status 400 (Bad Request) if the grade has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/grades")
    @Timed
    @ApiOperation(value = "创建班级")
    public ResponseEntity<Grade> createGrade(@Valid @RequestBody Grade grade) throws URISyntaxException {
        log.debug("REST request to save Grade : {}", grade);
        if (grade.getId() != null) {
            throw new BadRequestAlertException("A new grade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Grade result = gradeRepository.save(grade);
        return ResponseEntity.created(new URI("/api/grades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /grades : Updates an existing grade.
     *
     * @param grade the grade to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated grade,
     * or with status 400 (Bad Request) if the grade is not valid,
     * or with status 500 (Internal Server Error) if the grade couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/grades")
    @Timed
    @ApiOperation(value = "修改班级")
    public ResponseEntity<Grade> updateGrade(@Valid @RequestBody Grade grade) throws URISyntaxException {
        log.debug("REST request to update Grade : {}", grade);
        if (grade.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Grade result = gradeRepository.save(grade);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, grade.getId().toString()))
            .body(result);
    }

    /**
     * GET  /grades : get all the grades.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of grades in body
     */
    @GetMapping("/grades")
    @Timed
    @ApiOperation(value = "获取所有班级")
    public ResponseEntity<List<Grade>> getAllGrades(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Grades");
        Page<Grade> page;
        if (eagerload) {
            page = gradeRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = gradeRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/grades?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /grades/:id : get the "id" grade.
     *
     * @param id the id of the grade to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the grade, or with status 404 (Not Found)
     */
    @GetMapping("/grades/{id}")
    @Timed
    @ApiOperation(value = "获取班级")
    public ResponseEntity<Grade> getGrade(@PathVariable Long id) {
        log.debug("REST request to get Grade : {}", id);
        Optional<Grade> grade = gradeRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(grade);
    }

    /**
     * DELETE  /grades/:id : delete the "id" grade.
     *
     * @param id the id of the grade to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/grades/{id}")
    @Timed
    @ApiOperation(value = "删除班级")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long id) {
        log.debug("REST request to delete Grade : {}", id);

        gradeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
