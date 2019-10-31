package com.shaffiro.repository;

import com.shaffiro.domain.Regla;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Regla entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReglaRepository extends JpaRepository<Regla, Long>, JpaSpecificationExecutor<Regla> {

}
