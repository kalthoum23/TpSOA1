package com.jhipster.projetsoa.service.impl;

import com.jhipster.projetsoa.service.CompetenceService;
import com.jhipster.projetsoa.domain.Competence;
import com.jhipster.projetsoa.repository.CompetenceRepository;
import com.jhipster.projetsoa.repository.search.CompetenceSearchRepository;
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
 * Service Implementation for managing {@link Competence}.
 */
@Service
@Transactional
public class CompetenceServiceImpl implements CompetenceService {

    private final Logger log = LoggerFactory.getLogger(CompetenceServiceImpl.class);

    private final CompetenceRepository competenceRepository;

    private final CompetenceSearchRepository competenceSearchRepository;

    public CompetenceServiceImpl(CompetenceRepository competenceRepository, CompetenceSearchRepository competenceSearchRepository) {
        this.competenceRepository = competenceRepository;
        this.competenceSearchRepository = competenceSearchRepository;
    }

    @Override
    public Competence save(Competence competence) {
        log.debug("Request to save Competence : {}", competence);
        Competence result = competenceRepository.save(competence);
        competenceSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Competence> findAll() {
        log.debug("Request to get all Competences");
        return competenceRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Competence> findOne(Long id) {
        log.debug("Request to get Competence : {}", id);
        return competenceRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Competence : {}", id);
        competenceRepository.deleteById(id);
        competenceSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Competence> search(String query) {
        log.debug("Request to search Competences for query {}", query);
        return StreamSupport
            .stream(competenceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
