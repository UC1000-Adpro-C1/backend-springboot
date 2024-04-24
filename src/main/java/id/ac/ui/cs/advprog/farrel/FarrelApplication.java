package id.ac.ui.cs.advprog.farrel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class FarrelApplication {

	public static void main(String[] args) {
		SpringApplication.run(FarrelApplication.class, args);
	}

}
