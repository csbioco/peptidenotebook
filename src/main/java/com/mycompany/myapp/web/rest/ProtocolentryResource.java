package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.domain.Protocolentry;
import com.mycompany.myapp.repository.ProtocolentryRepository;
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

/**
 * REST controller for managing Protocolentry.
 */
@RestController
@RequestMapping("/api")
public class ProtocolentryResource {

    private final Logger log = LoggerFactory.getLogger(ProtocolentryResource.class);

    private static final String ENTITY_NAME = "protocolentry";

    private final ProtocolentryRepository protocolentryRepository;

    public ProtocolentryResource(ProtocolentryRepository protocolentryRepository) {
        this.protocolentryRepository = protocolentryRepository;
    }

    /**
     * POST  /protocolentries : Create a new protocolentry.
     *
     * @param protocolentry the protocolentry to create
     * @return the ResponseEntity with status 201 (Created) and with body the new protocolentry, or with status 400 (Bad Request) if the protocolentry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/protocolentries")
    public ResponseEntity<Protocolentry> createProtocolentry(@Valid @RequestBody Protocolentry protocolentry) throws URISyntaxException {
        log.debug("REST request to save Protocolentry : {}", protocolentry);
        if (protocolentry.getId() != null) {
            throw new BadRequestAlertException("A new protocolentry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Protocolentry result = protocolentryRepository.save(protocolentry);
        return ResponseEntity.created(new URI("/api/protocolentries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /protocolentries : Updates an existing protocolentry.
     *
     * @param protocolentry the protocolentry to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated protocolentry,
     * or with status 400 (Bad Request) if the protocolentry is not valid,
     * or with status 500 (Internal Server Error) if the protocolentry couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/protocolentries")
    public ResponseEntity<Protocolentry> updateProtocolentry(@Valid @RequestBody Protocolentry protocolentry) throws URISyntaxException {
        log.debug("REST request to update Protocolentry : {}", protocolentry);
        if (protocolentry.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Protocolentry result = protocolentryRepository.save(protocolentry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, protocolentry.getId().toString()))
            .body(result);
    }

    /**
     * GET  /protocolentries : get all the protocolentries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of protocolentries in body
     */
    @GetMapping("/protocolentries")
    public List<Protocolentry> getAllProtocolentries() {
        log.debug("REST request to get all Protocolentries");
        return protocolentryRepository.findAll();
    }

    /**
     * GET  /protocolentries/:id : get the "id" protocolentry.
     *
     * @param id the id of the protocolentry to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the protocolentry, or with status 404 (Not Found)
     */
    @GetMapping("/protocolentries/{id}")
    public ResponseEntity<Protocolentry> getProtocolentry(@PathVariable Long id) {
        log.debug("REST request to get Protocolentry : {}", id);
        Optional<Protocolentry> protocolentry = protocolentryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(protocolentry);
    }



    /**
     * GET  /protocolentriescal/:id : get the "id" protocolentry.
     *
     * @param id the id of the protocolentry to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the protocolentry, or with status 404 (Not Found)
     */
    @GetMapping("/protocolentriescal/{id}")
    public List<Protocolentry> getProtocolentrycal(@PathVariable Long id) {
        log.debug("REST request to get Protocolentry by protocol id: {}", id);
        List<Protocolentry> protocolentry = protocolentryRepository.findByProtocolId(id);
        return protocolentry;
    }

    /**
     * DELETE  /protocolentries/:id : delete the "id" protocolentry.
     *
     * @param id the id of the protocolentry to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/protocolentries/{id}")
    public ResponseEntity<Void> deleteProtocolentry(@PathVariable Long id) {
        log.debug("REST request to delete Protocolentry : {}", id);
        protocolentryRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
