package br.com.tcc.achileydlacroix.reconautomation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories(enableDefaultTransactions = false)
@EnableAsync
@EnableScheduling
public class ReconAutomationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReconAutomationApplication.class, args);
	}

}
