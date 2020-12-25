package com.jhipster.projetsoa.repository.search;

import com.jhipster.projetsoa.domain.Bureau;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Bureau} entity.
 */
public interface BureauSearchRepository extends ElasticsearchRepository<Bureau, Long> {
}
