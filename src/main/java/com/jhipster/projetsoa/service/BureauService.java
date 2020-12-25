package com.jhipster.projetsoa.service;

import com.jhipster.projetsoa.domain.Bureau;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Bureau}.
 */
public interface BureauService {

    /**
     * Save a bureau.
     *
     * @param bureau the entity to save.
     * @return the persisted entity.
     */
    Bureau save(Bureau bureau);

    /**
     * Get all the bureaus.
     *
     * @return the list of entities.
     */
    List<Bureau> findAll();
    /**
     * Get all the BureauDTO where Employe is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Bureau> findAllWhereEmployeIsNull();


    /**
     * Get the "id" bureau.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Bureau> findOne(Long id);

    /**
     * Delete the "id" bureau.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the bureau corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<Bureau> search(String query);
}
