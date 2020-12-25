package com.jhipster.projetsoa.service.impl;

import com.jhipster.projetsoa.service.ProjetService;
import com.jhipster.projetsoa.domain.Projet;
import com.jhipster.projetsoa.repository.ProjetRepository;
import com.jhipster.projetsoa.repository.search.ProjetSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Projet}.
 */
@Service
@Transactional
public class ProjetServiceImpl implements ProjetService {

    private final Logger log = LoggerFactory.getLogger(ProjetServiceImpl.class);

    private final ProjetRepository projetRepository;

    private final ProjetSearchRepository projetSearchRepository;

    public ProjetServiceImpl(ProjetRepository projetRepository, ProjetSearchRepository projetSearchRepository) {
        this.projetRepository = projetRepository;
        this.projetSearchRepository = projetSearchRepository;
    }

    @Override
    public Projet save(Projet projet) {
        log.debug("Request to save Projet : {}", projet);
        Projet result = projetRepository.save(projet);
        projetSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Projet> findAll() {
        log.debug("Request to get all Projets");
        return projetRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Projet> findOne(Long id) {
        log.debug("Request to get Projet : {}", id);
        return projetRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Projet : {}", id);
        projetRepository.deleteById(id);
        projetSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Projet> search(String query) {
        log.debug("Request to search Projets for query {}", query);
        return StreamSupport
            .stream(projetSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
