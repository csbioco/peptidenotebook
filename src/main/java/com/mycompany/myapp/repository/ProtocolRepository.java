package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Protocol;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Protocol entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProtocolRepository extends JpaRepository<Protocol, Long> {

    @Query("select protocol from Protocol protocol where protocol.user.login = ?#{principal.username}")
    List<Protocol> findByUserIsCurrentUser();

}
