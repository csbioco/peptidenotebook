package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Reagent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Reagent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReagentRepository extends JpaRepository<Reagent, Long> {

    @Query("select reagent from Reagent reagent where reagent.user.login = ?#{principal.username}")
    List<Reagent> findByUserIsCurrentUser();

}
