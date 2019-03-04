package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.domain.Calculated;
import com.mycompany.myapp.repository.CalculatedRepository;
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
import com.mycompany.myapp.service.UserService;
/**
 * REST controller for managing Calculated.
 */
@RestController
@RequestMapping("/api")
public class CalculatedResource {

    private final Logger log = LoggerFactory.getLogger(CalculatedResource.class);

    private static final String ENTITY_NAME = "calculated";

    private final CalculatedRepository calculatedRepository;
    private final CalculatedEntryRepository calculatedEntryRepository;
    private final CalculationAll calculationAll;
    private final UserService userService;
    public CalculatedResource(CalculatedRepository calculatedRepository, CalculationAll calculationAll, UserService userService, CalculatedEntryRepository calculatedEntryRepository) {
        this.calculatedRepository = calculatedRepository;
        this.calculationAll = calculationAll;
        this.userService = userService;
        this.calculatedEntryRepository = calculatedEntryRepository;
    }

    /**
     * POST  /calculateds : Create a new calculated.
     *
     * @param calculated the calculated to create
     * @return the ResponseEntity with status 201 (Created) and with body the new calculated, or with status 400 (Bad Request) if the calculated has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/calculateds")
    public ResponseEntity<Calculated> createCalculated(@Valid @RequestBody Calculated calculated) throws URISyntaxException {
        log.debug("REST request to save Calculated : {}", calculated);
        if (calculated.getId() != null) {
            throw new BadRequestAlertException("A new calculated cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (!calculationAll.checksequence(calculated)) {
            throw new BadRequestAlertException("Please check your input sequence", ENTITY_NAME, "sequence error");
        }
        calculated.setUser(userService.getUserWithAuthorities().get());
        Calculated result = calculatedRepository.save(calculated);
        calculationAll.createCalculateEntry(result);
        calculationAll.calculateresult(result.getId());
        return ResponseEntity.created(new URI("/api/calculateds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /calculateds : Updates an existing calculated.
     *
     * @param calculated the calculated to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated calculated,
     * or with status 400 (Bad Request) if the calculated is not valid,
     * or with status 500 (Internal Server Error) if the calculated couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/calculateds")
    public ResponseEntity<Calculated> updateCalculated(@Valid @RequestBody Calculated calculated) throws URISyntaxException {
        log.debug("REST request to update Calculated : {}", calculated);
        if (calculated.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Calculated result = calculatedRepository.save(calculated);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, calculated.getId().toString()))
            .body(result);
    }

    /**
     * GET  /calculateds : get all the calculateds.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of calculateds in body
     */
    @GetMapping("/calculateds")
    public List<Calculated> getAllCalculateds() {
        log.debug("REST request to get all Calculateds");
        // userService.getUserWithAuthorities().get()
        return calculatedRepository.findByUserIsCurrentUser();
    }

    /**
     * GET  /calculateds/:id : get the "id" calculated.
     *
     * @param id the id of the calculated to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the calculated, or with status 404 (Not Found)
     */
    @GetMapping("/calculateds/{id}")
    public ResponseEntity<Calculated> getCalculated(@PathVariable Long id) {
        log.debug("REST request to get Calculated : {}", id);
        Optional<Calculated> calculated = calculatedRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(calculated);
    }

    /**
     * DELETE  /calculateds/:id : delete the "id" calculated.
     *
     * @param id the id of the calculated to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/calculateds/{id}")
    public ResponseEntity<Void> deleteCalculated(@PathVariable Long id) {
        log.debug("REST request to delete Calculated : {}", id);
        calculatedEntryRepository.deletebycalculatedid(id);
        calculatedRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
