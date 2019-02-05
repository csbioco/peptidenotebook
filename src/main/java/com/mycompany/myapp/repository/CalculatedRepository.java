package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Calculated;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data  repository for the Calculated entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CalculatedRepository extends JpaRepository<Calculated, Long> {

    @Query("select calculated from Calculated calculated where calculated.user.login = ?#{principal.username}")
    List<Calculated> findByUserIsCurrentUser();
    @Query("select calculated from Calculated calculated where calculated.id = :id")
    Calculated findByCalculateId(@Param("id") Long id);
}