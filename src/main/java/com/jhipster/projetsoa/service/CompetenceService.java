package com.jhipster.projetsoa.service;

import com.jhipster.projetsoa.domain.Competence;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Competence}.
 */
public interface CompetenceService {

    /**
     * Save a competence.
     *
     * @param competence the entity to save.
     * @return the persisted entity.
     */
    Competence save(Competence competence);

    /**
     * Get all the competences.
     *
     * @return the list of entities.
     */
    List<Competence> findAll();


    /**
     * Get the "id" competence.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Competence> findOne(Long id);

    /**
     * Delete the "id" competence.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the competence corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<Competence> search(String query);
}
