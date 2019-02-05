package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Aas;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Aas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AasRepository extends JpaRepository<Aas, Long> {

}
