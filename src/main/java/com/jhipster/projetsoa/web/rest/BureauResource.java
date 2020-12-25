package com.jhipster.projetsoa.web.rest;

import com.jhipster.projetsoa.domain.Bureau;
import com.jhipster.projetsoa.service.BureauService;
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
 * REST controller for managing {@link com.jhipster.projetsoa.domain.Bureau}.
 */
@RestController
@RequestMapping("/api")
public class BureauResource {

    private final Logger log = LoggerFactory.getLogger(BureauResource.class);

    private static final String ENTITY_NAME = "bureau";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BureauService bureauService;

    public BureauResource(BureauService bureauService) {
        this.bureauService = bureauService;
    }

    /**
     * {@code POST  /bureaus} : Create a new bureau.
     *
     * @param bureau the bureau to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bureau, or with status {@code 400 (Bad Request)} if the bureau has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bureaus")
    public ResponseEntity<Bureau> createBureau(@RequestBody Bureau bureau) throws URISyntaxException {
        log.debug("REST request to save Bureau : {}", bureau);
        if (bureau.getId() != null) {
            throw new BadRequestAlertException("A new bureau cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bureau result = bureauService.save(bureau);
        return ResponseEntity.created(new URI("/api/bureaus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bureaus} : Updates an existing bureau.
     *
     * @param bureau the bureau to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bureau,
     * or with status {@code 400 (Bad Request)} if the bureau is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bureau couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bureaus")
    public ResponseEntity<Bureau> updateBureau(@RequestBody Bureau bureau) throws URISyntaxException {
        log.debug("REST request to update Bureau : {}", bureau);
        if (bureau.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Bureau result = bureauService.save(bureau);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bureau.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bureaus} : get all the bureaus.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bureaus in body.
     */
    @GetMapping("/bureaus")
    public List<Bureau> getAllBureaus(@RequestParam(required = false) String filter) {
        if ("employe-is-null".equals(filter)) {
            log.debug("REST request to get all Bureaus where employe is null");
            return bureauService.findAllWhereEmployeIsNull();
        }
        log.debug("REST request to get all Bureaus");
        return bureauService.findAll();
    }

    /**
     * {@code GET  /bureaus/:id} : get the "id" bureau.
     *
     * @param id the id of the bureau to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bureau, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bureaus/{id}")
    public ResponseEntity<Bureau> getBureau(@PathVariable Long id) {
        log.debug("REST request to get Bureau : {}", id);
        Optional<Bureau> bureau = bureauService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bureau);
    }

    /**
     * {@code DELETE  /bureaus/:id} : delete the "id" bureau.
     *
     * @param id the id of the bureau to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bureaus/{id}")
    public ResponseEntity<Void> deleteBureau(@PathVariable Long id) {
        log.debug("REST request to delete Bureau : {}", id);
        bureauService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/bureaus?query=:query} : search for the bureau corresponding
     * to the query.
     *
     * @param query the query of the bureau search.
     * @return the result of the search.
     */
    @GetMapping("/_search/bureaus")
    public List<Bureau> searchBureaus(@RequestParam String query) {
        log.debug("REST request to search Bureaus for query {}", query);
        return bureauService.search(query);
    }
}
