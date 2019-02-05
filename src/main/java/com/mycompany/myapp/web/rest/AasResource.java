package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.domain.Aas;
import com.mycompany.myapp.repository.AasRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mycompany.myapp.service.Create22aa;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Aas.
 */
@RestController
@RequestMapping("/api")
public class AasResource {

    private final Logger log = LoggerFactory.getLogger(AasResource.class);

    private static final String ENTITY_NAME = "aas";

    private final AasRepository aasRepository;
    private final Create22aa create22aa;
    public AasResource(AasRepository aasRepository, Create22aa create22aa) {
        this.aasRepository = aasRepository;
        this.create22aa = create22aa;
    }

    /**
     * POST  /aas : Create a new aas.
     *
     * @param aas the aas to create
     * @return the ResponseEntity with status 201 (Created) and with body the new aas, or with status 400 (Bad Request) if the aas has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/aas")
    public ResponseEntity<Aas> createAas(@Valid @RequestBody Aas aas) throws URISyntaxException {
        log.debug("REST request to save Aas : {}", aas);
        if (aas.getId() != null) {
            throw new BadRequestAlertException("A new aas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Aas result = aasRepository.save(aas);
        return ResponseEntity.created(new URI("/api/aas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /aas : Updates an existing aas.
     *
     * @param aas the aas to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated aas,
     * or with status 400 (Bad Request) if the aas is not valid,
     * or with status 500 (Internal Server Error) if the aas couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/aas")
    public ResponseEntity<Aas> updateAas(@Valid @RequestBody Aas aas) throws URISyntaxException {
        log.debug("REST request to update Aas : {}", aas);
        if (aas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Aas result = aasRepository.save(aas);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, aas.getId().toString()))
            .body(result);
    }

    /**
     * GET  /aas : get all the aas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of aas in body
     */
    @GetMapping("/aas")
    public List<Aas> getAllAas() {
        log.debug("REST request to get all Aas");
        return aasRepository.findAll();
    }

    /**
     * GET  /aas/:id : get the "id" aas.
     *
     * @param id the id of the aas to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the aas, or with status 404 (Not Found)
     */
    @GetMapping("/aas/{id}")
    public ResponseEntity<Aas> getAas(@PathVariable Long id) {
        log.debug("REST request to get Aas : {}", id);
        Optional<Aas> aas = aasRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(aas);
    }

    /**
     * DELETE  /aas/:id : delete the "id" aas.
     *
     * @param id the id of the aas to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/aas/{id}")
    public ResponseEntity<Void> deleteAas(@PathVariable Long id) {
        log.debug("REST request to delete Aas : {}", id);
        aasRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

     /**
     * DELETE  /aasall : delete all aas.
     *
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/aasall")
    public ResponseEntity<Void> deleteallAas() {
        log.debug("REST request to delete all Aas : {}");
        List<Aas> res = aasRepository.findAll();
        for (Aas tmp : res) {
            aasRepository.delete(tmp);
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, "CC")).build();
    }


    @GetMapping("/addaas")
    public ResponseEntity<Void> addAllAas() {
        create22aa.createCalculateEntry();
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, "added 20 aas")).build();
    }
}
