package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Lab;
import com.mycompany.myapp.repository.LabRepository;
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
 * REST controller for managing Lab.
 */
@RestController
@RequestMapping("/api")
@Api(value = "实验室管理",description = "实验室管理")
public class LabResource {

    private final Logger log = LoggerFactory.getLogger(LabResource.class);

    private static final String ENTITY_NAME = "lab";

    private final LabRepository labRepository;

    public LabResource(LabRepository labRepository) {
        this.labRepository = labRepository;
    }

    /**
     * POST  /labs : Create a new lab.
     *
     * @param lab the lab to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lab, or with status 400 (Bad Request) if the lab has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/labs")
    @Timed
    @ApiOperation(value = "创建实验室")
    public ResponseEntity<Lab> createLab(@Valid @RequestBody Lab lab) throws URISyntaxException {
        log.debug("REST request to save Lab : {}", lab);
        if (lab.getId() != null) {
            throw new BadRequestAlertException("A new lab cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Lab result = labRepository.save(lab);
        return ResponseEntity.created(new URI("/api/labs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /labs : Updates an existing lab.
     *
     * @param lab the lab to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lab,
     * or with status 400 (Bad Request) if the lab is not valid,
     * or with status 500 (Internal Server Error) if the lab couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/labs")
    @Timed
    @ApiOperation(value = "修改实验室")
    public ResponseEntity<Lab> updateLab(@Valid @RequestBody Lab lab) throws URISyntaxException {
        log.debug("REST request to update Lab : {}", lab);
        if (lab.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Lab result = labRepository.save(lab);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lab.getId().toString()))
            .body(result);
    }

    /**
     * GET  /labs : get all the labs.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of labs in body
     */
    @GetMapping("/labs")
    @Timed
    @ApiOperation(value = "获取所有实验室")
    public ResponseEntity<List<Lab>> getAllLabs(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Labs");
        Page<Lab> page;
        if (eagerload) {
            page = labRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = labRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/labs?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /labs/:id : get the "id" lab.
     *
     * @param id the id of the lab to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lab, or with status 404 (Not Found)
     */
    @GetMapping("/labs/{id}")
    @Timed
    @ApiOperation(value = "获取实验室")
    public ResponseEntity<Lab> getLab(@PathVariable Long id) {
        log.debug("REST request to get Lab : {}", id);
        Optional<Lab> lab = labRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(lab);
    }

    /**
     * DELETE  /labs/:id : delete the "id" lab.
     *
     * @param id the id of the lab to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/labs/{id}")
    @Timed
    @ApiOperation(value = "删除实验室")
    public ResponseEntity<Void> deleteLab(@PathVariable Long id) {
        log.debug("REST request to delete Lab : {}", id);

        labRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
