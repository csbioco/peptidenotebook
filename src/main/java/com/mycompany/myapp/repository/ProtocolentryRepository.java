package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Protocolentry;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring Data  repository for the Protocolentry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProtocolentryRepository extends JpaRepository<Protocolentry, Long> {
    @Query("select protocolentry from Protocolentry protocolentry where protocolentry.protocol.id = :id")
    List<Protocolentry> findByProtocolId(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("delete from Protocolentry protocolentry where protocolentry.protocol.id = :id")
    void deleteByProtocolId(@Param("id") Long id);
}
