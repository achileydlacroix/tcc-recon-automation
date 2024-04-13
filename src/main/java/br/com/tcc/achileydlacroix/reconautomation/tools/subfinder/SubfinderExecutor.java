package br.com.tcc.achileydlacroix.reconautomation.tools.subfinder;

import br.com.tcc.achileydlacroix.reconautomation.domain.subdomain.Subdomain;
import br.com.tcc.achileydlacroix.reconautomation.shared.TransactionalContext;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

@Component
public class SubfinderExecutor {

    private static TransactionalContext transacional;

    private final Logger log = LoggerFactory.getLogger(SubfinderExecutor.class);

    public Set<Subdomain> executeSubfinder(String target) {
        try {

            String command = "subfinder -silent -d" + target + "o json";

            Process process = Runtime.getRuntime().exec(command);

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();

            if (exitCode == 0) {

                Gson gson = new Gson();
                SubfinderResponse response = gson.fromJson(output.toString(), SubfinderResponse.class);
                Set<Subdomain> subdomains = new HashSet<>();

                for(int i = 0; i <= response.getSubdomains().size(); i++) {

                    Subdomain subdomain = new Subdomain(response.getSubdomains().get(i));
                    transacional.persist(subdomain);
                    subdomains.add(subdomain);
                    i++;
                }

                return subdomains;
            } else {
                System.err.println("Subfinder falhou com código de saída " + exitCode);
                return null;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

}
