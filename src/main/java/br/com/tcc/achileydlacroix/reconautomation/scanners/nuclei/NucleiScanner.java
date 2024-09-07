package br.com.tcc.achileydlacroix.reconautomation.scanners.nuclei;

import br.com.tcc.achileydlacroix.reconautomation.domain.Domain;
import br.com.tcc.achileydlacroix.reconautomation.domain.subdomain.Subdomain;
import br.com.tcc.achileydlacroix.reconautomation.shared.TransactionalContext;
import br.com.tcc.achileydlacroix.reconautomation.vulnerability.Vulnerability;
import br.com.tcc.achileydlacroix.reconautomation.vulnerability.VulnerabilityRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class NucleiScanner {

    private VulnerabilityRepository repository;
    private TransactionalContext transactional;

    private final Logger log = LoggerFactory.getLogger(NucleiScanner.class);

    public NucleiScanner(VulnerabilityRepository repository, TransactionalContext transactional) {
        this.repository = repository;
        this.transactional = transactional;
    }
    @Transactional
    public void scan(Domain domain) {
        List<String> domainFindings = cmdExecute(domain.getAddress());

        if (domainFindings == null) throw new AssertionError();

        for (String domainFinding : domainFindings) {
            Optional<Vulnerability> possibleDomainVuln = repository.findByInfo(domainFinding);
            if(possibleDomainVuln.isEmpty()) {
                Vulnerability domainVuln = new Vulnerability(domainFinding.replaceAll("\\x1b\\[[;\\d]*m", ""));
                domainVuln.setDomain(domain);
                transactional.persist(domainVuln);
                domain.addVulnerabilities(domainVuln);
            }
        }

        transactional.update(domain);
        log.info("Foram encontrados {} vulnerabilidades para o dominio {}", domainFindings.size(), domain.getAddress());

        Set<Subdomain> subdomains = domain.getSubdomains();

        for (Subdomain subdomain: subdomains) {
            List<String> subdomainFindings = cmdExecute(subdomain.getAddress());

            if (subdomainFindings == null) throw new AssertionError();

            for (String subdomainFinding : subdomainFindings) {
                Optional<Vulnerability> possibleSubdomainVuln = repository.findByInfo(subdomainFinding);

                if(possibleSubdomainVuln.isEmpty()) {
                    System.out.print(subdomainFinding);
                    Vulnerability subdomainVuln = new Vulnerability(subdomainFinding);
                    subdomainVuln.setSubdomain(subdomain);
                    transactional.persist(subdomainVuln);
                    subdomain.addVulnerabilities(subdomainVuln);
                }
            }

            log.info("Foram encontrados {} vulnerabilidades para o dominio {}", subdomainFindings.size(), subdomain.getAddress());

            transactional.update(subdomain);
        }

    }

    private List<String> cmdExecute(String target){

        String command = "docker run --rm --name kali kali nuclei -u " +target+ " -s critical,high,medium,low -silent";

        try {

            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            process.waitFor();

            String[] linesResponse = output.toString().split("\n");
            return List.of(linesResponse);

        } catch (Exception e) {
            log.error("Não foi possivel buscar vulnerabilidades para o dominio {}. Motivo: {}", target, e.getMessage());
        }
        return null;
    }
}
