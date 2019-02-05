package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Protocolentry;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Protocolentry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProtocolentryRepository extends JpaRepository<Protocolentry, Long> {

}
