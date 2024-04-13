package br.com.tcc.achileydlacroix.reconautomation.domain;

import java.net.URI;
import java.util.Optional;

import br.com.tcc.achileydlacroix.reconautomation.shared.TransactionalContext;
import br.com.tcc.achileydlacroix.reconautomation.tools.subfinder.SubfinderExecutor;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
public class DomainController {

    private DomainRepository repository;
    private TransactionalContext transactional;
    private SubfinderExecutor subfinderExecutor;

    private final Logger logger = LoggerFactory.getLogger(DomainController.class);

    @Autowired
    public DomainController(DomainRepository repository, TransactionalContext transacional) {
        this.repository = repository;
        this.transactional = transacional;
    }

    @PostMapping(value="/new-domain")
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Valid NewDomainRequest request, UriComponentsBuilder uriBuilder) {

        Optional<Domain> newDomain = repository.findByUrl(request.getUrl());

        if(!newDomain.isEmpty()) {
            logger.info("Domínio {} duplicado!", request.getUrl());
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Este domínio ja se encontra cadastrado no sistema");
        }

        Domain domain = new Domain(request.getUrl());
        transactional.persist(domain);

        domain.setSubdomains(subfinderExecutor.executeSubfinder(domain.getUrl()));
        transactional.update(domain);

        logger.info("Domain {} adicionado com status: {}.", domain.getId());

        URI uri = uriBuilder.path("/domain/{id}").build(domain.getId());

        return ResponseEntity.created(uri).build();
    }

}
