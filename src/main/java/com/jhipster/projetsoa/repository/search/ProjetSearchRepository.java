package com.jhipster.projetsoa.repository.search;

import com.jhipster.projetsoa.domain.Projet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Projet} entity.
 */
public interface ProjetSearchRepository extends ElasticsearchRepository<Projet, Long> {
}
