package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.domain.Protocol;
import com.mycompany.myapp.repository.ProtocolRepository;
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
import com.mycompany.myapp.service.CalculationAll;

/**
 * REST controller for managing Protocol.
 */
@RestController
@RequestMapping("/api")
public class ProtocolResource {

    private final Logger log = LoggerFactory.getLogger(ProtocolResource.class);

    private static final String ENTITY_NAME = "protocol";

    private final ProtocolRepository protocolRepository;
    private final UserService userService;
    private final CalculationAll calculationAll;

    public ProtocolResource(ProtocolRepository protocolRepository, UserService userService, CalculationAll calculationAll) {
        this.protocolRepository = protocolRepository;
        this.userService = userService;
        this.calculationAll = calculationAll;
    }

    /**
     * POST  /protocols : Create a new protocol.
     *
     * @param protocol the protocol to create
     * @return the ResponseEntity with status 201 (Created) and with body the new protocol, or with status 400 (Bad Request) if the protocol has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/protocols")
    public ResponseEntity<Protocol> createProtocol(@Valid @RequestBody Protocol protocol) throws URISyntaxException {
        log.debug("REST request to save Protocol : {}", protocol);
        if (protocol.getId() != null) {
            throw new BadRequestAlertException("A new protocol cannot already have an ID", ENTITY_NAME, "idexists");
        }
        protocol.setUser(userService.getUserWithAuthorities().get());
        Protocol result = protocolRepository.save(protocol);
        return ResponseEntity.created(new URI("/api/protocols/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /protocols : Updates an existing protocol.
     *
     * @param protocol the protocol to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated protocol,
     * or with status 400 (Bad Request) if the protocol is not valid,
     * or with status 500 (Internal Server Error) if the protocol couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/protocols")
    public ResponseEntity<Protocol> updateProtocol(@Valid @RequestBody Protocol protocol) throws URISyntaxException {
        log.debug("REST request to update Protocol : {}", protocol);
        if (protocol.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Protocol result = protocolRepository.save(protocol);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, protocol.getId().toString()))
            .body(result);
    }

    /**
     * GET  /protocols : get all the protocols.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of protocols in body
     */
    @GetMapping("/protocols")
    public List<Protocol> getAllProtocols() {
        log.debug("REST request to get current users' all Protocols");
        return protocolRepository.findByUserIsCurrentUser();
    }

    /**
     * GET  /protocols/:id : get the "id" protocol.
     *
     * @param id the id of the protocol to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the protocol, or with status 404 (Not Found)
     */
    @GetMapping("/protocols/{id}")
    public ResponseEntity<Protocol> getProtocol(@PathVariable Long id) {
        log.debug("REST request to get Protocol : {}", id);
        Optional<Protocol> protocol = protocolRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(protocol);
    }

    /**
     * DELETE  /protocols/:id : delete the "id" protocol.
     *
     * @param id the id of the protocol to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/protocols/{id}")
    public ResponseEntity<Void> deleteProtocol(@PathVariable Long id) {
        log.debug("REST request to delete Protocol : {}", id);
        calculationAll.deleteallreagentsensorprotocol(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
