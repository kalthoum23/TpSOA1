package com.jhipster.projetsoa.web.rest;

import com.jhipster.projetsoa.ProjetSoaApp;
import com.jhipster.projetsoa.domain.Bureau;
import com.jhipster.projetsoa.repository.BureauRepository;
import com.jhipster.projetsoa.repository.search.BureauSearchRepository;
import com.jhipster.projetsoa.service.BureauService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BureauResource} REST controller.
 */
@SpringBootTest(classes = ProjetSoaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class BureauResourceIT {

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    @Autowired
    private BureauRepository bureauRepository;

    @Autowired
    private BureauService bureauService;

    /**
     * This repository is mocked in the com.jhipster.projetsoa.repository.search test package.
     *
     * @see com.jhipster.projetsoa.repository.search.BureauSearchRepositoryMockConfiguration
     */
    @Autowired
    private BureauSearchRepository mockBureauSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBureauMockMvc;

    private Bureau bureau;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bureau createEntity(EntityManager em) {
        Bureau bureau = new Bureau()
            .numero(DEFAULT_NUMERO);
        return bureau;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bureau createUpdatedEntity(EntityManager em) {
        Bureau bureau = new Bureau()
            .numero(UPDATED_NUMERO);
        return bureau;
    }

    @BeforeEach
    public void initTest() {
        bureau = createEntity(em);
    }

    @Test
    @Transactional
    public void createBureau() throws Exception {
        int databaseSizeBeforeCreate = bureauRepository.findAll().size();
        // Create the Bureau
        restBureauMockMvc.perform(post("/api/bureaus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bureau)))
            .andExpect(status().isCreated());

        // Validate the Bureau in the database
        List<Bureau> bureauList = bureauRepository.findAll();
        assertThat(bureauList).hasSize(databaseSizeBeforeCreate + 1);
        Bureau testBureau = bureauList.get(bureauList.size() - 1);
        assertThat(testBureau.getNumero()).isEqualTo(DEFAULT_NUMERO);

        // Validate the Bureau in Elasticsearch
        verify(mockBureauSearchRepository, times(1)).save(testBureau);
    }

    @Test
    @Transactional
    public void createBureauWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bureauRepository.findAll().size();

        // Create the Bureau with an existing ID
        bureau.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBureauMockMvc.perform(post("/api/bureaus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bureau)))
            .andExpect(status().isBadRequest());

        // Validate the Bureau in the database
        List<Bureau> bureauList = bureauRepository.findAll();
        assertThat(bureauList).hasSize(databaseSizeBeforeCreate);

        // Validate the Bureau in Elasticsearch
        verify(mockBureauSearchRepository, times(0)).save(bureau);
    }


    @Test
    @Transactional
    public void getAllBureaus() throws Exception {
        // Initialize the database
        bureauRepository.saveAndFlush(bureau);

        // Get all the bureauList
        restBureauMockMvc.perform(get("/api/bureaus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bureau.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)));
    }
    
    @Test
    @Transactional
    public void getBureau() throws Exception {
        // Initialize the database
        bureauRepository.saveAndFlush(bureau);

        // Get the bureau
        restBureauMockMvc.perform(get("/api/bureaus/{id}", bureau.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bureau.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO));
    }
    @Test
    @Transactional
    public void getNonExistingBureau() throws Exception {
        // Get the bureau
        restBureauMockMvc.perform(get("/api/bureaus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBureau() throws Exception {
        // Initialize the database
        bureauService.save(bureau);

        int databaseSizeBeforeUpdate = bureauRepository.findAll().size();

        // Update the bureau
        Bureau updatedBureau = bureauRepository.findById(bureau.getId()).get();
        // Disconnect from session so that the updates on updatedBureau are not directly saved in db
        em.detach(updatedBureau);
        updatedBureau
            .numero(UPDATED_NUMERO);

        restBureauMockMvc.perform(put("/api/bureaus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBureau)))
            .andExpect(status().isOk());

        // Validate the Bureau in the database
        List<Bureau> bureauList = bureauRepository.findAll();
        assertThat(bureauList).hasSize(databaseSizeBeforeUpdate);
        Bureau testBureau = bureauList.get(bureauList.size() - 1);
        assertThat(testBureau.getNumero()).isEqualTo(UPDATED_NUMERO);

        // Validate the Bureau in Elasticsearch
        verify(mockBureauSearchRepository, times(2)).save(testBureau);
    }

    @Test
    @Transactional
    public void updateNonExistingBureau() throws Exception {
        int databaseSizeBeforeUpdate = bureauRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBureauMockMvc.perform(put("/api/bureaus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bureau)))
            .andExpect(status().isBadRequest());

        // Validate the Bureau in the database
        List<Bureau> bureauList = bureauRepository.findAll();
        assertThat(bureauList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Bureau in Elasticsearch
        verify(mockBureauSearchRepository, times(0)).save(bureau);
    }

    @Test
    @Transactional
    public void deleteBureau() throws Exception {
        // Initialize the database
        bureauService.save(bureau);

        int databaseSizeBeforeDelete = bureauRepository.findAll().size();

        // Delete the bureau
        restBureauMockMvc.perform(delete("/api/bureaus/{id}", bureau.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bureau> bureauList = bureauRepository.findAll();
        assertThat(bureauList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Bureau in Elasticsearch
        verify(mockBureauSearchRepository, times(1)).deleteById(bureau.getId());
    }

    @Test
    @Transactional
    public void searchBureau() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        bureauService.save(bureau);
        when(mockBureauSearchRepository.search(queryStringQuery("id:" + bureau.getId())))
            .thenReturn(Collections.singletonList(bureau));

        // Search the bureau
        restBureauMockMvc.perform(get("/api/_search/bureaus?query=id:" + bureau.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bureau.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)));
    }
}
