package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CalculatedEntry;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring Data  repository for the CalculatedEntry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CalculatedEntryRepository extends JpaRepository<CalculatedEntry, Long> {
    @Query("select calculatedEntry from CalculatedEntry calculatedEntry where calculatedEntry.calculated.id = :id")
    List<CalculatedEntry> findByCalculateId(@Param("id") Long id);



    @Modifying
    @Transactional
    @Query("delete from CalculatedEntry calculatedEntry where calculatedEntry.calculated.id = :id")
    void deletebycalculatedid(@Param("id") Long id);
}
