package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.domain.Reagent;
import com.mycompany.myapp.repository.ReagentRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mycompany.myapp.service.UserService;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Reagent.
 */
@RestController
@RequestMapping("/api")
public class ReagentResource {

    private final Logger log = LoggerFactory.getLogger(ReagentResource.class);

    private static final String ENTITY_NAME = "reagent";

    private final ReagentRepository reagentRepository;

    private final UserService userService;

    public ReagentResource(ReagentRepository reagentRepository, UserService userService) {
        this.reagentRepository = reagentRepository;
        this.userService = userService;
    }

    /**
     * POST  /reagents : Create a new reagent.
     *
     * @param reagent the reagent to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reagent, or with status 400 (Bad Request) if the reagent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reagents")
    public ResponseEntity<Reagent> createReagent(@Valid @RequestBody Reagent reagent) throws URISyntaxException {
        log.debug("REST request to save Reagent : {}", reagent);
        if (reagent.getId() != null) {
            throw new BadRequestAlertException("A new reagent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        reagent.setUser(userService.getUserWithAuthorities().get());
        Reagent result = reagentRepository.save(reagent);
        return ResponseEntity.created(new URI("/api/reagents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reagents : Updates an existing reagent.
     *
     * @param reagent the reagent to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reagent,
     * or with status 400 (Bad Request) if the reagent is not valid,
     * or with status 500 (Internal Server Error) if the reagent couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reagents")
    public ResponseEntity<Reagent> updateReagent(@Valid @RequestBody Reagent reagent) throws URISyntaxException {
        log.debug("REST request to update Reagent : {}", reagent);
        if (reagent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Reagent result = reagentRepository.save(reagent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reagent.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reagents : get all the reagents.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of reagents in body
     */
    @GetMapping("/reagents")
    public List<Reagent> getAllReagents() {
        log.debug("REST request to get all Reagents");
        return reagentRepository.findAll();
    }

    /**
     * GET  /reagents/:id : get the "id" reagent.
     *
     * @param id the id of the reagent to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reagent, or with status 404 (Not Found)
     */
    @GetMapping("/reagents/{id}")
    public ResponseEntity<Reagent> getReagent(@PathVariable Long id) {
        log.debug("REST request to get Reagent : {}", id);
        Optional<Reagent> reagent = reagentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(reagent);
    }

    /**
     * DELETE  /reagents/:id : delete the "id" reagent.
     *
     * @param id the id of the reagent to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reagents/{id}")
    public ResponseEntity<Void> deleteReagent(@PathVariable Long id) {
        log.debug("REST request to delete Reagent : {}", id);
        reagentRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
