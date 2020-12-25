package com.jhipster.projetsoa.web.rest;

import com.jhipster.projetsoa.domain.Competence;
import com.jhipster.projetsoa.service.CompetenceService;
import com.jhipster.projetsoa.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.jhipster.projetsoa.domain.Competence}.
 */
@RestController
@RequestMapping("/api")
public class CompetenceResource {

    private final Logger log = LoggerFactory.getLogger(CompetenceResource.class);

    private static final String ENTITY_NAME = "competence";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompetenceService competenceService;

    public CompetenceResource(CompetenceService competenceService) {
        this.competenceService = competenceService;
    }

    /**
     * {@code POST  /competences} : Create a new competence.
     *
     * @param competence the competence to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new competence, or with status {@code 400 (Bad Request)} if the competence has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/competences")
    public ResponseEntity<Competence> createCompetence(@RequestBody Competence competence) throws URISyntaxException {
        log.debug("REST request to save Competence : {}", competence);
        if (competence.getId() != null) {
            throw new BadRequestAlertException("A new competence cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Competence result = competenceService.save(competence);
        return ResponseEntity.created(new URI("/api/competences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /competences} : Updates an existing competence.
     *
     * @param competence the competence to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competence,
     * or with status {@code 400 (Bad Request)} if the competence is not valid,
     * or with status {@code 500 (Internal Server Error)} if the competence couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/competences")
    public ResponseEntity<Competence> updateCompetence(@RequestBody Competence competence) throws URISyntaxException {
        log.debug("REST request to update Competence : {}", competence);
        if (competence.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Competence result = competenceService.save(competence);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, competence.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /competences} : get all the competences.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of competences in body.
     */
    @GetMapping("/competences")
    public List<Competence> getAllCompetences() {
        log.debug("REST request to get all Competences");
        return competenceService.findAll();
    }

    /**
     * {@code GET  /competences/:id} : get the "id" competence.
     *
     * @param id the id of the competence to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the competence, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/competences/{id}")
    public ResponseEntity<Competence> getCompetence(@PathVariable Long id) {
        log.debug("REST request to get Competence : {}", id);
        Optional<Competence> competence = competenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(competence);
    }

    /**
     * {@code DELETE  /competences/:id} : delete the "id" competence.
     *
     * @param id the id of the competence to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/competences/{id}")
    public ResponseEntity<Void> deleteCompetence(@PathVariable Long id) {
        log.debug("REST request to delete Competence : {}", id);
        competenceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/competences?query=:query} : search for the competence corresponding
     * to the query.
     *
     * @param query the query of the competence search.
     * @return the result of the search.
     */
    @GetMapping("/_search/competences")
    public List<Competence> searchCompetences(@RequestParam String query) {
        log.debug("REST request to search Competences for query {}", query);
        return competenceService.search(query);
    }
}
