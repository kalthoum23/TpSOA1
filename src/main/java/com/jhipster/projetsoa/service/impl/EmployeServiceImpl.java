package com.jhipster.projetsoa.service.impl;

import com.jhipster.projetsoa.service.EmployeService;
import com.jhipster.projetsoa.domain.Employe;
import com.jhipster.projetsoa.repository.EmployeRepository;
import com.jhipster.projetsoa.repository.search.EmployeSearchRepository;
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
 * Service Implementation for managing {@link Employe}.
 */
@Service
@Transactional
public class EmployeServiceImpl implements EmployeService {

    private final Logger log = LoggerFactory.getLogger(EmployeServiceImpl.class);

    private final EmployeRepository employeRepository;

    private final EmployeSearchRepository employeSearchRepository;

    public EmployeServiceImpl(EmployeRepository employeRepository, EmployeSearchRepository employeSearchRepository) {
        this.employeRepository = employeRepository;
        this.employeSearchRepository = employeSearchRepository;
    }

    @Override
    public Employe save(Employe employe) {
        log.debug("Request to save Employe : {}", employe);
        Employe result = employeRepository.save(employe);
        employeSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employe> findAll() {
        log.debug("Request to get all Employes");
        return employeRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Employe> findOne(Long id) {
        log.debug("Request to get Employe : {}", id);
        return employeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Employe : {}", id);
        employeRepository.deleteById(id);
        employeSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employe> search(String query) {
        log.debug("Request to search Employes for query {}", query);
        return StreamSupport
            .stream(employeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
