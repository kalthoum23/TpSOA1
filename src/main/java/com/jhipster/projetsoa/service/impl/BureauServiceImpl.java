package com.jhipster.projetsoa.service.impl;

import com.jhipster.projetsoa.service.BureauService;
import com.jhipster.projetsoa.domain.Bureau;
import com.jhipster.projetsoa.repository.BureauRepository;
import com.jhipster.projetsoa.repository.search.BureauSearchRepository;
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
 * Service Implementation for managing {@link Bureau}.
 */
@Service
@Transactional
public class BureauServiceImpl implements BureauService {

    private final Logger log = LoggerFactory.getLogger(BureauServiceImpl.class);

    private final BureauRepository bureauRepository;

    private final BureauSearchRepository bureauSearchRepository;

    public BureauServiceImpl(BureauRepository bureauRepository, BureauSearchRepository bureauSearchRepository) {
        this.bureauRepository = bureauRepository;
        this.bureauSearchRepository = bureauSearchRepository;
    }

    @Override
    public Bureau save(Bureau bureau) {
        log.debug("Request to save Bureau : {}", bureau);
        Bureau result = bureauRepository.save(bureau);
        bureauSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bureau> findAll() {
        log.debug("Request to get all Bureaus");
        return bureauRepository.findAll();
    }



    /**
     *  Get all the bureaus where Employe is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<Bureau> findAllWhereEmployeIsNull() {
        log.debug("Request to get all bureaus where Employe is null");
        return StreamSupport
            .stream(bureauRepository.findAll().spliterator(), false)
            .filter(bureau -> bureau.getEmploye() == null)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Bureau> findOne(Long id) {
        log.debug("Request to get Bureau : {}", id);
        return bureauRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Bureau : {}", id);
        bureauRepository.deleteById(id);
        bureauSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bureau> search(String query) {
        log.debug("Request to search Bureaus for query {}", query);
        return StreamSupport
            .stream(bureauSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
