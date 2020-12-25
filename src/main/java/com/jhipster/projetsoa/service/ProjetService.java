package com.jhipster.projetsoa.service;

import com.jhipster.projetsoa.domain.Projet;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Projet}.
 */
public interface ProjetService {

    /**
     * Save a projet.
     *
     * @param projet the entity to save.
     * @return the persisted entity.
     */
    Projet save(Projet projet);

    /**
     * Get all the projets.
     *
     * @return the list of entities.
     */
    List<Projet> findAll();


    /**
     * Get the "id" projet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Projet> findOne(Long id);

    /**
     * Delete the "id" projet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the projet corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<Projet> search(String query);
}
