package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.domain.CalculatedEntry;
import com.mycompany.myapp.repository.CalculatedEntryRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import com.mycompany.myapp.service.CalculationAll;
import com.mycompany.myapp.service.classlib.Ret;

/**
 * REST controller for managing CalculatedEntry.
 */
@RestController
@RequestMapping("/api")
public class CalculatedEntryResource {

    private final Logger log = LoggerFactory.getLogger(CalculatedEntryResource.class);

    private static final String ENTITY_NAME = "calculatedEntry";

    private final CalculatedEntryRepository calculatedEntryRepository;
    private final CalculationAll calculationAll;

    public CalculatedEntryResource(CalculatedEntryRepository calculatedEntryRepository, CalculationAll calculationAll) {
        this.calculatedEntryRepository = calculatedEntryRepository;
        this.calculationAll = calculationAll;

    }

    /**
     * POST  /calculated-entries : Create a new calculatedEntry.
     *
     * @param calculatedEntry the calculatedEntry to create
     * @return the ResponseEntity with status 201 (Created) and with body the new calculatedEntry, or with status 400 (Bad Request) if the calculatedEntry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/calculated-entries")
    public ResponseEntity<CalculatedEntry> createCalculatedEntry(@Valid @RequestBody CalculatedEntry calculatedEntry) throws URISyntaxException {
        log.debug("REST request to save CalculatedEntry : {}", calculatedEntry);
        if (calculatedEntry.getId() != null) {
            throw new BadRequestAlertException("A new calculatedEntry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CalculatedEntry result = calculatedEntryRepository.save(calculatedEntry);
        return ResponseEntity.created(new URI("/api/calculated-entries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /calculated-entries : Updates an existing calculatedEntry.
     *
     * @param calculatedEntry the calculatedEntry to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated calculatedEntry,
     * or with status 400 (Bad Request) if the calculatedEntry is not valid,
     * or with status 500 (Internal Server Error) if the calculatedEntry couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/calculated-entries")
    public ResponseEntity<CalculatedEntry> updateCalculatedEntry(@Valid @RequestBody CalculatedEntry calculatedEntry) throws URISyntaxException {
        log.debug("REST request to update CalculatedEntry : {}", calculatedEntry);
        if (calculatedEntry.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CalculatedEntry result = calculatedEntryRepository.save(calculatedEntry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, calculatedEntry.getId().toString()))
            .body(result);
    }

    /**
     * GET  /calculated-entries : get all the calculatedEntries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of calculatedEntries in body
     */
    @GetMapping("/calculated-entries")
    public List<CalculatedEntry> getAllCalculatedEntries() {
        log.debug("REST request to get all CalculatedEntries");
        return calculatedEntryRepository.findAll();
    }

    /**
     * GET  /calculated-entries/:id : get the "id" calculatedEntry.
     *
     * @param id the id of the calculatedEntry to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the calculatedEntry, or with status 404 (Not Found)
     */
    @GetMapping("/calculated-entries/{id}")
    public ResponseEntity<CalculatedEntry> getCalculatedEntry(@PathVariable Long id) {
        log.debug("REST request to get CalculatedEntry : {}", id);
        Optional<CalculatedEntry> calculatedEntry = calculatedEntryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(calculatedEntry);
    }

    /**
     * DELETE  /calculated-entries/:id : delete the "id" calculatedEntry.
     *
     * @param id the id of the calculatedEntry to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/calculated-entries/{id}")
    public ResponseEntity<Void> deleteCalculatedEntry(@PathVariable Long id) {
        log.debug("REST request to delete CalculatedEntry : {}", id);
        calculatedEntryRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/calculated-entriesbycal/{id}")
    public Ret getAllCalculatedEntriesbycalId(@PathVariable Long id) {
        log.debug("REST request to get all CalculatedEntries by calculatede id");
        Ret return_res = calculationAll.returncal(id);
        return return_res;
    }
}
