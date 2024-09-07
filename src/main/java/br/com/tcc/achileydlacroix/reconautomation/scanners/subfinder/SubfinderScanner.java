package br.com.tcc.achileydlacroix.reconautomation.scanners.subfinder;

import br.com.tcc.achileydlacroix.reconautomation.domain.Domain;
import br.com.tcc.achileydlacroix.reconautomation.domain.DomainRepository;
import br.com.tcc.achileydlacroix.reconautomation.domain.subdomain.Subdomain;
import br.com.tcc.achileydlacroix.reconautomation.domain.subdomain.SubdomainRepository;
import br.com.tcc.achileydlacroix.reconautomation.shared.TransactionalContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubfinderScanner {

    private SubdomainRepository subdomainRepository;
    private TransactionalContext transactional;

    private final Logger log = LoggerFactory.getLogger(SubfinderScanner.class);

    @Autowired
    public SubfinderScanner(SubdomainRepository subdomainRepository, TransactionalContext transactional) {
        this.subdomainRepository = subdomainRepository;
        this.transactional = transactional;
    }

    @Transactional
    public void find(Domain domain) {

        String cmdResponse = cmdExecute(domain.getAddress());

        List<SubfinderResponse> responses = convert(cmdResponse);

        List<Subdomain> subdomains = new ArrayList<>();

        for (SubfinderResponse response : responses) {
            Optional<Subdomain> possibleSubdomain = subdomainRepository.findByAddress(response.getHost());

            if(possibleSubdomain.isEmpty()) {
                Subdomain subdomain = new Subdomain(response.getHost(), domain);
                transactional.persist(subdomain);
                domain.addSubdomains(subdomain);
            }
        }

        transactional.update(domain);

        log.info("Foram encontrados {} subdominios para o dominio {}", responses.size(), domain.getAddress());

    }

    private String cmdExecute(String target){

        String command = String.format ("docker run --rm --name kali kali subfinder -d '%s' -silent -json",target.replaceFirst("^www\\.", ""));

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
            return output.toString();

        } catch (Exception e) {
            log.error("Não foi possivel buscar os subdomains para o dominio {}. Motivo: {}", target, e.getMessage());
            return "Erro ao executar a busca por subdomínios";
        }
    }

    private List<SubfinderResponse> convert(String cmdResponse) {

        String[] linesResponse = cmdResponse.split("\n");

        String[] lines = new String[linesResponse.length - 1];

        System.arraycopy(linesResponse, 1, lines, 0, lines.length);

        ObjectMapper objectMapper = new ObjectMapper();
        List<SubfinderResponse> responses = new ArrayList<>();

        for (String line : lines) {
            try {
                SubfinderResponse response = objectMapper.readValue(line, SubfinderResponse.class);
                responses.add(response);
            } catch (IOException e) {
                log.error("Não foi possivel converter o resultado do Subfinder. Motivo: {}", e.getMessage());
            }
        }

        return responses;

    }
}
