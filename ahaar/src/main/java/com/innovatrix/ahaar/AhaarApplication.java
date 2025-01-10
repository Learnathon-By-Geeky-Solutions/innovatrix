package com.innovatrix.ahaar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
public class AhaarApplication {

	/**
	 * Launches the Ahaar Spring Boot application.
	 *
	 * @param args Command-line arguments passed to the application during startup
	 * @see SpringApplication#run(Class, String...)
	 */
	public static void main(String[] args) {
		SpringApplication.run(AhaarApplication.class, args);
	}


}
