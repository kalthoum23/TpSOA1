package com.jhipster.projetsoa.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link CompetenceSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CompetenceSearchRepositoryMockConfiguration {

    @MockBean
    private CompetenceSearchRepository mockCompetenceSearchRepository;

}
