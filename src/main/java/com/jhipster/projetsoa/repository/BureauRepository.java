package com.jhipster.projetsoa.repository;

import com.jhipster.projetsoa.domain.Bureau;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Bureau entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BureauRepository extends JpaRepository<Bureau, Long> {
}
