package br.com.tcc.achileydlacroix.reconautomation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(enableDefaultTransactions = false)
public class ReconAutomationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReconAutomationApplication.class, args);
	}

}
