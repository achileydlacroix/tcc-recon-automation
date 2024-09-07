package br.com.tcc.achileydlacroix.reconautomation.domain;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import br.com.tcc.achileydlacroix.reconautomation.scanners.ScanRunner;
import br.com.tcc.achileydlacroix.reconautomation.shared.TransactionalContext;
import br.com.tcc.achileydlacroix.reconautomation.scanners.subfinder.SubfinderScanner;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/domains")
public class DomainController {

    private DomainRepository repository;
    private TransactionalContext transactional;
    private ScanRunner scanRunner;

    private final Logger logger = LoggerFactory.getLogger(DomainController.class);

    @Autowired
    public DomainController(DomainRepository repository, TransactionalContext transactional, ScanRunner scanRunner) {
        this.repository = repository;
        this.transactional = transactional;
        this.scanRunner = scanRunner;
    }

    @PostMapping(value="/new-domain")
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Valid NewDomainRequest request, UriComponentsBuilder uriBuilder) {

        Optional<Domain> possibleDomain = repository.findByAddress(request.getAddress());

        if(!possibleDomain.isEmpty()) {
            logger.info("Domínio {} duplicado!", request.getAddress());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Domínio já cadastrado");
        }

        Domain domain = new Domain(request.getAddress());

        transactional.persist(domain);
        scanRunner.launchScan(domain);

        logger.info("Domain {} adicionado com status: {}.", domain.getId(), HttpStatus.CREATED);

        URI uri = uriBuilder.path("/domain/{id}").build(domain.getId());

        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public List<DomainResponse> list()  {
        List<Domain> domains = repository.findAll();
        return DomainResponse.convert(domains);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {

        Optional<Domain> domainToBeDeleted = repository.findById(id);

        if(domainToBeDeleted.isPresent()) {
            transactional.delete(repository.findById(id).get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        logger.info("Domínio {} não encontrado!", id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Domínio não encontrado");

    }

}
