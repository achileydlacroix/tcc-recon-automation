package br.com.tcc.achileydlacroix.reconautomation.tools;

import br.com.tcc.achileydlacroix.reconautomation.shared.TransactionalContext;
import br.com.tcc.achileydlacroix.reconautomation.tools.subfinder.SubfinderExecutor;
import br.com.tcc.achileydlacroix.reconautomation.vulnerability.Vulnerability;
import br.com.tcc.achileydlacroix.reconautomation.vulnerability.VulnerabilityRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class NucleiExecutor {

    private VulnerabilityRepository repository;
    private TransactionalContext transacional;

    private final Logger log = LoggerFactory.getLogger(SubfinderExecutor.class);

    public static List<Vulnerability> executeNuclei(String target) {
        try {

            String command = "nuclei -d" + target + "o json";

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
                JsonObject teste =gson.fromJson(output.toString(), JsonObject.class);

                return null;
            } else {
                System.err.println("Nuclei falhou com código de saída " + exitCode);
                return null;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
