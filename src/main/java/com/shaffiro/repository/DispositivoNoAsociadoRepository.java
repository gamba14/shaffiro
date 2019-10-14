package com.shaffiro.repository;

import com.shaffiro.domain.DispositivoNoAsociado;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DispositivoNoAsociado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DispositivoNoAsociadoRepository extends JpaRepository<DispositivoNoAsociado, Long> {

}
