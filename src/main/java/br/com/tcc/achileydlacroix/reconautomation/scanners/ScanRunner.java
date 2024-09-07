package br.com.tcc.achileydlacroix.reconautomation.scanners;

import br.com.tcc.achileydlacroix.reconautomation.domain.Domain;
import br.com.tcc.achileydlacroix.reconautomation.domain.DomainRepository;
import br.com.tcc.achileydlacroix.reconautomation.scanners.nuclei.NucleiScanner;
import br.com.tcc.achileydlacroix.reconautomation.scanners.subfinder.SubfinderScanner;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScanRunner {

    private SubfinderScanner subfinder;
    private NucleiScanner nuclei;
    private DomainRepository domainRepository;

    private final Logger log = LoggerFactory.getLogger(ScanRunner.class);

    @Autowired
    public ScanRunner(SubfinderScanner subfinder, NucleiScanner nuclei,DomainRepository domainRepository) {
        this.subfinder = subfinder;
        this.nuclei = nuclei;
        this.domainRepository = domainRepository;
    }
    @Transactional
    @Scheduled(fixedDelayString = "${schedule.scan.fixed.delay}")
    public void scheduleScan() {
        List<Domain> domains = domainRepository.findAll();

        log.info("Foram encontradas {} dominios", domains.size());

        for (Domain domain : domains) {
            subfinder.find(domain);
            nuclei.scan(domain);
        }
    }
    @Transactional
    @Async
    public void launchScan(Domain domain) {
        subfinder.find(domain);
        nuclei.scan(domain);

    }
}
